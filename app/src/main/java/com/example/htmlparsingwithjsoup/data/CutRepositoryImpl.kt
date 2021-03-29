package com.example.htmlparsingwithjsoup.data

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CutRepositoryImpl @Inject constructor(private val apiService: CutApi) : CutRepository {

    override fun getElectricData(
        successHandler: (Document?) -> Unit,
        errorHandler: (Throwable?) -> Unit
    ) {
        apiService.getElectricData().enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                if (response.isSuccessful) {
                    val document: Document = Jsoup.parse(response.body())
                    successHandler(document)
                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                errorHandler(t)
            }
        })
    }

    override fun getWaterData(
        successHandler: (Document?) -> Unit,
        errorHandler: (Throwable?) -> Unit
    ) {
        apiService.getWaterData().enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                if (response.isSuccessful) {
                    val document: Document = Jsoup.parse(response.body())
                    successHandler(document)
                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                errorHandler(t)
            }
        })
    }

    override fun getDetailData(
        url:String,
        successHandler: (Document?) -> Unit,
        errorHandler: (Throwable?) -> Unit
    ) {
        apiService.getDetailData(url).enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                if (response.isSuccessful) {
                    val document: Document = Jsoup.parse(response.body())
                    successHandler(document)
                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                errorHandler(t)
            }
        })
    }

}

