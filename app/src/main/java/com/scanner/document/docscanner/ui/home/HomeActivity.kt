package com.scanner.document.docscanner.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.scanner.document.docscanner.R
import com.scanner.document.docscanner.ui.global.NetworkAppCompatActivity
import com.scanner.document.docscanner.util.replaceFragmentInActivity
import com.scanner.document.docscanner.util.obtainViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber

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
        }
    }

    override fun openCameraApp() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //region Binding
    fun obtainViewModel(): HomeViewModel = obtainViewModel(HomeViewModel::class.java)
    //endregion

    //region Companion Object
    companion object {
        fun newIntent(context: Context): Intent = Intent(context, HomeActivity::class.java)
    }
    //endregion

}