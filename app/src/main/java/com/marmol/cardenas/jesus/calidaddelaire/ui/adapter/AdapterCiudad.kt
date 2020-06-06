package com.marmol.cardenas.jesus.calidaddelaire.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marmol.cardenas.jesus.calidaddelaire.R
import com.marmol.cardenas.jesus.calidaddelaire.model.CiudadWAQI
import kotlinx.android.synthetic.main.ciudad.view.*

class AdapterCiudad(
    val datos : MutableList<CiudadWAQI>,
    val listener: (CiudadWAQI) -> Unit
) : RecyclerView.Adapter<AdapterCiudad.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idEstacion = itemView.idEstacion
        val nombreEstacion = itemView.nombreEstacion
        val botonAgregar = itemView.btnAgregar
        val aqi = itemView.aqi
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ciudad, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ciudad = datos[position]
        holder.nombreEstacion.text = ciudad.nombre
        holder.idEstacion.text = ciudad.id.toString()
        holder.botonAgregar.setOnClickListener{
            listener(ciudad)
        }
        holder.aqi.text = ciudad.aqi
    }
}