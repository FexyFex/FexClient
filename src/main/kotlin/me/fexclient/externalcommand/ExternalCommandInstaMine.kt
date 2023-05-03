package me.fexclient.externalcommand

import me.fexclient.MinecraftFexClientConfig
import net.minecraft.client.Minecraft

class ExternalCommandInstaMine(isOn: Boolean): ExternalCommand {
    override val action: (mc: Minecraft) -> Unit = {
        MinecraftFexClientConfig.useInstaMine = isOn
    }

    companion object {
        const val commandName: String = "instamine"
        const val commandParams: String = "<onoff>"
        const val commandUsage: String = "Usage: <onoff> must be either 0 (off) or 1 (on). Does not work on servers."
    }
}