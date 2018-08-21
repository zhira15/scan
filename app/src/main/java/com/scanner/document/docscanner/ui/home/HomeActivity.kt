package com.scanner.document.docscanner.ui.home

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.scanner.document.docscanner.R
import com.scanner.document.docscanner.ui.camera.CameraActivity
import com.scanner.document.docscanner.ui.documents.DocumentsViewModel
import com.scanner.document.docscanner.ui.global.NetworkAppCompatActivity
import com.scanner.document.docscanner.util.replaceFragmentInActivity
import com.scanner.document.docscanner.util.obtainViewModel

/**
 * Created by AnthonyCAS on 8/20/18.
 */

class HomeActivity: NetworkAppCompatActivity(), HomeNavigator {

    //region Variables
    private lateinit var viewModel: HomeViewModel
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupViewFragment()
        setupViewModel()
    }

    override fun onResume() {
        super.onResume()
    }
    //endregion

    private fun setupViewFragment() {
        supportFragmentManager.findFragmentById(R.id.content) ?: HomeFragment.newInstance().let {
            replaceFragmentInActivity(it, R.id.content)
        }
    }

    private fun setupViewModel() {
        viewModel = obtainViewModel().apply {
            // observers
            openCamera.observe(this@HomeActivity, Observer {
                openCameraApp()
            })
        }
    }

    override fun openCameraApp() {
        startActivity(CameraActivity.newIntent(this))
        //var intent: Intent = Intent(this, CameraTest::class.java)
        //startActivity(intent)
    }

    //region Binding
    fun obtainViewModel(): HomeViewModel = obtainViewModel(HomeViewModel::class.java)
    fun obtainDocumentsViewModel(): DocumentsViewModel = obtainViewModel(DocumentsViewModel::class.java)
    //endregion

    //region Companion Object
    companion object {
        fun newIntent(context: Context): Intent = Intent(context, HomeActivity::class.java)
    }
    //endregion

}