package com.example.htmlparsingwithjsoup.data

import org.jsoup.nodes.Document

interface CutRepository {

    fun getElectricData(
        successHandler: (Document?) -> Unit,
        errorHandler: (Throwable?) -> Unit
    )

    fun getWaterData(
        successHandler: (Document?) -> Unit,
        errorHandler: (Throwable?) -> Unit
    )

    fun getDetailData(
        url: String,
        successHandler: (Document?) -> Unit,
        errorHandler: (Throwable?) -> Unit
    )

}