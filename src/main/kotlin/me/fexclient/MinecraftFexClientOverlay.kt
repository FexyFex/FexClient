package me.fexclient

import net.minecraft.client.Minecraft
import net.minecraft.src.entity.EntityPlayer
import org.lwjgl.input.Keyboard
import org.lwjgl.opengl.Display
import kotlin.math.roundToInt


class MinecraftFexClientOverlay(private val mc: Minecraft) {
    private val fontRenderer = mc.fontRenderer
    private var isHidden = false

    fun draw() {
        if (mc.theWorld == null) return
        fontRenderer.drawStringWithShadow("Hack Menu", 2, 2, 0xFFFFFF)
        if (isHidden) {
            fontRenderer.drawString("([CTRL + H] to show)", 58, 2, 0xFF1111)
            return
        }
        fontRenderer.drawString("([CTRL + H] to hide)", 58, 2, 0x11FF11)

        val width = Display.getWidth()
        val height = Display.getHeight()

        // XRAY
        fontRenderer.drawString("X-Ray", 2, 13, 0xFFFFFF)
        fontRenderer.drawString("[X]", 36, 13, redOrGreen(MinecraftFexClientConfig.useXRay))
        // XRAY

        // FULLBRIGHT
        fontRenderer.drawString("Fullbright", 2, 24, 0xFFFFFF)
        fontRenderer.drawString("[C]", 54, 24, redOrGreen(MinecraftFexClientConfig.useUniformBrightness))
        // FULLBRIGHT

        // STATIC TIME
        fontRenderer.drawString("Static Time", 2, 35, 0xFFFFFF)
        fontRenderer.drawString("[V]", 58, 35, redOrGreen(MinecraftFexClientConfig.useStaticTime))
        if (MinecraftFexClientConfig.useStaticTime) {
            fontRenderer.drawString("Time:", 76, 35, 0xFFFFFF)
            fontRenderer.drawString("${MinecraftFexClientConfig.staticTime}", 102, 35, 0xFFFFFF)

            val offset = (MinecraftFexClientConfig.staticTime.toString().length * 7) + 105
            fontRenderer.drawString("<-", offset, 35, greenOrWhite(Keyboard.isKeyDown(Keyboard.KEY_LEFT)))
            fontRenderer.drawString("->", offset + 14, 35, greenOrWhite(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)))
        }
        // STATIC TIME

        // PLAYER TRACKER
        fontRenderer.drawString("No Fall", 2, 46, 0xFFFFFF)
        fontRenderer.drawString("[N]", 39, 46, redOrGreen(MinecraftFexClientConfig.doNoFall))
        // PLAYER TRACKER
    }


    fun toggleVisible() { isHidden = !isHidden }


    private fun redOrGreen(bool: Boolean) = if (bool) 0x11FF11 else 0xFF1111
    private fun greenOrWhite(bool: Boolean) = if (bool) 0x11FF11 else 0xFFFFFF
}