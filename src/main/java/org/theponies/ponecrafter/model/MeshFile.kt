package org.theponies.ponecrafter.model

import java.io.File

class MeshFile(val fileName: String, val data: ByteArray) {
    constructor(file: File) : this(file.name, file.readBytes())

    override fun toString(): String = fileName
}