package org.theponies.ponecrafter.model;

import tornadofx.JsonModel

abstract class BaseModel : JsonModel {
    abstract fun getTypeName(): String

    abstract fun getModelName(): String
}
