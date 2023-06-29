package me.fexclient.externalcommand

import me.fexclient.datatype.Vec3i
import net.minecraft.client.Minecraft
import net.minecraft.src.entity.TileEntityChest

class ExternalCommandOpenChest(pos: Vec3i): ExternalCommand {
    override val action: (mc: Minecraft) -> Unit = {
        if (it.theWorld != null) {
            val tileEntity = it.theWorld.getBlockTileEntity(pos.x, pos.y, pos.z)
            if (tileEntity == null)
                println("nothing there")
            else {
                val chest = tileEntity as? TileEntityChest
                if (chest != null) {
                    it.thePlayer.displayGUIChest(chest)
                }
            }
        }
    }

    companion object {
        const val commandName: String = "open"
        const val commandParams: String = "<x> <y> <z>"
        const val commandUsage: String = "Usage: <x>, <y> and <z> must all be a number."
    }
}