package com.marmol.cardenas.jesus.calidaddelaire.webservices

import com.marmol.cardenas.jesus.calidaddelaire.model.DatosAirQualityModel
import com.marmol.cardenas.jesus.calidaddelaire.model.DatosCiudadesWAQI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WAQIService {
    @GET("feed/{estacion}/?token=23a008e119646d0df6d7b1b54467a50ddc45e9f3")
    fun requestLastDataFromStation(@Path("estacion") estacion : String) : Call<DatosAirQualityModel>

    @GET("search/?token=23a008e119646d0df6d7b1b54467a50ddc45e9f3")
    fun requestDataFromCitySearched(@Query("keyword") ciudad: String) : Call<DatosCiudadesWAQI>
}