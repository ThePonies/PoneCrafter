package org.theponies.ponecrafter.cli

class Cli {
    fun execute(args: Array<String>) {
        if (args.size < 2) {
            println("Usage: <executable> cli <command> [parameters]")
            println("Try 'cli help' for a list of commands.")
            return
        }
        val params = args.drop(2)
        when (args[1]) {
            "help" -> help()
        }
    }

    private fun help() {
        println("Usage: <executable> cli <command> [parameters]")
        println("CLI commands:")
    }
}