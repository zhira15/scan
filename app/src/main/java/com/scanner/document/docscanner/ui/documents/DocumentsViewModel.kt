package com.scanner.document.docscanner.ui.documents

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.scanner.document.docscanner.data.model.DocItem
import com.scanner.document.docscanner.util.FileReader
import com.scanner.document.docscanner.util.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.io.File
import kotlin.collections.ArrayList

/**
 * Created by AnthonyCAS on 8/20/18.
 */


enum class DocumentsViewState {
    LOADING, ERROR, COMPLETE
}

class DocumentsViewModel(context: Application): AndroidViewModel(context) {

    internal val adapter = DocumentsAdapter()
    internal val actionOpenDocument = SingleLiveEvent<String>()
    internal val state = MutableLiveData<DocumentsViewState>()
    val errorMessage = ObservableField<String>("")

    private val disposables = CompositeDisposable()
    val isEmpty = ObservableBoolean(true)

    init {
        adapter.itemSelected = {
            Timber.d("Open document action")
        }
        reloadData()
    }

    fun reloadData() {
        state.value = DocumentsViewState.LOADING
        adapter.items = FileReader.walk()
        isEmpty.set(adapter.isEmpty())
        state.value = DocumentsViewState.COMPLETE
    }
}