package me.fexclient.externalcommand

import net.minecraft.client.Minecraft

class ExternalCommandSetTime(timeValue: Long): ExternalCommand {
    override val action: (mc: Minecraft) -> Unit = {
        if (it.theWorld != null) it.theWorld.setWorldTime(timeValue)
    }

    override fun getCommandName(): String = ExternalCommandSetBrightness.commandName

    companion object {
        const val commandName: String = "time"
        const val commandParams: String = "<value>"
        const val commandUsage: String = "Usage: <value> must be a positive number"
    }
}