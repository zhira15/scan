package com.scanner.document.docscanner.ui.documents

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scanner.document.docscanner.R
import com.scanner.document.docscanner.databinding.FragmentDocumentsBinding
import com.scanner.document.docscanner.ui.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_documents.*
import timber.log.Timber

/**
 * Created by AnthonyCAS on 8/20/18.
 */

class DocumentsFragment: Fragment() {

    private lateinit var viewDataBinding: FragmentDocumentsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        viewDataBinding = FragmentDocumentsBinding.inflate(inflater, container, false).apply {
            viewModel = (activity as HomeActivity).obtainDocumentsViewModel()

            viewModel?.apply {
                documentsList.adapter = this.adapter
                documentsList.layoutManager = GridLayoutManager(context, 2)

                // hide divider decorator
                //val divider = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
                //divider.setDrawable(ContextCompat.getDrawable(activity as HomeActivity, R.drawable.main_line_divider))
                //documentsList.addItemDecoration(divider)

                state.observe(this@DocumentsFragment, Observer {
                    when (it) {
                        DocumentsViewState.LOADING -> { swipeRefresh.isRefreshing = true }
                        else -> { swipeRefresh.isRefreshing = false }
                    }
                })
            }
        }

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var viewModel = (activity as HomeActivity).obtainDocumentsViewModel()
        swipeRefresh.setOnRefreshListener {
            viewModel.reloadData()
        }
    }

    companion object {
        fun newInstance() = DocumentsFragment()
    }
}