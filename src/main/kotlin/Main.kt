import me.fexclient.AutoTunneler
import net.minecraft.client.Minecraft
import java.io.File

fun main(args: Array<String>) {
    extractNativesIfNecessary()
    val username = if (args.isNotEmpty()) args[0] else "NoUsernameGiven"
    println(username)

    Minecraft.main(arrayOf(username))

    val minecraftDir = Minecraft.getMinecraftDir()
    val minecraftDirPath = minecraftDir.toPath().toString()
    val logDir = "$minecraftDirPath/logs/"
    val logDirFile = File(logDir)

    if (!logDirFile.exists()) logDirFile.mkdir()

    val timeStamp = System.currentTimeMillis()
    val fileName = "tunnellog_$timeStamp.txt"
    val filePath = logDir + fileName
    AutoTunneler.Companion.Log.writeToFile(filePath)
}


// This function moves the lwjgl native libraries into the app directory where they can be properly read from
fun extractNativesIfNecessary() {
    val minecraftDir = Minecraft.getMinecraftDir()
    val minecraftDirPath = minecraftDir.toPath().toString()
    val nativesDir = File("$minecraftDirPath/natives/")

    // Only move files if the dir doesn't exist yet
    if (!nativesDir.exists()) {
        nativesDir.mkdir()

        allNatives.forEach {
            val content = ClassLoader.getSystemResourceAsStream(it)?.readBytes() ?: throw Exception("Bruh idk")
            val filePath = nativesDir.toPath().toString() + "/" + it.substringAfterLast("\\").substringAfterLast("/")
            val nativesFile = File(filePath)
            nativesFile.createNewFile()
            nativesFile.writeBytes(content)
        }
    }

    System.setProperty("org.lwjgl.librarypath", nativesDir.path.toString())
    System.setProperty("net.java.games.input.librarypath", nativesDir.path.toString())
}


val nativeJInput = "natives/jinput-dx8.dll"
val nativeJInput64 = "natives/jinput-dx8_64.dll"
val nativeJInputRaw = "natives/jinput-raw.dll"
val nativeJInputRaw64 = "natives/jinput-raw_64.dll"

val nativeLWJGL = "natives/lwjgl.dll"
val nativeLWJGL64 = "natives/lwjgl64.dll"
val nativeOpenAL = "natives/OpenAL32.dll"
val nativeOpenAL64 = "natives/OpenAL64.dll"

val allNatives = arrayOf(
    nativeJInput, nativeJInput64, nativeJInputRaw, nativeJInputRaw64,
    nativeLWJGL, nativeLWJGL64, nativeOpenAL, nativeOpenAL64
)