package me.fexclient.externalcommand

import me.fexclient.MinecraftFexClientConfig
import net.minecraft.client.Minecraft


class ExternalCommandFullHealth(isOn: Boolean): ExternalCommand {
    override val action: (mc: Minecraft) -> Unit = { mc ->
        MinecraftFexClientConfig.useFullHealth = isOn
    }

    companion object {
        const val commandName: String = "fullhealth"
        const val commandParams: String = "<onoff>"
        const val commandUsage: String = "Usage: <onoff> must be either 0 (off) or 1 (on)."
    }
}