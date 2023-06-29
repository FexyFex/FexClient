package me.fexclient.externalcommand.input

import me.fexclient.datatype.Vec3i
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
                if (arguments.size < 2) return "Not enough arguments!"
                val blockIdString = arguments[0]
                val radiusString = arguments[1]
                val blockId: Int
                val radius: Int
                try {
                    blockId = blockIdString.toInt()
                } catch (e: Exception) { return "Invalid Block ID \"$blockIdString\"" }
                try {
                    radius = radiusString.toInt()
                } catch (e: Exception) { return "Invalid Radius \"$radiusString\"" }
                ExternalCommandBlockScan(blockId, radius)
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

            ExternalCommandGive.commandName -> {
                if (arguments.size < 2) return "Not enough arguments!"
                val itemIdString = arguments[0]
                val amountString = arguments[1]
                val itemId: Int
                val amount: Int
                try {
                    itemId = itemIdString.toInt()
                } catch (e: Exception) { return "Invalid Item ID \"$itemIdString\"" }
                try {
                    amount = amountString.toInt()
                } catch (e: Exception) { return "Invalid amount \"$amountString\"" }
                ExternalCommandGive(itemId, amount)
            }

            ExternalCommandFullHealth.commandName -> {
                if (arguments.isEmpty()) return "Not enough arguments!"
                val statusString = arguments[0]
                val status: Boolean
                try {
                    status = statusString.toInt() == 1
                } catch (e: Exception) { return "Invalid status \"$statusString\""}
                ExternalCommandFullHealth(status)
            }

            ExternalCommandPlaceBlock.commandName -> {
                if (arguments.size != 3) return "Unexpected number of arguments!"
                val position = Vec3i(0,0,0)
                try {
                    position.x = arguments[0].toInt()
                    position.y = arguments[1].toInt()
                    position.z = arguments[2].toInt()
                } catch (e: Exception) { return "Non-integer arguments given! $arguments" }
                ExternalCommandPlaceBlock(position)
            }

            ExternalCommandOpenChest.commandName -> {
                if (arguments.size != 3) return "Unexpected number of arguments!"
                val position = Vec3i(0,0,0)
                try {
                    position.x = arguments[0].toInt()
                    position.y = arguments[1].toInt()
                    position.z = arguments[2].toInt()
                }catch (e: Exception) { return "Non-integer arguments given! $arguments" }
                ExternalCommandOpenChest(position)
            }

            else -> UnknownExternalCommand()
        }

        return "Success"
    }
}