package me.fexclient

import me.fexclient.externalcommand.ExternalCommand
import me.fexclient.externalcommand.input.UserExternalCommandInputApp
import net.minecraft.client.Minecraft
import net.minecraft.src.Block
import net.minecraft.src.datatype.Vec3D
import net.minecraft.src.gui.GuiMainMenu
import org.lwjgl.input.Keyboard
import java.io.File
import kotlin.math.roundToInt


object MinecraftFexClientInjectorApp {
    private val externalCommands = mutableListOf<ExternalCommand>()
    private val commandInput = UserExternalCommandInputApp(externalCommands)

    private lateinit var tunneler: AutoTunneler
    private lateinit var mc: Minecraft


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

        if (Keyboard.isKeyDown(Keyboard.KEY_L) && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
            if (MinecraftFexClientConfig.tunnelerActive) {
                tunneler.stop()
                MinecraftFexClientConfig.tunnelerActive = false
            } else {
                MinecraftFexClientConfig.tunnelerActive = true
            }
        }

        if (MinecraftFexClientConfig.tunnelerActive && mc.theWorld != null && mc.thePlayer != null) {
            tunneler.tick(delta)
        }

        if (MinecraftFexClientConfig.requestedLogout) {
            MinecraftFexClientConfig.resetToDefault()

            if (mc.isMultiplayerWorld)
                mc.theWorld.sendQuittingDisconnectingPacket()
            mc.func_6261_a(null)
            mc.displayGuiScreen(GuiMainMenu())

            val minecraftDir = Minecraft.getMinecraftDir()
            val minecraftDirPath = minecraftDir.toPath().toString()
            val logDir = "$minecraftDirPath/logs/"
            val logDirFile = File(logDir)

            if (!logDirFile.exists()) logDirFile.mkdir()

            val timeStamp = System.currentTimeMillis()
            val fileName = "tunnellog_$timeStamp.txt"
            val filePath = logDir + fileName

            AutoTunneler.Companion.Log.writeToFile(filePath)
        }
    }

    fun frameUpdate() {
        if (MinecraftFexClientConfig.tunnelerActive && mc.theWorld != null && mc.thePlayer != null) {
            tunneler.renderInfoGui()
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


    fun destroy() {
        commandInput.destroy()
    }
}