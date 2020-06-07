package com.marmol.cardenas.jesus.calidaddelaire.webservices

import com.google.gson.GsonBuilder
import com.marmol.cardenas.jesus.calidaddelaire.model.DatosCalidadAire
import com.marmol.cardenas.jesus.calidaddelaire.model.DatosCiudadesWAQI
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class API {
    companion object{
        private fun generateOkHttpBuilder() : OkHttpClient {
            return OkHttpClient().newBuilder().build()
        }
        fun generateRetrofitWAQIInstance() : Retrofit {

            val builder = GsonBuilder()
            builder.registerTypeAdapter(
                DatosCalidadAire::class.java,
                DeserializerDatos()
            )

            return Retrofit.Builder()
                .baseUrl("https://api.waqi.info/")
                .client(generateOkHttpBuilder())
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .build()
        }
        fun generateRetrofitWAQICiudadInstance() : Retrofit {

            val builder = GsonBuilder()
            builder.registerTypeAdapter(
                DatosCiudadesWAQI::class.java,
                DeserializerCiudad()
            )

            return Retrofit.Builder()
                .baseUrl("https://api.waqi.info/")
                .client(generateOkHttpBuilder())
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .build()
        }
    }
}