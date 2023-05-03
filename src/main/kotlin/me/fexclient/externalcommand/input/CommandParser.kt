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

            ExternalCommandTeleport.commandName -> {
                if (arguments.size < 3) return "Not enough arguments!"
                val xString = arguments[0]
                val yString = arguments[1]
                val zString = arguments[2]
                val x: Int
                val y: Int
                val z: Int
                try {
                    x = xString.toInt()
                    y = yString.toInt()
                    z = zString.toInt()
                } catch (e: Exception) {
                    return "Invalid coords \"$xString,$yString,$zString\""
                }
                ExternalCommandTeleport(x,y,z)
            }

            ExternalCommandBlockScan.commandName -> {
                if (arguments.isEmpty()) return "Not enough arguments!"
                val blockIdString = arguments[0]
                val blockId: Int
                try {
                    blockId = blockIdString.toInt()
                } catch (e: Exception) { return "Invalid Block ID \"$blockIdString\"" }
                ExternalCommandBlockScan(blockId)
            }

            ExternalCommandInstaMine.commandName -> {
                if (arguments.isEmpty()) return "Not enough arguments!"
                val statusString = arguments[0]
                val status: Boolean
                try {
                    status = statusString.toInt() == 1
                } catch (e: Exception) { return "Invalid status \"$statusString\""}
                ExternalCommandInstaMine(status)
            }

            else -> UnknownExternalCommand()
        }

        return "Success"
    }
}