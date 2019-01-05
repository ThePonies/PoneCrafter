package org.theponies.ponecrafter.model

import org.json.JSONObject

interface JsonModel {
    fun toJsonObject(): JSONObject

    fun toJson(): String {
        return toJsonObject().toString()
    }
}
