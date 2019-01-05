package org.theponies.ponecrafter.exceptions

class SavePackageException : Exception {
    constructor(message: String) : super(message) {}

    constructor(message: String, cause: Throwable) : super(message, cause) {}
}
