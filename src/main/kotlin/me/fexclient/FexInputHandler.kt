package me.fexclient

import org.lwjgl.input.Keyboard


object FexInputHandler {
    private val downKeys = mutableSetOf<Int>()
    private val keysJustPressed = mutableSetOf<Int>()

    fun isKeyJustPressed(key: Int): Boolean {
        return key in keysJustPressed
    }

    fun receiveInputs() {
        keysJustPressed.clear()
        do {
            val key = Keyboard.getEventKey()
            val isDown = Keyboard.getEventKeyState()
            if (isDown) {
                if (key !in downKeys) {
                    downKeys.add(key)
                    keysJustPressed.add(key)
                }
            } else {
                downKeys.remove(key)
            }
        } while (Keyboard.next())
    }


    fun keyCodeToString(keyCode: Int): String {
        val fields = Keyboard::class.java.fields
        return Keyboard::class.java.fields
            .first {
                it.name.startsWith("KEY_") && it.getInt(null) == keyCode
            }.name
    }
}