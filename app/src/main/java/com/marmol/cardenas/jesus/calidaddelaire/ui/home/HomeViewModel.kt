package com.marmol.cardenas.jesus.calidaddelaire.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marmol.cardenas.jesus.calidaddelaire.Repositorio
import com.marmol.cardenas.jesus.calidaddelaire.model.DatosAirQualityModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun getDatos(estaciones : List<String>) : List<MutableLiveData<DatosAirQualityModel>> {
        var list = mutableListOf<MutableLiveData<DatosAirQualityModel>>()
        estaciones.map {
            list.add(Repositorio.getData(it))
        }
        return list
    }

    class HomeViewModelFactory() : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel() as T
        }
    }

}