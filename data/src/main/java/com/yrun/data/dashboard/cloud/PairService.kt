package com.yrun.data.dashboard.cloud

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PairService {

    @GET("v1/latest")

    fun loadPair(
        @Query("from") fromCurrency: String,
        @Query("to") toCurrency: String
    ): Call<PairRate>
}

data class PairRate(
    @SerializedName("rates")
    val rates: HashMap<String, Double>
)