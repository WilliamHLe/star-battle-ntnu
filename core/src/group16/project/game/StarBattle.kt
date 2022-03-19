package group16.project.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.kotcrab.vis.ui.VisUI
import com.badlogic.gdx.utils.ObjectMap
import group16.project.game.views.MainMenuScreen
import group16.project.game.views.GameScreen
import group16.project.game.views.JoinLobbyScreen
import group16.project.game.views.View

class StarBattle : Game() {
    private val screens: ObjectMap<Class<out View?>, View> = ObjectMap<Class<out View?>, View>()
    private var view: View? = null

    override fun create() {
        // Load the UI first
        VisUI.load()

        // Load screens and change screen to start screen
        loadScreens()
        changeScreen(MainMenuScreen::class.java)
        Gdx.app.log("CONTROLLER", "StarBattleController loaded")
    }

    override fun render() {
        // Delta time rendering
        val dt = Gdx.graphics.deltaTime
        // Render and update state
        view!!.render(dt)
    }

    override fun dispose() {
        // Dispose of the view
        setScreen(null)
        screens.forEach { e -> e.value.dispose() }
        screens.clear()
        if (VisUI.isLoaded()) VisUI.dispose()
    }
    fun setScreen(screen: View?) {
        super.setScreen(screen)
        view = screen
    }

    fun changeScreen(key: Class<out View?>?) {
        setScreen(screens[key])
    }

    fun loadScreens() {
        screens.put(MainMenuScreen::class.java, MainMenuScreen(this))
        screens.put(GameScreen::class.java, GameScreen(this))
        screens.put(JoinLobbyScreen::class.java, JoinLobbyScreen(this))
    }
}