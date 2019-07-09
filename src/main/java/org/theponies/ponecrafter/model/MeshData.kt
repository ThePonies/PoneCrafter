package org.theponies.ponecrafter.model

import java.io.InputStream

class MeshData(val data: ByteArray) {
    constructor(inputStream: InputStream) : this(inputStream.use { it.readBytes() })
}