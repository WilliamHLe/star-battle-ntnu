package group16.project.game

import kotlin.jvm.JvmStatic
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import group16.project.game.StarBattle
import group16.project.game.models.CoreFirebaseConnection
import pl.mk5.gdx.fireapp.GdxFIRApp

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        //GdxFIRApp.inst().configure();
        Lwjgl3Application(StarBattle(DesktopFirebaseConnection()), Lwjgl3ApplicationConfiguration().apply {
            setForegroundFPS(60)
            useVsync(true)
            setTitle(Configuration.gameTitle)
            setWindowedMode(Configuration.gameWidth.toInt(), Configuration.gameHeight.toInt())
            setWindowIcon(Configuration.gameIcon)
        })
    }
}