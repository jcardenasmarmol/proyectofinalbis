package com.marmol.cardenas.jesus.calidaddelaire.webservices

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.marmol.cardenas.jesus.calidaddelaire.model.DatosCalidadAire
import java.lang.reflect.Type

class DeserializerDatos : JsonDeserializer<DatosCalidadAire> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): DatosCalidadAire {
        val id = json?.asJsonObject?.get("data")?.asJsonObject?.get("idx")?.asInt
        val city = json?.asJsonObject?.get("data")?.asJsonObject?.get("city")?.asJsonObject?.get("name")?.asString
        val date = json?.asJsonObject?.get("data")?.asJsonObject?.get("time")?.asJsonObject?.get("s")?.asString
        val keySet = (json?.asJsonObject?.get("data")?.asJsonObject?.get("iaqi") as JsonObject).keySet()
        var hashMap = HashMap<String, Double>()

        keySet.map {
            hashMap[it.toLowerCase()] = json.asJsonObject?.get("data")?.asJsonObject?.get("iaqi")?.asJsonObject?.get(it)?.asJsonObject?.get("v")?.asDouble ?: 0.0
        }
        return DatosCalidadAire(
            id.toString(), city ?: "", date ?: "", hashMap
        )
    }

}