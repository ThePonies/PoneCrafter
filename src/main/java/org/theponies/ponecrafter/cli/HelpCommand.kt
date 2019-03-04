package org.theponies.ponecrafter.cli

class HelpCommand(paramDescription: String, description: String, private val cli: Cli) : Command(paramDescription, description) {

    override fun execute(params: List<String>) {
        println("Usage: <executable> cli <command> [parameters]")
        println("CLI commands:")
        cli.commands.forEach {k, v ->
            println("$k ${v.paramDescription}: ${v.description}")
        }
    }
}
