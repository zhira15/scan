package com.scanner.document.docscanner.ui.camera

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.scanner.document.docscanner.R
import com.scanner.document.docscanner.ui.global.NetworkAppCompatActivity
import com.scanner.document.docscanner.util.obtainViewModel
import com.scanner.document.docscanner.util.replaceFragmentInActivity

/**
 * Created by AnthonyCAS on 8/20/18.
 */
class CameraActivity: NetworkAppCompatActivity() {

    //region variables
    private lateinit var viewModel: CameraViewModel
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        setupViewFragment()
        //setupViewModel()
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

    //region Binding
    fun obtainViewModel(): CameraViewModel = obtainViewModel(CameraViewModel::class.java)
    //endregion

    //region Companion Object
    companion object {
        fun newIntent(context: Context): Intent = Intent(context, CameraActivity::class.java)
    }
    //endregion
}