package com.scanner.document.docscanner.ui.camera

import android.arch.lifecycle.Observer
import android.content.Context
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import com.scanner.document.docscanner.R
import com.scanner.document.docscanner.ScannerAplication
import com.scanner.document.docscanner.databinding.FragmentCameraBinding
import kotlinx.android.synthetic.main.fragment_camera.*
import timber.log.Timber
import java.io.File

/**
 * Created by AnthonyCAS on 8/20/18.
 */

class CameraFragment: Fragment(), CameraNavigator.View {

    //region Variables
    lateinit var cameraPreview: CameraNavigator.Preview
    private lateinit var cameraManager: CameraManager

    private var backgroundThread: HandlerThread? = null
    override var backgroundHandler: Handler? = null

    private lateinit var viewDataBinding: FragmentCameraBinding
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cameraPreview = ScannerAplication.appComponent.getCameraPreview()
        startBackgroundThread()

        cameraPreview.attachView(this)
        cameraManager = (activity as CameraActivity).getSystemService(Context.CAMERA_SERVICE) as CameraManager
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        viewDataBinding = FragmentCameraBinding.inflate(inflater, container, false).apply {
            viewModel = (activity as CameraActivity).obtainViewModel().apply {

                actionTakePicture.observe(this@CameraFragment, Observer {
                    if (cameraPreview.cameraIsReady) {
                        cameraPreview.captureImage()
                    }
                })

                actionExtraOptions.observe(this@CameraFragment, Observer {

                    extraOptions.visibility = it.let {
                        when (it) {
                            true -> {View.VISIBLE }
                            else ->  {View.GONE }
                        }
                    }
                })
            }
        }

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun openPreviewActivity(imagePath: String) {
        Timber.e("PATH: $imagePath")
        //startActivity(Intent(this, PreviewActivity::class.java).putExtra("filename", imagePath))
    }

    private fun startBackgroundThread() {
        backgroundThread = HandlerThread("CameraBackground").also { it.start() }
        backgroundHandler = Handler(backgroundThread?.looper)
    }

    private fun stopBackgroundThread() {
        backgroundThread?.quitSafely()
        try {
            backgroundThread?.join()
            backgroundThread = null
            backgroundHandler = null
        } catch (e: InterruptedException) {
            Timber.e(e.toString())
        }
    }

    override fun getGalleryFolder(): String {
        val systemGalleryFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
        val imageSavingFolder = File(systemGalleryFolder + File.separator + getString(R.string.gallery_dir))
        return if (imageSavingFolder.exists()) imageSavingFolder.toString()
        else {
            if (imageSavingFolder.mkdir()) imageSavingFolder.toString()
            else systemGalleryFolder
        }
    }

    override fun getScreenRotation(): Int {
        return (activity as CameraActivity).windowManager.defaultDisplay.rotation
    }

    override fun setTextureViewAspectRatio(width: Int, height: Int) {
        cameraPreviewLayout.setAspectRatio(width, height)
    }


    override fun onPause() {
        stopBackgroundThread()
        cameraPreview.detachView()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        if (cameraPreviewLayout.isAvailable)
            cameraPreview.launchCameraPreview(cameraManager, cameraPreviewLayout)
        else {
            cameraPreviewLayout.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
                override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {}
                override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {}
                override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean = false
                override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
                    cameraPreview.launchCameraPreview(cameraManager, cameraPreviewLayout)
                }
            }
        }
    }

    companion object {
        fun newInstance() = CameraFragment()
    }
}