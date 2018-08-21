package com.scanner.document.docscanner.ui.camera

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scanner.document.docscanner.databinding.FragmentCameraBinding
import kotlinx.android.synthetic.main.fragment_camera.*

/**
 * Created by AnthonyCAS on 8/20/18.
 */
class CameraFragment: Fragment() {

    private lateinit var viewDataBinding: FragmentCameraBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        viewDataBinding = FragmentCameraBinding.inflate(inflater, container, false).apply {
            viewModel = (activity as CameraActivity).obtainViewModel().apply {
                actionExtraOptions.observe(this@CameraFragment, Observer {

                    extraOptions.visibility = it.let {
                        when (it) {
                            true -> {View.VISIBLE }
                            else ->  {View.GONE }
                        }
                    }

                    cameraPreviewLayout.visibility = View.GONE
                })
            }
        }

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() = CameraFragment()
    }
}