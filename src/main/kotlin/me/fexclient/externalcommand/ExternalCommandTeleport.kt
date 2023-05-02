package me.fexclient.externalcommand

import net.minecraft.client.Minecraft

class ExternalCommandTeleport(val x: Int, val y: Int, val z: Int): ExternalCommand {
    override val action: (mc: Minecraft) -> Unit = {
        if (it.thePlayer != null && it.playerController != null) {
            it.thePlayer.setPosition(x.toDouble(), y.toDouble(), z.toDouble())
            it.playerController.clickBlock(x, 0, z, 0)
            //it.playerController.sendBlockRemoved(x,y,z,0)
        }
    }

    companion object {
        const val commandName: String = "tp"
        const val commandParams: String = "<x> <y> <z>"
        const val commandUsage: String = "Usage: <x>, <y> and <z> must be a number"
    }
}