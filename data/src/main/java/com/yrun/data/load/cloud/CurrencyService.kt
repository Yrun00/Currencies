package com.yrun.data.load.cloud

import retrofit2.Call
import retrofit2.http.GET

interface CurrencyService {

    @GET("v1/currencies")
    fun loadCurrencies() : Call<HashMap<String,String>>

}