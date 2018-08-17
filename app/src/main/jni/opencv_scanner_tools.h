//
// Created by Anthony Ccapira Avendaño on 8/17/18.
//

#ifndef DOCUMENT_SCANNER_OPENCV_SCANNER_TOOLS_H
#define DOCUMENT_SCANNER_OPENCV_SCANNER_TOOLS_H

#endif //DOCUMENT_SCANNER_OPENCV_SCANNER_TOOLS_H

#include <jni.h>

#ifdef __cplusplus
extern "C" {
    #endif

    JNIEXPORT jintArray JNICALL Java_opencv4android_OpenCVHelper_gray
      (JNIEnv *, jclass, jintArray, jint, jint);

    JNIEXPORT jintArray JNICALL Java_opencv4android_OpenCVHelper_detectFeatures
      (JNIEnv *, jclass, jintArray, jint, jint);

    #ifdef __cplusplus
}
#endif
#endif
