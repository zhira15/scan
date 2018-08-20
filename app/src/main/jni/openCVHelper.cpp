//
// Created by Adarsh on 13/9/16
//

#include "openCVHelper.h"
#include <stdio.h>
#include <stdlib.h>
#include <algorithm>
#include <vector>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <android/log.h>
#include <opencv2/opencv.hpp>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc_c.h>
#include <android/bitmap.h>
#define APPNAME "OCV4Android"

using namespace cv;
using namespace std;

int RESIZE_WIDTH=75;
int RESIZE_HEIGHT=150;




Mat resize_(Mat &inputImage)
{
	Mat outputImage;
	int w = inputImage.size().width;
	int h = inputImage.size().height;
	Size rsiz = (w > h) ? Size(RESIZE_HEIGHT,RESIZE_WIDTH) : Size(RESIZE_WIDTH,RESIZE_HEIGHT);
	resize(inputImage, outputImage, rsiz);
	return outputImage;
}

int getDistance(Point a, Point b)
{
	int c = sqrt( pow(b.x - a.x , 2) + pow(b.y - a.y, 2));
	// cout << c << endl;
	return c;
}

vector<Point2f> pushPoints(Point p1, Point p2, Point p3, Point p4)
{
	vector<Point2f> dst;
	dst.push_back(p1);
	dst.push_back(p2);
	dst.push_back(p3);
	dst.push_back(p4);
	return dst;
}

vector<Point2f> orderPoints(vector<Point2f> sort)
{
	Point2f tl, tr, bl, br;
	int diff[4], sum[4], max_min[4];
	for(int i = 0; i < sort.size(); i++)
	{
		sum[i] = sort[i].x + sort[i].y;
	}
	max_min[0] = min(sum[0],min(sum[1],min(sum[2],sum[3])));
	max_min[1] = max(sum[0],max(sum[1],max(sum[2],sum[3])));
	for(int i = 0; i < sort.size(); i++)
	{
		diff[i] = abs(sort[i].x - sort[i].y);
	}
	max_min[2] = min(diff[0],min(diff[1],min(diff[2],diff[3])));
	max_min[3] = max(diff[0],max(diff[1],max(diff[2],diff[3])));
	for(int i = 0; i < sort.size(); i++)
	{
		if(sum[i]==max_min[0]){ tl = sort[i];}
		if(sum[i]==max_min[1]){ br = sort[i];}
		//if(diff[i]==max_min[2]){ tr = sort[i];}
		if(diff[i]==max_min[3]){ bl = sort[i];}
	}
	for(int i = 0; i < sort.size(); i++)
	{
		if(sort[i]!=tl && sort[i]!=bl && sort[i]!=br)
		{
			tr = sort[i];
		}
	}
	sort = pushPoints(tl, tr, bl, br);
	//cout << sort << endl;
	return sort;
}

vector<Point2f> getPoints(Mat image)
{
	int width = image.size().width;
	int height = image.size().height;
	int intensity, img_intensity, larea = 0, lindex = 0;
	Mat bgdModel, fgdModel, mask;
	vector<vector<Point> > contours;
	vector<Point2f> approxCurve, rectPts;
	double a, epsilon;
	Rect rect, bounding_rect;
	Size rsiz = (width > height) ? Size(RESIZE_HEIGHT,RESIZE_WIDTH) : Size(RESIZE_WIDTH,RESIZE_HEIGHT);

    int x_rect=(int)((float)RESIZE_WIDTH/7.0);
    int y_rect= (int)((float)RESIZE_HEIGHT/7.0);
    int hig_rect,wid_rect;
    if(width > height)
    {
        wid_rect=width-(int)(2*((float)RESIZE_HEIGHT/7.0));
        hig_rect=height-(int)(2*((float)RESIZE_WIDTH/7.0));
    }
    else
    {
        wid_rect=width-(int)(2*((float)RESIZE_WIDTH/7.0));
        hig_rect=height-(int)(2*((float)RESIZE_HEIGHT/7.0));
    }


	resize(image, image, rsiz);
	mask = Mat::zeros(rsiz, CV_8UC1);
	bgdModel = Mat::zeros(1, 65, CV_64F);
	fgdModel = Mat::zeros(1, 65, CV_64F);
	// rect = Rect(50, 50, width-100, height-100);
	rect = Rect(x_rect,y_rect,wid_rect ,hig_rect );
	__android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "%d", image.channels());
	grabCut(image, mask, rect, bgdModel, fgdModel, 20, GC_INIT_WITH_RECT);
	for(int i = 0; i < image.rows; i++)
	{
		for(int j = 0; j < image.cols; j++)
		{
			intensity = mask.at<uchar>(i, j);
			if( (intensity == 0) | (intensity == 2) ) { mask.at<uchar>(i, j) = 0 ; } else { mask.at<uchar>(i, j) = 255 ; }
		}
	}
	//imshow("Mask", mask);
	//waitKey(0);
	findContours(mask, contours, CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE);
	for(int i = 0; i < contours.size(); i++)
	{
		a = contourArea(contours[i]);
		if(a > larea)
		{
			larea = a;
			lindex = i;
			bounding_rect = boundingRect(contours[i]);
		}
	}
	//drawContours(image, contours, lindex, Scalar(0,0,255), 2, 8);
	//imshow("Image",image);
	//waitKey(0);
	epsilon = 0.1*arcLength(Mat(contours[lindex]), true);
	approxPolyDP(Mat(contours[lindex]), approxCurve, epsilon,true);
	//cout << approxCurve << endl;
	approxCurve = orderPoints(approxCurve);

	return approxCurve;
}

