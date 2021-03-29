package com.example.htmlparsingwithjsoup.data

import com.example.htmlparsingwithjsoup.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface CutApi {

    @GET("/" + Constants.ELECTRIC)
    fun getElectricData() : Call<String?>

    @GET("/" + Constants.WATER)
    fun getWaterData() : Call<String?>

    @GET
    fun getDetailData(@Url url : String) : Call<String?>

}