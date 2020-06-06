package com.marmol.cardenas.jesus.calidaddelaire.ui.editar

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marmol.cardenas.jesus.calidaddelaire.R
import com.marmol.cardenas.jesus.calidaddelaire.model.CiudadWAQI
import com.marmol.cardenas.jesus.calidaddelaire.model.DatosCiudadesWAQI
import com.marmol.cardenas.jesus.calidaddelaire.ui.adapter.AdapterCiudad
import kotlinx.android.synthetic.main.fragment_edit.*

class EditFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterCiudad
    private lateinit var editViewModel :EditViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editViewModel = EditViewModel.EditViewModelFactory().create(EditViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_edit, container, false)
        recyclerView = view.findViewById(R.id.mainRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter =
            AdapterCiudad(mutableListOf()){
                agregar(it)
            }

        recyclerView.adapter = adapter

        val searchView = view.findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                actualizarLista()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                actualizarLista()
                return true
            }
        })

        return view
    }

    private fun actualizarLista() {
        editViewModel.getCiudades(searchView.query.toString()).observe(viewLifecycleOwner, Observer {
            adapter.datos.clear()
            adapter.datos.addAll(it.ciudades)
            adapter.notifyDataSetChanged()
        })
    }

    private fun agregar(ciudad : CiudadWAQI){
        val preferences = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val estaciones = mutableSetOf<String>()
        preferences?.let {
            it.getStringSet("estaciones", null)?.map {
                estaciones.add(it)
            }
        }
        if (!estaciones.contains("@"+ciudad.id.toString())) estaciones?.add("@"+ciudad.id.toString())
        else Toast.makeText(context, "La ciudad ya esta agregada", Toast.LENGTH_LONG).show()
        val edit = preferences?.edit()
        edit?.putStringSet("estaciones", estaciones)
        edit?.apply()
    }

}