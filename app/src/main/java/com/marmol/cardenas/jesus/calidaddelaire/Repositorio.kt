package com.marmol.cardenas.jesus.calidaddelaire

import androidx.lifecycle.MutableLiveData
import com.marmol.cardenas.jesus.calidaddelaire.model.DatosAirQualityModel
import com.marmol.cardenas.jesus.calidaddelaire.model.DatosCiudadesWAQI
import com.marmol.cardenas.jesus.calidaddelaire.webservices.API
import com.marmol.cardenas.jesus.calidaddelaire.webservices.WAQIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class Repositorio {
    companion object{
        private val apiWAQI = API.generateRetrofitWAQIInstance().create<WAQIService>()
        private val apiWAQICiudad = API.generateRetrofitWAQICiudadInstance().create<WAQIService>()
        fun getData(estacion: String): MutableLiveData<DatosAirQualityModel> {
            var datos = MutableLiveData<DatosAirQualityModel>()

            apiWAQI.requestLastDataFromStation(estacion)
                .enqueue(object : Callback<DatosAirQualityModel> {
                    override fun onFailure(call: Call<DatosAirQualityModel>, t: Throwable) {

                    }

                    override fun onResponse(
                        call: Call<DatosAirQualityModel>,
                        response: Response<DatosAirQualityModel>
                    ) {
                        datos.value = response.body()
                    }

                })

            return datos
        }
        fun getCiudades(ciudad: String): MutableLiveData<DatosCiudadesWAQI> {
            var ciudades = MutableLiveData<DatosCiudadesWAQI>()
            apiWAQICiudad.requestDataFromCitySearched(ciudad)
                .enqueue(object : Callback<DatosCiudadesWAQI> {
                    override fun onFailure(call: Call<DatosCiudadesWAQI>, t: Throwable) {
                    }

                    override fun onResponse(
                        call: Call<DatosCiudadesWAQI>,
                        response: Response<DatosCiudadesWAQI>
                    ) {
                        ciudades.value = response.body()
                    }

                })
            return ciudades
        }
    }
}