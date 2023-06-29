package me.fexclient

import me.fexclient.datatype.Vec3d
import me.fexclient.datatype.Vec3i
import net.minecraft.client.Minecraft
import net.minecraft.src.block.World
import net.minecraft.src.entity.EntityPlayerSP
import net.minecraft.src.gui.GuiInventory
import net.minecraft.src.helpers.MathHelper.floor_double
import net.minecraft.src.player.PlayerControllerMP
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse
import java.awt.Robot
import java.awt.event.InputEvent
import java.io.File
import java.sql.Timestamp
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.roundToInt


// Specifically designed to mine a 1x2 shaft through the nether towards +x at y=120 and z=0
class AutoTunneler(private val mc: Minecraft) {
    private lateinit var player: EntityPlayerSP
    private lateinit var world: World

    private var isInitialized = false

    private val foreSight = 5
    private val messageSendCooldown = 200f // not really seconds but kinda close I guess
    private var nextMessageSendTimer = 0f

    private var isSeeingUnsafeGround = false
    private var isOutOfPickaxes = false

    private val yLevel: Int = 121
    private val zCoord: Int = 0

    private var changePickaxeCooldown = 0f


    fun tick(delta: Float) {
        if (!isInitialized) {
            this.player = mc.thePlayer
            this.world = mc.theWorld
            if (!isInventoryArrangedCorrectly()) {
                println("Auto Tunneler will not start without some netherrack in the first slot")
                MinecraftFexClientConfig.tunnelerActive = false
                return
            }
            isInitialized = true
        }

        isSeeingUnsafeGround = false
        isOutOfPickaxes = false

        val currentPlayerPos = Vec3d(player.posX, floor(player.posY) - 1, player.posZ)
        nextMessageSendTimer = max(0f, nextMessageSendTimer - 0.16f)

        if (player.health < 8) {
            Log.writeln("THE BOT IS GETTING LOW ON HEALTH! SAFETLY LOGOUT AT $currentPlayerPos")
            MinecraftFexClientConfig.requestedLogout
        }

        if (!isOnCorrectHeight(currentPlayerPos)) {
            // Send message
            Log.write("BOT HAS LEFT ITS DESIGNATED Y-LEVEL! ")
            Log.writeln("Logout position: ${Vec3d(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ)}")
            MinecraftFexClientConfig.requestedLogout = true
        }

        if (isDangerAhead(currentPlayerPos)) {
            // Send discord message (or email lol)
            Log.write("LAVA OR FIRE AHEAD! ")
            Log.writeln("Logout position: $currentPlayerPos")
            MinecraftFexClientConfig.requestedLogout = true
        }

        if (!isFloorSafe(currentPlayerPos)) {
            isSeeingUnsafeGround = true
            // Send message
            Log.write("UNSAFE FLOOR DETECTED! TRYING TO BRIDGE: ")
            if (!bridgeUnsafeFloor(currentPlayerPos)) {
                Log.writeln("FAILED!")
                return
            } else {
                Log.writeln("SUCCESS!")
            }
        }

        if (!hasPickaxesLeftInInventory()) {
            isOutOfPickaxes = true
            // Send message
            if (nextMessageSendTimer <= 0f) {
                Log.writeln("NO MORE PICKAXES LEFT IN INVENTORY! PLEASE SEND NEW SUPPLIES!")

                val minecraftDir = Minecraft.getMinecraftDir()
                val minecraftDirPath = minecraftDir.toPath().toString()
                val logDir = "$minecraftDirPath/logs/"
                val logDirFile = File(logDir)

                if (!logDirFile.exists()) logDirFile.mkdir()

                val timeStamp = System.currentTimeMillis()
                val fileName = "tunnellog_$timeStamp.txt"
                val filePath = logDir + fileName

                Log.writeToFile(filePath)
                nextMessageSendTimer = messageSendCooldown
            }
            // simply continue with fists
        }

        // Have pickaxes fall into the second slot if that slot is empty
        if (player.inventory.mainInventory[1]?.itemID !in pickaxeIds && (!isOutOfPickaxes || switchStage != 0)) {
            continuePickaxeSwitch()
            return
        }

        // After all those checks, we start walking and mining (if blocks are in range)
        player.inventory.currentHotBarSlot = 1

        val nextBlock: Vec3i? = getNextBlockToBreak(currentPlayerPos)
        var lookAngle = 0f
        if (nextBlock != null) {
            val diff = currentPlayerPos.y + 1 - nextBlock.y
            lookAngle = diff.toFloat() * 65f
        }

        if (player.rotationYaw != 0f) player.setPositionAndRotation(player.posX, player.posY, player.posZ, 270f, lookAngle)
        player.moveEntityWithHeading(0f, 0.98f) // moving forwards

        if (nextBlock == null) return

        val controller = mc.playerController as? PlayerControllerMP

        if (controller != null && controller.miningProgress == 0f) {
            controller.resetMiningEfforts()
            controller.clickBlock(nextBlock.x, nextBlock.y, nextBlock.z, 4)
        }

        mc.playerController.sendBlockRemoving(nextBlock.x, nextBlock.y, nextBlock.z, 4)
    }

