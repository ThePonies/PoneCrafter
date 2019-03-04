package org.theponies.ponecrafter.cli

class Cli {
    val commands = mapOf(
        Pair("help", HelpCommand("", "Display command help.", this)),
        Pair("create", CreateCommand("<type> <output folder>", "Create a new content item of the given type.")),
        Pair("package", PackageCommand("<input folder> (<output folder>)", "Create a PCC file from a folder."))
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
            try {
                commands.getValue(command).execute(params)
            } catch (e: CliException) {
                System.err.println(e.message)
            }
        } else {
            println("Invalid command: $command")
            println("Try 'cli help' for a list of commands.")
        }
    }

}