Mat doPerspective(Mat inputImage)
{
	// inputImage = resize_(inputImage);
	__android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "Checkpt 1");
	vector<Point2f> points = getPoints(inputImage);
	__android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "Checkpt 2");
	// cout << points << endl;
	int w1 = getDistance(points[0], points[1]);
	Point p1, p2;
	int w = inputImage.size().width;
	int h = inputImage.size().height;
	if(w>h){p1 = Point(0,h); p2 = Point(w,0);}
	else{p1 = Point(w,0); p2 = Point(0,h);}
	vector<Point2f> dst = pushPoints(Point(0,0), p1, p2, Point(w,h));
	//cout << dst << endl;
	Mat transmtx = getPerspectiveTransform(points, dst);
	warpPerspective(inputImage, inputImage, transmtx, inputImage.size());
	//imwrite("perspective_output.jpg", inputImage);
	return inputImage;
}

Mat binarize(Mat image)
{
	cvtColor( image, image, CV_BGR2GRAY);
	adaptiveThreshold(image, image, 255.0, CV_ADAPTIVE_THRESH_GAUSSIAN_C, CV_THRESH_BINARY, 11, 2);
	return image;
}

//This function is used for brightness and contrast adjustment
Mat enhance(Mat image, double alpha = 1.0, double beta = 0)
{
	image.convertTo(image, -1, alpha, beta); //Alpha is brightness value [1.0 - 3.0], Beta is Contrast value [0 - 100]
	return image;
}

jobject mat_to_bitmap(JNIEnv * env, Mat & src, bool needPremultiplyAlpha, jobject bitmap_config){
    jclass java_bitmap_class = (jclass)env->FindClass("android/graphics/Bitmap");
    jmethodID mid = env->GetStaticMethodID(java_bitmap_class,
                                           "createBitmap", "(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;");

    jobject bitmap = env->CallStaticObjectMethod(java_bitmap_class,
                                                 mid, src.size().width, src.size().height, bitmap_config);
    AndroidBitmapInfo  info;
    void*              pixels = 0;

    try {
        CV_Assert(AndroidBitmap_getInfo(env, bitmap, &info) >= 0);
        CV_Assert(src.type() == CV_8UC1 || src.type() == CV_8UC3 || src.type() == CV_8UC4);
        CV_Assert(AndroidBitmap_lockPixels(env, bitmap, &pixels) >= 0);
        CV_Assert(pixels);
        if(info.format == ANDROID_BITMAP_FORMAT_RGBA_8888){
            Mat tmp(info.height, info.width, CV_8UC4, pixels);
            if(src.type() == CV_8UC1){
                cvtColor(src, tmp, CV_GRAY2RGBA);
            }else if(src.type() == CV_8UC3){
                cvtColor(src, tmp, CV_BGR2RGBA);
            }else if(src.type() == CV_8UC4){
                if(needPremultiplyAlpha){
                    cvtColor(src, tmp, CV_RGBA2mRGBA);
                }else{
                    src.copyTo(tmp);
                }
            }
        }else{
            // info.format == ANDROID_BITMAP_FORMAT_RGB_565
            Mat tmp(info.height, info.width, CV_8UC2, pixels);
            if(src.type() == CV_8UC1){
                cvtColor(src, tmp, CV_GRAY2BGR565);
            }else if(src.type() == CV_8UC3){
                cvtColor(src, tmp, CV_BGR2BGR565);
            }else if(src.type() == CV_8UC4){
                cvtColor(src, tmp, CV_RGBA2BGR565);
            }
        }
        AndroidBitmap_unlockPixels(env, bitmap);
        return bitmap;
    }catch(cv::Exception e){
        AndroidBitmap_unlockPixels(env, bitmap);
        jclass je = env->FindClass("org/opencv/core/CvException");
        if(!je) je = env->FindClass("java/lang/Exception");
        env->ThrowNew(je, e.what());
        return bitmap;
    }catch (...){
        AndroidBitmap_unlockPixels(env, bitmap);
        jclass je = env->FindClass("java/lang/Exception");
        env->ThrowNew(je, "Unknown exception in JNI code {nMatToBitmap}");
        return bitmap;
    }
}


