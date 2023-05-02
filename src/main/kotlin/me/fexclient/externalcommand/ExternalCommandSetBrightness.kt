package me.fexclient.externalcommand

import me.fexclient.MinecraftFexClientConfig
import net.minecraft.client.Minecraft


class ExternalCommandSetBrightness(fullBrightValue: Float): ExternalCommand {
    override val action: (mc: Minecraft) -> Unit = {
        if (fullBrightValue == -1f) {
            MinecraftFexClientConfig.useUniformBrightness = false
            MinecraftFexClientConfig.uniformBrightness = 1f
        } else {
            if (fullBrightValue < 0.0f || fullBrightValue > 1.0f) {
                println("Wrong usage of fullbright command")
            } else {
                MinecraftFexClientConfig.useUniformBrightness = true
                MinecraftFexClientConfig.uniformBrightness = fullBrightValue
            }
        }
    }

    companion object {
        const val commandName: String = "brightness"
        const val commandParams: String = "<value>"
        const val commandUsage: String = "Usage: <value> must be a number between 0 and 1. Can be -1 to disable the hack"
    }
}