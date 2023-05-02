package me.fexclient

import me.fexclient.externalcommand.ExternalCommand
import me.fexclient.externalcommand.input.UserExternalCommandInputApp
import net.minecraft.client.Minecraft
import net.minecraft.src.Block
import net.minecraft.src.block.BlockTNT
import net.minecraft.src.item.Item
import net.minecraft.src.item.ItemStack
import kotlin.math.roundToInt


object MinecraftFexClientInjectorApp {
    private val externalCommands = mutableListOf<ExternalCommand>()
    private val commandInput = UserExternalCommandInputApp(externalCommands)

    init {
        Thread(commandInput, "ExternalCommandInputThread").start()
    }

    fun tick(mc: Minecraft) {
        val cleanupList = mutableListOf<ExternalCommand>()
        try {
            externalCommands.forEach {
                it.action(mc)
                cleanupList.add(it)
            }
        } catch(_: ConcurrentModificationException) {
            // It's possible that this will be thrown when the command input thread modifies this list
            // during the forEach, but it's not a big deal. We will simply try again next tick.
        }
        cleanupList.forEach { externalCommands.remove(it) }
        cleanupList.clear()

        if (mc.theWorld != null) {
            val playerPosX = mc.thePlayer.posX.roundToInt()
            val playerPosY = mc.thePlayer.posY.roundToInt()
            val playerPosZ = mc.thePlayer.posZ.roundToInt()
            val chunk = mc.theWorld.getChunkFromBlockCoords(playerPosX, playerPosZ)

            val index = chunk.blocks.indexOfFirst { it.toInt() == Block.dirt.blockID }
            val x = (index shr 11) + (chunk.xPosition * 16)
            val y = (index and 127)
            val z = ((index and 1920) shr 7) + (chunk.zPosition)
            println("$x,$y,$z")
            mc.playerController.sendBlockRemoving(x,y,z,0)
            mc.playerController.sendPlaceBlock(mc.thePlayer, mc.theWorld, ItemStack(Block.tnt, 1), x, y, z, 0)
        }
    }


    fun destroy() {
        commandInput.destroy()
    }
}