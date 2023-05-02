package me.fexclient.externalcommand

import me.fexclient.MinecraftFexClientConfig
import net.minecraft.client.Minecraft


class ExternalCommandSetStaticTime(timeValue: Long): ExternalCommand {
    override val action: (mc: Minecraft) -> Unit = {
        if (timeValue == -1L) {
            MinecraftFexClientConfig.useStaticTime = false
            MinecraftFexClientConfig.staticTime = 0L
        } else {
            if (timeValue >= 0L) {
                MinecraftFexClientConfig.useStaticTime = true
                MinecraftFexClientConfig.staticTime = timeValue
            }
        }
    }

    override fun getCommandName(): String = ExternalCommandSetBrightness.commandName

    companion object {
        const val commandName: String = "statictime"
        const val commandParams: String = "<value>"
        const val commandUsage: String = "Usage: <value> must be a positive number. Can be -1 to disable the hack."
    }
}