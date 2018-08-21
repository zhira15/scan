package com.scanner.document.docscanner.ui.camera

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.Camera
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.scanner.document.docscanner.R
import com.scanner.document.docscanner.ui.global.NetworkAppCompatActivity
import com.scanner.document.docscanner.util.CameraPreview
import com.scanner.document.docscanner.util.Utils
import com.scanner.document.docscanner.util.obtainViewModel
import com.scanner.document.docscanner.util.replaceFragmentInActivity
import kotlinx.android.synthetic.main.fragment_camera.*
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*

/**
 * Created by AnthonyCAS on 8/20/18.
 */
class CameraActivity: NetworkAppCompatActivity() {

    //region variables
    private lateinit var viewModel: CameraViewModel
    private var camera: Camera? = null
    private var cameraPreview: CameraPreview? = null
    private var pictureCallbacks: Camera.PictureCallback? = null

    lateinit var camerapreview: LinearLayout
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        setupViewFragment()
        setupCamera()
        setupViewModel()
    }

    override fun onResume() {
        super.onResume()
        if (camera == null) {
            Timber.e("AQUIIIIII")
            camera = Camera.open(findBackFacingCamera())
            pictureCallbacks = getPictureCallback()
            cameraPreview?.refreshCamera(camera)
        }
    }

    private fun setupViewFragment() {
        supportFragmentManager.findFragmentById(R.id.content) ?: CameraFragment.newInstance().let {
            replaceFragmentInActivity(it, R.id.content)
        }
    }

    private fun setupViewModel() {
        viewModel = obtainViewModel().apply {

        }
    }

    private fun setupCamera() {
        if (camera == null) {
            Timber.e("AQUIIIIII")
            camera = Camera.open(findBackFacingCamera())
            pictureCallbacks = getPictureCallback()
            cameraPreview = CameraPreview(application, camera)
            cameraPreview?.refreshCamera(camera)
        }
        //camera = Camera.open(findBackFacingCamera())

//        camerapreview.addView(cameraPreview)

        //cameraPreviewLayout.addView(cameraPreview)
    }

    private fun getPictureCallback(): Camera.PictureCallback {
        return Camera.PictureCallback { data, camera ->
            // make a new picture file
            val pictureFile = getOutputMediaFile() ?: return@PictureCallback

            try {
                // write the file
                val fos = FileOutputStream(pictureFile)
                fos.write(data)
                fos.close()

                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
                val bitmap = BitmapFactory.decodeFile(pictureFile.toString(), options)
                bitmap?.recycle()
                val uri = bitmap?.let {
                    Utils.getUri(this@CameraActivity, it)
                }
                Timber.e("HERE START POLYGON")
                /*val `in` = Intent(this@CameraScreen, PolygonViewScreen::class.java)
                   `in`.putExtra("imageTest", uri)
                   startActivity(`in`)
                   finish()*/
            } catch (e: FileNotFoundException) {

            } catch (e: IOException) {

            }
            // refresh camera to continue preview
            cameraPreview?.refreshCamera(camera)
        }
    }

    private fun getOutputMediaFile(): File? {
        val mediaStorageDir = File("/sdcard/", "Single")
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }
        val name = (Date().time / 1000).toInt().toString()
        val mediaFile = File(mediaStorageDir.path + File.separator
                + "IMG_" + name + ".jpg")
        Timber.e("mediaFile : $mediaFile")
        return mediaFile
    }

    private fun findBackFacingCamera(): Int {
        var cameraId = -1
        val numberOfCameras = Camera.getNumberOfCameras()
        for (ix in 0 until numberOfCameras) {
            val info = Camera.CameraInfo()
            Camera.getCameraInfo(ix, info)
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = ix
                break
            }
        }
        return cameraId
    }

    private fun releaseCamera() {
        camera?.release()
        camera = null
    }

    //region Binding
    fun obtainViewModel(): CameraViewModel = obtainViewModel(CameraViewModel::class.java)
    //endregion

    //region Companion Object
    companion object {
        fun newIntent(context: Context): Intent = Intent(context, CameraActivity::class.java)
    }
    //endregion
}