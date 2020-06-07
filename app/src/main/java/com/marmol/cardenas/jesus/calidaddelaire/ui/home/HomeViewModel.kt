package com.marmol.cardenas.jesus.calidaddelaire.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marmol.cardenas.jesus.calidaddelaire.webservices.Repositorio
import com.marmol.cardenas.jesus.calidaddelaire.model.DatosCalidadAire

class HomeViewModel : ViewModel() {


    fun getDatos(estaciones : List<String>) : List<MutableLiveData<DatosCalidadAire>> {
        var list = mutableListOf<MutableLiveData<DatosCalidadAire>>()
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