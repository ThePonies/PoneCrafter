package org.theponies.ponecrafter.cli

abstract class Command(val paramDescription: String, val description: String) {
    abstract fun execute(params: List<String>)
}
