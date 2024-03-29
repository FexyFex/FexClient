package me.fexclient

import me.fexclient.datatype.Vec3i
import net.minecraft.client.Minecraft
import net.minecraft.src.Block
import net.minecraft.src.gui.GuiMainMenu
import org.lwjgl.input.Keyboard
import java.io.File
import kotlin.math.roundToLong


object MinecraftFexClientInjectorApp {
    private lateinit var tunneler: AutoTunneler
    private lateinit var mc: Minecraft
    private lateinit var hackMenuOverlay: MinecraftFexClientOverlay

    private var lastPosHit: Vec3i = Vec3i(0,0,0)


    fun init(mc: Minecraft) {
        tunneler = AutoTunneler(mc)
        this.mc = mc
        this.hackMenuOverlay = MinecraftFexClientOverlay(mc)
    }


    fun preWorldTick(mc: Minecraft) {
        if (MinecraftFexClientConfig.useFullHealth && mc.thePlayer != null)
            mc.thePlayer.setHealth(20)
    }

    fun tick(elapsedTimeInNanos: Float) {
        FexInputHandler.receiveInputs()

        if (FexInputHandler.isKeyJustPressed(Keyboard.KEY_X))
            toggleXRay()

        if (FexInputHandler.isKeyJustPressed(Keyboard.KEY_N))
            MinecraftFexClientConfig.doNoFall = !MinecraftFexClientConfig.doNoFall

        if (FexInputHandler.isKeyJustPressed(Keyboard.KEY_C)) {
            MinecraftFexClientConfig.useUniformBrightness = !MinecraftFexClientConfig.useUniformBrightness
            MinecraftFexClientConfig.uniformBrightness = 1.0f
            mc.gameSettings.renderDistance = 1;
            mc.renderGlobal.renderDistance = 3;
        }

        if (FexInputHandler.isKeyJustPressed(Keyboard.KEY_V)) {
            MinecraftFexClientConfig.useStaticTime = !MinecraftFexClientConfig.useStaticTime
            MinecraftFexClientConfig.staticTime = 0L
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && FexInputHandler.isKeyJustPressed(Keyboard.KEY_H))
            hackMenuOverlay.toggleVisible()

        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && FexInputHandler.isKeyJustPressed(Keyboard.KEY_L)) {
            if (MinecraftFexClientConfig.tunnelerActive) {
                tunneler.stop()
                MinecraftFexClientConfig.tunnelerActive = false
            } else {
                MinecraftFexClientConfig.tunnelerActive = true
            }
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) xor Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
            shiftStaticTime() else { previousInputTime = -1; shiftModifierTime = 1f }

        if (Keyboard.isKeyDown(Keyboard.KEY_UP) xor Keyboard.isKeyDown(Keyboard.KEY_DOWN))
            shiftCloudHeight() else { previousInputCloud = -1; shiftModifierCloud = 1f }

        val delta = (elapsedTimeInNanos / 1_000_000_000.0).toFloat()

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
        hackMenuOverlay.draw()
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


    fun onBlockHit(mc: Minecraft, block: Block, position: Vec3i, side: Int) {
        lastPosHit = position
        if (MinecraftFexClientConfig.useInstaMine && mc.theWorld != null && mc.thePlayer != null) {
            mc.playerController.sendBlockRemoved(position.x, position.y, position.z, side)
        }
    }

    private fun toggleXRay() {
        MinecraftFexClientConfig.useXRay = !MinecraftFexClientConfig.useXRay
        mc.gameSettings.renderDistance = 0;
        mc.renderGlobal.renderDistance = 3;
    }

    private var previousInputTime = -1
    private var shiftModifierTime = 1f
    private fun shiftStaticTime() {
        val left = Keyboard.isKeyDown(Keyboard.KEY_LEFT)
        val right = Keyboard.isKeyDown(Keyboard.KEY_RIGHT)

        if (left) {
            if (previousInputTime == 0) shiftModifierTime *= 1.04f
            MinecraftFexClientConfig.staticTime -= (10 * shiftModifierTime).roundToLong()
            previousInputTime = 0
        }

        if (right) {
            if (previousInputTime == 1) shiftModifierTime *= 1.04f
            MinecraftFexClientConfig.staticTime += (10 * shiftModifierTime).roundToLong()
            previousInputTime = 1
        }
    }

    private var previousInputCloud = -1
    private var shiftModifierCloud = 1f
    private fun shiftCloudHeight() {
        val up = Keyboard.isKeyDown(Keyboard.KEY_UP)
        val down = Keyboard.isKeyDown(Keyboard.KEY_DOWN)

        if (up) {
            if (previousInputCloud == 1) shiftModifierTime *= 1.04f
            MinecraftFexClientConfig.cloudHeightModifier += shiftModifierCloud
            previousInputCloud = 1
        }

        if (down) {
            if (previousInputCloud == 0) shiftModifierTime *= 1.04f
            MinecraftFexClientConfig.cloudHeightModifier -= shiftModifierCloud
            previousInputCloud = 0
        }
    }
}