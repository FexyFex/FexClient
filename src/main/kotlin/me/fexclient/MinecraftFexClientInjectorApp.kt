package me.fexclient

import me.fexclient.externalcommand.ExternalCommand
import me.fexclient.externalcommand.input.UserExternalCommandInputApp
import net.minecraft.client.Minecraft
import net.minecraft.src.Block
import net.minecraft.src.datatype.Vec3D
import org.lwjgl.input.Keyboard
import kotlin.math.roundToInt


object MinecraftFexClientInjectorApp {
    private val externalCommands = mutableListOf<ExternalCommand>()
    private val commandInput = UserExternalCommandInputApp(externalCommands)

    private lateinit var tunneler: AutoTunneler
    private lateinit var mc: Minecraft

    private val pressedKeys = mutableSetOf<Int>()
    private val downKeys = mutableSetOf<Int>()


    fun init(mc: Minecraft) {
        tunneler = AutoTunneler(mc)
        this.mc = mc
        Thread(commandInput, "ExternalCommandInputThread").start()
    }


    fun preWorldTick(mc: Minecraft) {
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

        if (MinecraftFexClientConfig.useFullHealth && mc.thePlayer != null)
            mc.thePlayer.setHealth(20)
    }

    fun tick(elapsedTimeInNanos: Float) {
        val delta = elapsedTimeInNanos / 1_000_000_000f

        manageInput()

        if (keyPressed(Keyboard.KEY_L))
            MinecraftFexClientConfig.tunnelerActive = !MinecraftFexClientConfig.tunnelerActive

        if (MinecraftFexClientConfig.tunnelerActive && mc.theWorld != null && mc.thePlayer != null) {
            tunneler.tick(delta)
        }
    }

    fun postWorldTick(mc: Minecraft) {
        if (MinecraftFexClientConfig.useStaticTime && mc.theWorld != null) {
            mc.theWorld.setWorldTime(MinecraftFexClientConfig.staticTime)
        }

        if (MinecraftFexClientConfig.useFullHealth && mc.thePlayer != null)
            mc.thePlayer.setHealth(20)
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

    private fun keyPressed(key: Int): Boolean {
        return key in pressedKeys
    }

    private fun manageInput() {
        val key = Keyboard.getEventKey()
        if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
            if (Keyboard.KEY_L !in downKeys)
                pressedKeys.add(Keyboard.KEY_L)
            else
                pressedKeys.remove(Keyboard.KEY_L)

            downKeys.add(Keyboard.KEY_L)
        } else {
            downKeys.remove(Keyboard.KEY_L)
        }
    }


    fun destroy() {
        commandInput.destroy()
    }
}