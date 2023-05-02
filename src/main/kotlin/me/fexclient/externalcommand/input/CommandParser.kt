package me.fexclient.externalcommand.input

import me.fexclient.externalcommand.*


class CommandParser(private val rawCommandString: String) {
    var command: ExternalCommand? = null
    val error: String

    init {
        error = parse()
    }

    private fun parse(): String {
        val tokens = rawCommandString.split(" ")
        if (tokens.isEmpty()) return "Nothing entered"
        val commandName = tokens[0]
        val arguments = tokens.subList(1, tokens.size)

        this.command = when (commandName) {
            ExternalCommandSetTime.commandName -> {
                if (arguments.isEmpty()) return "Lacking arguments!"
                val timeValueString: String = arguments[0]
                val timeValue: Long
                try {
                    timeValue = timeValueString.toLong()
                } catch (e: Exception) {
                    return "Invalid time value \"$timeValueString\""
                }
                ExternalCommandSetTime(timeValue)
            }

            ExternalCommandSetStaticTime.commandName -> {
                if (arguments.isEmpty()) return "Lacking arguments!"
                val timeValueString = arguments[0]
                val timeValue: Long
                try {
                    timeValue = timeValueString.toLong()
                } catch (e: Exception) {
                    return "Invalid time value \"$timeValueString\""
                }
                ExternalCommandSetStaticTime(timeValue)
            }

            ExternalCommandSetBrightness.commandName -> {
                if (arguments.isEmpty()) return "Lacking arguments!"
                val brightnessValueString = arguments[0]
                val brightnessValue: Float
                try {
                    brightnessValue = brightnessValueString.toFloat()
                } catch (e: Exception) {
                    return "Invalid brightness value \"$brightnessValueString\""
                }
                ExternalCommandSetBrightness(brightnessValue)
            }

            else -> UnknownExternalCommand()
        }

        return "Success"
    }
}