extern "C" {

    JNIEXPORT jintArray JNICALL Java_com_scanner_document_docscanner_activities_OpenCVHelper_gray(
            JNIEnv *env, jclass obj, jintArray buf, int w, int h) {

        jint *cbuf;
        cbuf = env->GetIntArrayElements(buf, JNI_FALSE);
        if (cbuf == NULL) {
            return 0;
        }

        Mat imgData(h, w, CV_8UC4, (unsigned char *) cbuf);
        for(int p = 0; p < w; p++) {
            for(int q = 0; q < h; q++){
                // __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "%d", imgData.at<uchar>(p,q));
            }
        }

        __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "do i come here");
        cvtColor(imgData , imgData , CV_RGBA2RGB);
        imgData = doPerspective(imgData);
        imgData = binarize(imgData);

        int size = w * h;
        jintArray result = env->NewIntArray(size);
        env->SetIntArrayRegion(result, 0, size, cbuf);
        env->ReleaseIntArrayElements(buf, cbuf, 0);
        return result;
    }


    JNIEXPORT jobject JNICALL Java_com_scanner_document_docscanner_activities_OpenCVHelper_getGrayBitmap
    (JNIEnv *env, jobject thiz,jobject bitmap)
    {
        __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "Scaning getGrayBitmap");
        int ret;
        AndroidBitmapInfo info;
        void* pixels = 0;

        if ((ret = AndroidBitmap_getInfo(env, bitmap, &info)) < 0) {
            __android_log_print(ANDROID_LOG_VERBOSE, APPNAME,"AndroidBitmap_getInfo() failed ! error=%d", ret);
            return NULL;
        }

        if (info.format != ANDROID_BITMAP_FORMAT_RGBA_8888 )
        {       __android_log_print(ANDROID_LOG_VERBOSE, APPNAME,"Bitmap format is not RGBA_8888!");
            return NULL;
        }

        if ((ret = AndroidBitmap_lockPixels(env, bitmap, &pixels)) < 0) {
            __android_log_print(ANDROID_LOG_VERBOSE, APPNAME,"AndroidBitmap_lockPixels() failed ! error=%d", ret);
        }

        Mat mbgra(info.height, info.width, CV_8UC4, pixels);
        // init our output image
        Mat dst = mbgra.clone();

        cvtColor(mbgra, dst, CV_BGR2GRAY);

        //get source bitmap's config
        jclass java_bitmap_class = (jclass)env->FindClass("android/graphics/Bitmap");
        jmethodID mid = env->GetMethodID(java_bitmap_class, "getConfig", "()Landroid/graphics/Bitmap$Config;");
        jobject bitmap_config = env->CallObjectMethod(bitmap, mid);
        jobject _bitmap = mat_to_bitmap(env,dst,false,bitmap_config);

        AndroidBitmap_unlockPixels(env, bitmap);
        return _bitmap;
    }


    JNIEXPORT jintArray JNICALL Java_com_scanner_document_docscanner_activities_OpenCVHelper_getBoxPoints(
            JNIEnv *env, jclass obj, jintArray buf, int w, int h) {

        jint *cbuf;
        cbuf = env->GetIntArrayElements(buf, JNI_FALSE);
        if (cbuf == NULL) {
            // return 0;
        }

        Mat imgData(h, w, CV_8UC4, (unsigned char *) cbuf);
        int OrigWidth = imgData.cols;
        int OrigHeight = imgData.rows;
        int resizedHgt,resizedWidth;
        if (OrigWidth > OrigHeight){
            resizedWidth=RESIZE_HEIGHT;
            resizedHgt = RESIZE_WIDTH;
        }
        else{
                 resizedWidth=RESIZE_WIDTH;
                 resizedHgt = RESIZE_HEIGHT;
        }

        // imgData = resize_(imgData);

        cvtColor(imgData , imgData , CV_RGBA2RGB);
        __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "SIZE %d %d",OrigHeight,OrigWidth);
        __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "Checkpt 3xxx");
        vector<Point2f> points = getPoints(imgData);
        __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "Checkpt 3");
        int point[8] = {0,0,0,0,0,0,0,0};
        int countr=0;
            for(int i=0;i<points.size();i++)
            {
                 __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, " point  before x: %d", (int)points[i].x);
                point[countr] = (int)points[i].x*(float(OrigWidth)/resizedWidth);
               countr++;
                __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, " point  before y: %d", (int)points[i].y);
                point[countr] = (int)points[i].y*(float(OrigHeight)/resizedHgt);
                countr++;
            }
        for(int i=0;i<countr;i++)
                {
                    __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, " point %d", point[i]);

                }
        __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "%d", points.size());
        // jfloatArray result = env->NewFloatArray(points.size());
        // env->SetFloatArrayRegion(result, 0, points.size(), points);
        // return result;
        jintArray result = env->NewIntArray(8);
        env->SetIntArrayRegion(result, 0, 8, point);
        env->ReleaseIntArrayElements(buf, cbuf, 0);
        return result;
    }

    JNIEXPORT jintArray JNICALL Java_com_scanner_document_docscanner_activities_OpenCVHelper_perspective(
            JNIEnv *env, jclass obj, jintArray buf, jintArray pts, int w, int h) {
        __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "inside the function");
        jint *cbuf;
        cbuf = env->GetIntArrayElements(buf, JNI_FALSE);
        if (cbuf == NULL) {
            return 0;
        }
        int newpoints[8];
        Mat imgData(h, w, CV_8UC4, (unsigned char *) cbuf);
        Mat abc;
        //Mat imgDataCopy(imgData, Rect(50,50,500,500));
        cvtColor(imgData , abc , CV_RGBA2RGB);
        jint *point = env->GetIntArrayElements(pts, JNI_FALSE);
            if (point == NULL) {
                return buf;
            }
        for(int p = 0; p < 8; p++) {
            newpoints[p] = point[p];
            __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "%d", newpoints[p]);

        }
        vector<Point2f> points;
        for(int p = 0; p < 8; p+=2) {
                Point2f abc((float)newpoints[p],(float)newpoints[p+1]);
                points.push_back(abc);
         }

        int w1 = getDistance(points[0], points[1]);
        Point p1, p2;
        w = abc.size().width;
        h = abc.size().height;
        p1 = Point(w,0); p2 = Point(0,h);
        // if(w>h){p1 = Point(0,h); p2 = Point(w,0);}
        // else{p1 = Point(w,0); p2 = Point(0,h);}
        vector<Point2f> dst = pushPoints(Point(0,0), p1, p2, Point(w,h));
        //cout << dst << endl;
        Mat transmtx = getPerspectiveTransform(points, dst);
        warpPerspective(abc, abc, transmtx, imgData.size());

        cvtColor(abc , imgData , CV_RGB2RGBA);

        int size = imgData.rows*imgData.cols;
        jintArray result = env->NewIntArray(size);
        env->SetIntArrayRegion(result, 0, size, cbuf);
        env->ReleaseIntArrayElements(buf, cbuf, 0);
        __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "%d %d %d %d", w, h, imgData.rows, imgData.cols);
        return result;

    }

}