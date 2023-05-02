package me.fexclient.externalcommand

import net.minecraft.client.Minecraft

class UnknownExternalCommand: ExternalCommand {
    override val action: (mc: Minecraft) -> Unit = {}
}