    private fun getNextBlockToBreak(playerPos: Vec3d): Vec3i? {
        val pos = playerPos.floorToVec3i()
        var targetPos: Vec3i? = null
        repeat(foreSight) { x ->
            if (targetPos != null) return@repeat
            repeat(2) { y ->
                val offset = Vec3i(x + 1, y, 0)
                val blockId = getBlockIdAt(pos + offset)
                if (blockId != 0 && targetPos == null) {
                    targetPos = pos + offset
                }
            }
        }
        return targetPos
    }

    private fun isDangerAhead(playerPos: Vec3d): Boolean {
        val pos = playerPos.floorToVec3i()
        repeat(foreSight) { x ->
            repeat(4) { y ->
                val offset = Vec3i(x, y, 0)
                val blockId = getBlockIdAt(pos + offset)
                if (blockId == 10 || blockId == 11 || blockId == 51) return true
            }
        }
        return false
    }

    private fun isFloorSafe(playerPos: Vec3d): Boolean {
        repeat(3) {
            val offset = Vec3i(it, -1, 0)
            val blockId = getBlockIdAt(playerPos.floorToVec3i() + offset)
            if (blockId == 0) return false
        }
        return true
    }

    private fun bridgeUnsafeFloor(playerPos: Vec3d): Boolean {
        var nextUnsafeBlock: Vec3i? = null
        for(it in 0 until foreSight) {
            val offset = Vec3i(it, -1, 0)
            val target = playerPos.floorToVec3i() + offset
            val blockId = getBlockIdAt(target)
            if (blockId == 0) {
                nextUnsafeBlock = target
                nextUnsafeBlock.y += 1
                break
            }
        }
        if (nextUnsafeBlock == null) { return false }

        player.inventory.currentHotBarSlot = 0
        val heldItem = player.inventory.getCurrentHotBarSlot()
        if (heldItem != null && heldItem.stackSize > 0) {
            return mc.playerController.sendPlaceBlock(
                player, world, player.inventory.getCurrentHotBarSlot(),
                nextUnsafeBlock.x, nextUnsafeBlock.y, nextUnsafeBlock.z, 0
            )
        } else {
            return false
        }
    }

    private fun hasPickaxesLeftInInventory(): Boolean {
        return player.inventory.mainInventory.any {
            var result = false
            if (it != null)
                result = it.itemID in pickaxeIds
            result
        }
    }

    private fun isOnCorrectHeight(playerPos: Vec3d): Boolean {
        val posY = playerPos.y.roundToInt()
        return posY in (this.yLevel - 1)..(this.yLevel)
    }

    private fun isInventoryArrangedCorrectly(): Boolean {
        return player.inventory.mainInventory[0]?.itemID == 87
    }

    fun renderInfoGui() {
        val pickaxeCount: Int = player.inventory.mainInventory.count {
            if (it == null)
                false
            else
                it.itemID in pickaxeIds
        }

        val pathStatus = if (isSeeingUnsafeGround) "Unsafe" else "Safe"

        mc.fontRenderer.drawString("Tunneler Active", 300, 6, 0xFFFFFF)
        mc.fontRenderer.drawString("Pickaxes: $pickaxeCount", 300, 17, 0xFFFFFF)
        mc.fontRenderer.drawString("Path: $pathStatus", 300, 28, 0xFFFFFF)
    }

    private fun getBlockIdAt(pos: Vec3d) = getBlockIdAt(Vec3i(floor_double(pos.x), floor_double(pos.y), floor_double(pos.z)))
    private fun getBlockIdAt(pos: Vec3i) = world.getBlockId(pos.x, pos.y, pos.z)

    fun stop() {
        isInitialized = false
    }


    companion object {
        //                               iron  wood  stone  dia   gold
        private val pickaxeIds = arrayOf(257 , 270 , 274  , 278 , 285 )

        object Log {
            private var fullLog: String = ""
            private var newLine = true

            fun write(text: String) { fullLog += timeStamp() + text; newLine = false }
            fun writeln(line: String) { fullLog += timeStamp() + line + "\n"; newLine = true }
            fun writeln() { fullLog += "\n"; newLine = true }

            private fun timeStamp() = if (newLine) Timestamp(System.currentTimeMillis()).toString() + ": " else ""

            fun writeToFile(path: String) {
                if (fullLog == "") return
                val file = File(path)
                file.createNewFile()
                file.writeText(fullLog)
                fullLog = ""
            }
        }

        fun Boolean.toInt() = if (this) 1 else 0
    }


    private var switchStage = 0
    private fun continuePickaxeSwitch() {
        when (switchStage) {
            0 -> mc.displayGuiScreen(GuiInventory(player))
            1 -> {
                val nextSlotIndex = player.inventory.mainInventory.indexOfFirst { it != null && it.itemID in pickaxeIds }
                println(nextSlotIndex)
                val x = 280 + ((nextSlotIndex % 9) * 35)
                var y = 110 + ((nextSlotIndex > 8).toInt() * 12)
                when {
                    nextSlotIndex > 26 -> y += 35
                    nextSlotIndex > 17 -> y += 70
                    nextSlotIndex > 8 -> y += 105
                }

                Mouse.setCursorPosition(x, y)
                val rob = Robot()
                rob.mousePress(InputEvent.BUTTON1_DOWN_MASK)
                rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
            }
            2 -> {
                val x = 320
                val y = 110
                Mouse.setCursorPosition(x,y)
                val rob = Robot()
                rob.mousePress(InputEvent.BUTTON1_DOWN_MASK)
                rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
            }
        }
        switchStage = (switchStage + 1) % 3
    }


}