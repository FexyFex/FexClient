package me.fexclient.externalcommand

import net.minecraft.client.Minecraft


class ExternalCommandBlockScan(blockId: Int): ExternalCommand {
    override val action: (mc: Minecraft) -> Unit = { mc ->
        if (mc.theWorld != null && mc.thePlayer != null) {
            val chunk = mc.theWorld.getChunkFromChunkCoords(mc.thePlayer.chunkCoordX, mc.thePlayer.chunkCoordZ)

            repeat(16) { x ->
                repeat(128) { y ->
                    repeat(16) { z ->
                        val id = chunk.getBlockID(x,y,z)
                        if (id == blockId)
                            println("${x + chunk.xPosition * 16},$y,${z + chunk.zPosition * 16}")
                    }
                }
            }
        }
    }

    companion object {
        const val commandName: String = "scan"
        const val commandParams: String = "<blockid>"
        const val commandUsage: String = "Usage: <blockid> must be a valid block id"
    }
}