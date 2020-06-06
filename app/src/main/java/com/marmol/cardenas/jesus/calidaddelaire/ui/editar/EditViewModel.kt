package com.marmol.cardenas.jesus.calidaddelaire.ui.editar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marmol.cardenas.jesus.calidaddelaire.Repositorio
import com.marmol.cardenas.jesus.calidaddelaire.model.DatosCiudadesWAQI

class EditViewModel : ViewModel() {

    fun getCiudades(ciudad: String) : MutableLiveData<DatosCiudadesWAQI> {
        return Repositorio.getCiudades(ciudad)
    }

    class EditViewModelFactory() : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return EditViewModel() as T
        }
    }
}