package com.marmol.cardenas.jesus.calidaddelaire.webservices

import androidx.lifecycle.MutableLiveData
import com.marmol.cardenas.jesus.calidaddelaire.model.DatosCalidadAire
import com.marmol.cardenas.jesus.calidaddelaire.model.DatosCiudadesWAQI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class Repositorio {
    companion object{
        private val apiWAQI = API.generateRetrofitWAQIInstance().create<WAQIService>()
        private val apiWAQICiudad = API.generateRetrofitWAQICiudadInstance().create<WAQIService>()
        fun getData(estacion: String): MutableLiveData<DatosCalidadAire> {
            var datos = MutableLiveData<DatosCalidadAire>()

            apiWAQI.requestLastDataFromStation(estacion)
                .enqueue(object : Callback<DatosCalidadAire> {
                    override fun onFailure(call: Call<DatosCalidadAire>, t: Throwable) {

                    }

                    override fun onResponse(
                        call: Call<DatosCalidadAire>,
                        response: Response<DatosCalidadAire>
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