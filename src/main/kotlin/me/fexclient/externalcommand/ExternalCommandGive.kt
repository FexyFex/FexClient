package me.fexclient.externalcommand

import net.minecraft.client.Minecraft

class ExternalCommandGive(id: Int, amount: Int): ExternalCommand {
    override val action: (mc: Minecraft) -> Unit = { mc ->
        if (mc.thePlayer != null) {
            mc.thePlayer.dropItem(id, amount)
        }
    }

    companion object {
        const val commandName: String = "give"
        const val commandParams: String = "<itemid> <amount>"
        const val commandUsage: String = "Usage: <itemid> must be a valid item id. <amount> must be between 0 and 127."
    }
}