package me.fexclient.externalcommand

import net.minecraft.client.Minecraft


class ExternalCommandBlockScan(blockId: Int, radius: Int): ExternalCommand {
    override val action: (mc: Minecraft) -> Unit = { mc ->
        if (mc.theWorld != null && mc.thePlayer != null) {
            for (xChunkPos in (mc.thePlayer.chunkCoordX - radius) .. (mc.thePlayer.chunkCoordX + radius)) {
                for (zChunkPos in (mc.thePlayer.chunkCoordZ - radius) .. (mc.thePlayer.chunkCoordZ + radius)) {

                    val targetChunk = mc.theWorld.getChunkFromChunkCoords(xChunkPos, zChunkPos)
                    repeat(16) { x ->
                        repeat(128) { y ->
                            repeat(16) { z ->
                                val id = targetChunk.getBlockID(x,y,z)
                                if (id == blockId)
                                    println("${x + targetChunk.xPosition * 16},$y,${z + targetChunk.zPosition * 16}")
                            }
                        }
                    }

                }
            }
        }
    }

    companion object {
        const val commandName: String = "scan"
        const val commandParams: String = "<blockid> <radius>"
        const val commandUsage: String = "Usage: <blockid> must be a valid block id. <radius> specifies the range of the scan in chunks and must be 0 or higher"
    }
}