package com.scanner.document.docscanner.util

import android.app.Application
import android.hardware.Camera
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import timber.log.Timber
import java.io.IOException

class CameraPreview(context: Application, private var camera: Camera?): SurfaceView(context), SurfaceHolder.Callback {
    private val surfaceHolder: SurfaceHolder
    internal var distance = 0f

    init {
        surfaceHolder = getHolder()
        surfaceHolder.addCallback(this)
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        try {
            if (camera == null) {
                camera?.setPreviewDisplay(holder)
                camera?.startPreview()
            }
        } catch (e: IOException) {
            Timber.d("Error setting camera preview: " + e.message)
        }

    }

    fun refreshCamera(camera: Camera?) {
        val isSurface = surfaceHolder.getSurface() ?: return
        // stop preview before making changes
        try {
            camera?.stopPreview()
        } catch (e: Exception) {
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        // start preview with new settings
        setCamera(camera)
        try {
            camera?.setPreviewDisplay(surfaceHolder)
            camera?.setDisplayOrientation(90)
            camera?.startPreview()
        } catch (e: Exception) {
            Timber.d("Error starting camera preview: " + e.message)
        }

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, w: Int, h: Int) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.
        refreshCamera(camera)
    }

    fun setCamera(currentCamera: Camera?) {
        // method to set a camera instance
        camera = currentCamera
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        // camera.release();

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Get the pointer ID
        val params = camera!!.getParameters()
        val action = event.getAction()

        if (event.getPointerCount() > 1) {
            // handle multi-touch events
            if (action == MotionEvent.ACTION_POINTER_DOWN) {
                distance = getFingerSpacing(event)
            } else if (action == MotionEvent.ACTION_MOVE && params.isZoomSupported()) {
                camera!!.cancelAutoFocus()
                handleZoom(event, params)
            }
        } else {
            // handle single touch events
            if (action == MotionEvent.ACTION_UP) {
                handleFocus(event, params)
            }
        }
        return true
    }

    private fun handleZoom(event: MotionEvent, params: Camera.Parameters) {
        val maxZoom = params.getMaxZoom()
        var zoom = params.getZoom()
        val newDist = getFingerSpacing(event)
        if (newDist > distance) {
            // zoom in
            if (zoom < maxZoom)
                zoom++
        } else if (newDist < distance) {
            // zoom out
            if (zoom > 0)
                zoom--
        }
        distance = newDist
        params.setZoom(zoom)
        camera?.setParameters(params)
    }

    fun handleFocus(event: MotionEvent, params: Camera.Parameters) {
        val pointerId = event.getPointerId(0)
        val pointerIndex = event.findPointerIndex(pointerId)
        // Get the pointer's current position
        val x = event.getX(pointerIndex)
        val y = event.getY(pointerIndex)

        val supportedFocusModes = params.getSupportedFocusModes()
        if (supportedFocusModes != null && supportedFocusModes?.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            camera?.autoFocus(object : Camera.AutoFocusCallback {
                override fun onAutoFocus(b: Boolean, camera: Camera) {
                    // currently set to auto-focus on single touch
                }
            })
        }
    }

    /** Determine the space between the first two fingers  */
    private fun getFingerSpacing(event: MotionEvent): Float {
        // ...
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return Math.sqrt((x * x + y * y).toDouble()).toFloat()
    }
}