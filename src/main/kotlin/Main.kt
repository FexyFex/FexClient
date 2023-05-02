import net.minecraft.client.Minecraft

fun main(args: Array<String>) {
    System.setProperty("org.lwjgl.librarypath", "C:\\lib")
    System.setProperty("net.java.games.input.librarypath", "C:\\lib")
    Minecraft.main(arrayOf("tonmanns"))
}