import net.minecraft.client.Minecraft

fun main(args: Array<String>) {
    val username = if (args.isNotEmpty()) args[0] else "NoUsernameProvided"
    println(username)
    System.setProperty("org.lwjgl.librarypath", "C:\\lib")
    System.setProperty("net.java.games.input.librarypath", "C:\\lib")
    Minecraft.main(arrayOf(username))
}