package me.fexclient.externalcommand.input

import me.fexclient.externalcommand.*
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JTextArea
import javax.swing.JTextField
import kotlin.reflect.full.companionObject


class UserExternalCommandInputWindow(private val commandList: MutableList<ExternalCommand>): JFrame() {
    private val mainPanel = JPanel()
    private val tutorialTextArea = JTextArea("Tutorial")
    private val inputField = JTextField("")

    private val allCommands = arrayOf(
        ExternalCommandSetBrightness::class, ExternalCommandSetTime::class, ExternalCommandTeleport::class
    )

    init {
        mainPanel.layout = BorderLayout()

        tutorialTextArea.isEditable = false
        var tutorialText = "Available Commands: \n"
        allCommands.forEach {
            val companion = it.companionObject!!
            val commandName = companion.members.first { member -> member.name == "commandName" }.call(null)
            val commandParams = companion.members.first { member -> member.name == "commandParams" }.call(null)
            val commandUsage = companion.members.first { member -> member.name == "commandUsage" }.call(null)
            tutorialText += "   /$commandName $commandParams | $commandUsage\n"
        }
        tutorialTextArea.text = tutorialText
        mainPanel.add(tutorialTextArea, BorderLayout.CENTER)

        inputField.addActionListener(TextInputListener(commandList))
        mainPanel.add(inputField, BorderLayout.SOUTH)

        this.add(mainPanel)

        isVisible = true
        size = Dimension(300,200)
        defaultCloseOperation = JFrame.DO_NOTHING_ON_CLOSE

        title = "FexClient Command Line"
    }

    private class TextInputListener(val commandList: MutableList<ExternalCommand>): ActionListener {
        override fun actionPerformed(e: ActionEvent?) {
            if (e == null) return
            val rawCommandString = e.actionCommand.removePrefix("/") // commands can be started with or without /
            val tokens = rawCommandString.split(" ")
            val commandName = tokens[0]
            val commandArguments = tokens.subList(1, tokens.size)
            val parser = CommandParser(rawCommandString)
            val command = parser.command
            if (command != null) commandList.add(command)
            println("$commandName $commandArguments: ${parser.error}")
        }
    }
}