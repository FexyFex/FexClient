package me.fexclient.externalcommand

import net.minecraft.client.Minecraft


interface ExternalCommand {
    val action: (mc: Minecraft) -> Unit
}