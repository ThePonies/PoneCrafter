package org.theponies.ponecrafter.cli

import java.lang.Exception

class CliException : Exception {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}