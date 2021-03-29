package com.example.htmlparsingwithjsoup.presantation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.htmlparsingwithjsoup.data.CutRepository
import com.example.htmlparsingwithjsoup.enums.CutType
import org.jsoup.nodes.Document

class CutViewModel @ViewModelInject constructor
    (
    private val repository: CutRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    private val _serviceResponse = MutableLiveData<Document>()
    private val _detailServiceResponse = MutableLiveData<Document>()

    private var apiError = MutableLiveData<Throwable>()

    init {
        _isLoading.value = false
    }

    val getDocumentLiveData: LiveData<Document>
        get() = _serviceResponse

    val getDocumentDetailLiveData: LiveData<Document>
        get() = _detailServiceResponse

    val isLoadingLiveData: LiveData<Boolean>
        get() = _isLoading

    fun getDocument(cutType: CutType) {
        _isLoading.value = true

        when (cutType) {
            CutType.ELECTRIC -> {
                repository.getElectricData(
                    {
                        _serviceResponse.value = it
                        _isLoading.value = false
                    },
                    {
                        apiError.value = it
                        _isLoading.value = false
                    })

            }

            CutType.WATER -> {
                repository.getWaterData(
                    {
                        _serviceResponse.value = it
                        _isLoading.value = false
                    },
                    {
                        apiError.value = it
                        _isLoading.value = false
                    })

            }
        }
    }

    fun getDocumentDetail(url: String) {
        _isLoading.value = true
        repository.getDetailData(
            url,
            {
                _detailServiceResponse.value = it
                _isLoading.value = false
            },
            {
                apiError.value = it
                _isLoading.value = false
            })
    }
}