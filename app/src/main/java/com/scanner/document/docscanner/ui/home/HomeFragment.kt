package com.scanner.document.docscanner.ui.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scanner.document.docscanner.databinding.FragmentHomeBinding

/**
 * Created by AnthonyCAS on 8/20/18.
 */

class HomeFragment: Fragment() {

    private lateinit var viewDataBinding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        viewDataBinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            viewModel = (activity as HomeActivity).obtainViewModel()
            viewModel?.apply {
                //observers
            }
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    companion object {
        fun newInstance() = HomeFragment()
    }
}