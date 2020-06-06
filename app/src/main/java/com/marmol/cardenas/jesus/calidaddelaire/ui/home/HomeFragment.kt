package com.marmol.cardenas.jesus.calidaddelaire.ui.home

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marmol.cardenas.jesus.calidaddelaire.ui.adapter.AdapterDatosCalidadAire
import com.marmol.cardenas.jesus.calidaddelaire.R
import com.marmol.cardenas.jesus.calidaddelaire.model.DatosAirQualityModel

class HomeFragment : Fragment() {

    private lateinit var btnAgregar : Button
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter: AdapterDatosCalidadAire
    private var estaciones = mutableListOf<String>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        btnAgregar = root.findViewById(R.id.irAAgregar)
        btnAgregar.setOnClickListener {
            findNavController().navigate(R.id.go_to_agregar)
        }
        recyclerView = root.findViewById(R.id.mainRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter =
            AdapterDatosCalidadAire(mutableListOf()){
                DialogoAlerta("¿Quieres eliminar la estacion de ${it.ciudad}?") {
                    eliminar(it)
                }.show(parentFragmentManager, "Eliminar")
            }
        recyclerView.adapter = adapter
        homeViewModel = HomeViewModel.HomeViewModelFactory().create(HomeViewModel::class.java)

        obtenerListaEstaciones()

        if (estaciones.isNotEmpty()){
            btnAgregar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

        homeViewModel.getDatos(estaciones).map {
            it.observe(viewLifecycleOwner, Observer { dato -> actualizar(dato) })
        }
        return root
    }

    private fun eliminar(it: DatosAirQualityModel) {
        estaciones.remove("@${it.id}")
        actualizarPreferencias()
        adapter.data.remove(it)
        adapter.notifyDataSetChanged()
        if (estaciones.isEmpty()){
            btnAgregar.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }
    }

    private fun obtenerListaEstaciones() {
        var preferences = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        preferences?.let {
            it.getStringSet("estaciones", setOf<String>())?.map {estacion ->
                if (!estaciones.contains(estacion)) estaciones.add(estacion)
            }
        }
    }

    private fun actualizarPreferencias() {
        val preferences = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val edit = preferences?.edit()
        edit?.putStringSet("estaciones", estaciones.toMutableSet())
        edit?.apply()
    }

    private fun actualizar(it : DatosAirQualityModel){
        adapter.data.add(it)
        adapter.notifyDataSetChanged()
    }

    class DialogoAlerta(var mensaje : String, var listener : () -> Unit) : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            var builder = AlertDialog.Builder(requireContext())
            builder.setMessage(mensaje)
                .setTitle("")
                .setPositiveButton("Ok") { dialog, _ ->
                    listener()
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.cancel()
                }
            return builder.create()
        }
    }
}
