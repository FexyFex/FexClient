package me.fexclient.externalcommand.input

import me.fexclient.externalcommand.ExternalCommand
import java.awt.event.WindowEvent


class UserExternalCommandInputApp(private val commandList: MutableList<ExternalCommand>): Runnable {
    private lateinit var window: UserExternalCommandInputWindow
    private var shouldRun = true

    override fun run() {
        window = UserExternalCommandInputWindow(commandList)
        while (shouldRun) { Thread.sleep(100) }
        window.dispatchEvent(WindowEvent(window, WindowEvent.WINDOW_CLOSING))
    }

    fun destroy() {
        shouldRun = false
    }
}