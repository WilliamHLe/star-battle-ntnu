package group16.project.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.kotcrab.vis.ui.VisUI
import com.badlogic.gdx.utils.ObjectMap
import group16.project.game.models.FirebaseInterface
import group16.project.game.models.GameInfo
import group16.project.game.views.*

class StarBattle(private val fbic: FirebaseInterface) : Game() {
    private val screens: ObjectMap<Class<out View?>, View> = ObjectMap<Class<out View?>, View>()
    private var view: View? = null


    fun updateCurrentGame(gameCode: String, player: String, opponent: String){
        GameInfo.currentGame = gameCode
        GameInfo.player = player
        GameInfo.opponent = opponent
    }

    override fun create() {
        // Load the UI first
        VisUI.load()

        // Load screens and change screen to start screen
        loadScreens()

        // Log in here.
        fbic.signInAnonymously()

        //check if the view in screens are a highScoreScreen
        val highScoreScreen = screens[LeaderboardScreen::class.java]
        if(highScoreScreen is LeaderboardScreen) {
            //Get highscoreList listener
            fbic.getHighScoreListener(highScoreScreen)
        }
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
    private fun setScreen(screen: View?) {
        super.setScreen(screen)
        view = screen
    }

    fun changeScreen(key: Class<out View?>?) {
        setScreen(screens[key])
    }

    private fun loadScreens() {
        screens.put(MainMenuScreen::class.java, MainMenuScreen(this, fbic))
        screens.put(GameScreen::class.java, GameScreen(this, fbic))
        screens.put(CreateLobbyScreen::class.java, CreateLobbyScreen(this, fbic))
        screens.put(JoinLobbyScreen::class.java, JoinLobbyScreen(this, fbic))
        screens.put(LeaderboardScreen::class.java, LeaderboardScreen(this))
        screens.put(SkinScreen::class.java, SkinScreen(this))
    }
}