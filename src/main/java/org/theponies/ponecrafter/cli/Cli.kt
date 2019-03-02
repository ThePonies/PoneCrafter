package org.theponies.ponecrafter.cli

class Cli {
    private val commands = mapOf(
        Pair("help", Command("", "Display command help.", ::help)),
        Pair("create", Command("<input file> (<output file>)", "Create a PCC file from a folder.", ::create))
    )

    fun execute(args: Array<String>) {
        if (args.size < 2) {
            println("Usage: <executable> cli <command> [parameters]")
            println("Try 'cli help' for a list of commands.")
            return
        }
        val command = args[1]
        val params = args.drop(2)
        if (commands.containsKey(command)) {
            commands.getValue(command).callback.invoke(params)
        } else {
            println("Invalid command: $command")
            println("Try 'cli help' for a list of commands.")
        }
    }

    private fun help(params: List<String>) {
        println("Usage: <executable> cli <command> [parameters]")
        println("CLI commands:")
        commands.forEach {k, v ->
            println("$k ${v.paramDescription}: ${v.description}")
        }
    }

    private fun create(params: List<String>) {

    }

    data class Command(val paramDescription: String, val description: String, val callback: (params: List<String>) -> Any)
}