package me.fexclient.externalcommand

import me.fexclient.datatype.Vec3i
import net.minecraft.client.Minecraft


class ExternalCommandPlaceBlock(position: Vec3i): ExternalCommand {
    override val action: (mc: Minecraft) -> Unit = { mc ->
        mc.playerController.sendPlaceBlock(
            mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentHotBarSlot(),
            position.x, position.y, position.z, 0
        )

    }

    companion object {
        const val commandName: String = "place"
        const val commandParams: String = "<x> <y> <z>"
        const val commandUsage: String = "Usage: all values must be integers."
    }
}