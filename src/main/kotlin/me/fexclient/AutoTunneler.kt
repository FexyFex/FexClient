package me.fexclient

import me.fexclient.datatype.Vec3d
import me.fexclient.datatype.Vec3i
import net.minecraft.client.Minecraft
import net.minecraft.src.block.World
import net.minecraft.src.entity.EntityPlayerSP
import net.minecraft.src.helpers.MathHelper.floor_double
import net.minecraft.src.item.ItemStack
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.roundToInt


// Specifically designed to mine a 1x2 shaft through the nether towards +x at y=120 and z=0
class AutoTunneler(private val mc: Minecraft) {
    private lateinit var player: EntityPlayerSP
    private lateinit var world: World

    private var isInitialized = false

    private val foreSight = 6
    private val messageSendCooldown = 1f // in seconds
    private var nextMessageSendTimer = messageSendCooldown

    private var isCurrentlyMining = false

    private val yLevel: Int = 64


    fun tick(delta: Float) {
        if (!isInitialized) begin()

        val currentPlayerPos = Vec3d(player.posX, floor(player.posY) - 1, player.posZ)
        nextMessageSendTimer = max(0f, nextMessageSendTimer - delta)

        if (!isOnCorrectHeight(currentPlayerPos)) {
            // Send message
            println("BOT HAS LEFT ITS DESIGNATED Y-LEVEL!")
        }

        if (isDangerAhead(currentPlayerPos)) {
            // Send discord message (or email lol)
            println("DANGER AHEAD! (likely lava)")
        }

        if (!isFloorSafe(currentPlayerPos)) {
            // Send message
            println("UNSAFE FLOOR DETECTED! THE BOT COULD FALL!")
        }

        if (!hasPickaxesLeftInInventory()) {
            // Send message
            println("NO MORE PICKAXES LEFT IN INVENTORY! PLEASE SEND NEW SUPPLIES!")
            // simply continue with fists.
        }

        // After all those checks, we start walking and mining (if blocks are in range)
        if (player.rotationYaw != 0f)
            player.setPositionAndRotation(player.posX, player.posY, player.posZ, 270f, 0f)

        player.moveEntityWithHeading(0f, 0.5f) // moving forwards

        renderInfoGui()
    }

    private fun isDangerAhead(playerPos: Vec3d): Boolean {
        val pos = playerPos.floorToVec3i()
        repeat(foreSight) { x ->
            repeat(4) { y ->
                val offset = Vec3i(x, y, 0)
                val blockId = getBlockIdAt(pos + offset)
                if (blockId == 10 || blockId == 11) return true
            }
        }
        return false
    }

    private fun isFloorSafe(playerPos: Vec3d): Boolean {
        repeat(foreSight) {
            val offset = Vec3i(it + 1, -1, 0)
            val blockId = getBlockIdAt(playerPos.floorToVec3i() + offset)
            if (blockId == 0) return false
        }
        return true
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
        return playerPos.y.roundToInt() == this.yLevel
    }

    private fun renderInfoGui() {
        mc.fontRenderer.drawString("Tunneler status: Active", 60, 6, 0xFFFFFF)
    }

    private fun getBlockIdAt(pos: Vec3d) = getBlockIdAt(Vec3i(floor_double(pos.x), floor_double(pos.y), floor_double(pos.z)))
    private fun getBlockIdAt(pos: Vec3i) = world.getBlockId(pos.x, pos.y, pos.z)

    private fun begin() {
        this.player = mc.thePlayer
        this.world = mc.theWorld
    }

    private fun stop() {}


    companion object {
        //                               iron  wood  stone  dia   gold
        private val pickaxeIds = arrayOf(257 , 270 , 274  , 278 , 285 )
    }
}