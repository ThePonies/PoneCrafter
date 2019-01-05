package org.theponies.ponecrafter.model

import org.json.JSONException
import org.json.JSONObject

import java.util.UUID

class Floor(private val uuid: UUID, val name: String, val description: String, val price: Int) : JsonModel {

    @Throws(JSONException::class)
    constructor(jsonObject: JSONObject) : this(
        UUID.fromString(jsonObject.getString(GUID)),
        jsonObject.getString(NAME),
        jsonObject.getString(DESCRIPTION),
        jsonObject.getInt(PRICE)
    ) {
    }

    constructor(json: String) : this(JSONObject(json)) {}

    override fun toJsonObject(): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put(GUID, uuid)
        jsonObject.put(NAME, name)
        jsonObject.put(DESCRIPTION, description)
        jsonObject.put(PRICE, price)
        return jsonObject
    }

    companion object {
        val TEXTURE_SIZE = 128
        private val GUID = "guid"
        private val NAME = "name"
        private val DESCRIPTION = "description"
        private val PRICE = "price"
    }
}
