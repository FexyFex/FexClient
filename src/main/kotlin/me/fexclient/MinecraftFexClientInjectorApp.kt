package me.fexclient

import me.fexclient.externalcommand.ExternalCommand
import me.fexclient.externalcommand.input.UserExternalCommandInputApp
import net.minecraft.client.Minecraft
import net.minecraft.src.Block
import net.minecraft.src.datatype.Vec3D
import kotlin.math.roundToInt


object MinecraftFexClientInjectorApp {
    private val externalCommands = mutableListOf<ExternalCommand>()
    private val commandInput = UserExternalCommandInputApp(externalCommands)

    init {
        Thread(commandInput, "ExternalCommandInputThread").start()
    }

    fun preWorldtick(mc: Minecraft) {
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
    }


    fun postWorldTick(mc: Minecraft) {
        if (MinecraftFexClientConfig.useStaticTime && mc.theWorld != null) {
            mc.theWorld.setWorldTime(MinecraftFexClientConfig.staticTime)
        }
    }


    fun onBlockHit(mc: Minecraft, block: Block, position: Vec3D, side: Int) {
        if (MinecraftFexClientConfig.useInstaMine && mc.theWorld != null && mc.thePlayer != null) {
            mc.playerController.sendBlockRemoved(
                position.xCoord.roundToInt(),
                position.yCoord.roundToInt(),
                position.zCoord.roundToInt(),
                side
            )
        }
    }



    fun destroy() {
        commandInput.destroy()
    }
}