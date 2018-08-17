package com.scanner.document.document_scanner.scan

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

import javax.inject.Inject

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.scanner.document.document_scanner.R
import com.scanner.document.document_scanner.common.viewmodel.Response
import com.scanner.document.document_scanner.common.viewmodel.Status
import dagger.android.AndroidInjection
import timber.log.Timber


class ScanActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ScanViewModelFactory

    @BindView(R.id.data_textview)
    var dataTextView: TextView? = null

    @BindView(R.id.loading_indicator)
    var loadingIndicator: ProgressBar? = null

    private lateinit var viewModel: ScanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scan_activity)

        ButterKnife.bind(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ScanViewModel::class.java)

        viewModel.response.observe(this, Observer {
            response -> processResponse(response)
        })
    }

    @OnClick(R.id.common_camera_button)
    internal fun onCameraButtonClicked() {
        viewModel.loadCommonCamera()
    }

    @OnClick(R.id.scan_button)
    internal fun onScanButtonClicked() {
        viewModel.loadScanData()
    }

    private fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()

            Status.SUCCESS -> renderDataState(response?.data)

            Status.ERROR -> renderErrorState(response?.error)
        }
    }

    private fun renderLoadingState() {
        dataTextView!!.visibility = View.GONE
        loadingIndicator!!.visibility = View.VISIBLE
    }

    private fun renderDataState(data: String?) {
        loadingIndicator!!.visibility = View.GONE
        dataTextView!!.visibility = View.VISIBLE
        dataTextView!!.text = data
    }

    private fun renderErrorState(throwable: Throwable?) {
        Timber.e(throwable)
        loadingIndicator!!.visibility = View.GONE
        dataTextView!!.visibility = View.GONE
        Toast.makeText(this, R.string.data_error, Toast.LENGTH_SHORT).show()
    }
}