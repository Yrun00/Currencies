package com.yrun.data.load.cloud

import retrofit2.Call
import retrofit2.http.GET

interface CurrencyService {

    @GET("currencies")
    fun loadCurrencies() : Call<HashMap<String,String>>

}