package group16.project.game.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import group16.project.game.Configuration
import group16.project.game.StarBattle
import group16.project.game.views.components.PopupComponent
import group16.project.game.views.components.SlideshowComponent
import group16.project.game.models.FirebaseInterface
import group16.project.game.models.GameState

class MainMenuScreen(val gameController: StarBattle, private val fbic: FirebaseInterface) : View() {
    override fun draw(delta: Float) {}

    override fun init() {
        // Log in user
        fbic.signInAnonymously()
        // Draw layout
        drawLayout()
    }
    private fun drawLayout() {
        val table = VisTable()

        val bg = Image(TextureRegionDrawable(TextureRegion(TextureHandler.textures["BACKGROUND"])))
        bg.setSize(stage.width, stage.height)
        bg.setPosition(0f, 0f)
        stage.addActor(bg)

        // Create the description field
        val logo = Image(TextureRegionDrawable(TextureRegion(TextureHandler.textures["LOGO"])))

        // Add a debug "StartGame" button
        val btnStart = VisTextButton("Start debug game")
        btnStart.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                gameController.changeScreen(GameScreen::class.java)
                fbic.updateCurrentGameState(GameState.SETUP)
            }
        })
        // Add a "JoinLobby" button
        val btnJoin = VisTextButton("Join game lobby")
        btnJoin.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                gameController.changeScreen(JoinLobbyScreen::class.java)
            }
        })

        // Add a "Create Lobby" button
        val btnCreateLobby = VisTextButton("Create game lobby")
        btnCreateLobby.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                gameController.changeScreen(CreateLobbyScreen::class.java)
            }
        })

        // Add a "change skin" button
        val btnChangeSkin = VisTextButton("Change skin")
        btnChangeSkin.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                gameController.changeScreen(SkinScreen::class.java)
            }
        })

        // Add a "Leaderboard" button
        val btnHighScore = VisTextButton("Leaderboard")
        btnHighScore.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                gameController.changeScreen(LeaderboardScreen::class.java)
            }
        })

        // Create the layout
        table.columnDefaults(0).pad(10f)
        table.columnDefaults(1).pad(10f)
        table.setFillParent(true)
        table.add(logo).size(stage.width / 3, stage.width / 6)
        table.row()
        if (Configuration.debug) {
            table.add(btnStart).size(stage.width / 2, 45.0f)
            table.row()
        }
        table.add(btnJoin).size(stage.width/2, 45.0f)
        table.row()
        table.add(btnCreateLobby).size(stage.width / 2, 45.0f)
        table.row()
        table.add(btnChangeSkin).size(stage.width / 2, 45.0f)
        table.row()
        table.add(btnHighScore).size(stage.width / 8, 45.0f)

        stage.addActor(table)

        // Draw help icon on top right corner
        val helpIcon = Image(TextureRegionDrawable(TextureRegion(TextureHandler.textures["ICON_HELP"])))
        helpIcon.setSize(50f, 58f)
        helpIcon.setPosition(stage.width - 60f, stage.height - 66f)
        val btnHelp = VisTextButton("")
        btnHelp.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                println("HELP")
                stage.addActor(PopupComponent(SlideshowComponent(Configuration.slides), true))
            }
        })
        btnHelp.setSize(50f, 58f)
        btnHelp.setPosition(stage.width - 60f, stage.height - 66f)
        btnHelp.setColor(0f,0f,0f,0f)
        stage.addActor(helpIcon)
        stage.addActor(btnHelp)

        Gdx.app.log("VIEW", "Main Menu loaded")
    }
}