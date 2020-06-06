package com.marmol.cardenas.jesus.calidaddelaire.webservices

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.marmol.cardenas.jesus.calidaddelaire.model.CiudadWAQI
import com.marmol.cardenas.jesus.calidaddelaire.model.DatosCiudadesWAQI
import java.lang.reflect.Type

class DeserializerCiudad : JsonDeserializer<DatosCiudadesWAQI> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): DatosCiudadesWAQI {

        val ciudades = mutableListOf<CiudadWAQI>()

        json?.asJsonObject?.get("data")?.asJsonArray?.map {

                val ciudad = CiudadWAQI(
                    it.asJsonObject.get("uid").asInt,
                    it.asJsonObject.get("station").asJsonObject.get("name").asString,
                    it.asJsonObject.get("aqi").asString
                )
                ciudades.add(ciudad)

        }

        return DatosCiudadesWAQI(
            ciudades
        )
    }
}