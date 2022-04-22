package group16.project.game.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.VisTextField
import group16.project.game.StarBattle
import group16.project.game.views.components.ImageSlideshowComponent
import group16.project.game.views.components.PopupComponent
import group16.project.game.views.components.SlideshowComponent
import java.util.ArrayList
import group16.project.game.models.FirebaseInterface
import group16.project.game.models.GameState

class MainMenuScreen(val gameController: StarBattle, private val fbic: FirebaseInterface) : View() {
    override fun draw(delta: Float) {}
    override fun pause() {}
    override fun resume() {}


    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        // Redraw on resize
        stage.clear()
        drawLayout()
    }

    override fun init() {
        // Log in user
        fbic.signInAnonymously()
        // Draw layout
        drawLayout()
    }
    fun drawLayout() {
        var table = VisTable()

        val bg = Image(TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("background.png")))))
        bg.setSize(stage.width, stage.height)
        bg.setPosition(0f, 0f)
        stage.addActor(bg)

        // Create the description field
        val txtDescription = VisTextField("Star Battle NTNU")
        txtDescription.isDisabled = true
        txtDescription.setAlignment(1) // center text

        // Add a "StartGame" button
        val btnStart = VisTextButton("Start the game")
        btnStart.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                gameController.changeScreen(GameScreen::class.java)
                fbic.updateCurrentGameState(GameState.SETUP)
            }
        })
        // Add a "JoinLobby" button
        val btnJoin = VisTextButton("Join lobby game")
        btnJoin.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                gameController.changeScreen(JoinLobbyScreen::class.java)
            }
        })

        // Add a "Create Lobby" button
        val btnCreateLobby = VisTextButton("Create lobby")
        btnCreateLobby.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                gameController.changeScreen(CreateLobbyScreen::class.java)
            }
        })

        // add a "HighScore" button
        val btnHighScore = VisTextButton("Leaderboard")
        btnHighScore.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                gameController.changeScreen(HighScoreScreen::class.java)
            }
        })

        // Create the layout
        table.columnDefaults(0).pad(10f)
        table.columnDefaults(1).pad(10f)
        table.setFillParent(true)
        table.add(txtDescription).size(stage.width / 2, 100.0f)
        table.row()
        table.add(btnStart).size(stage.width / 2, 45.0f)
        table.row()
        table.add(btnJoin).size(stage.width/2, 45.0f)
        table.row()
        table.add(btnCreateLobby).size(stage.width / 2, 45.0f)
        table.row()
        table.add(btnHighScore).size(stage.width / 8, 45.0f)

        stage.addActor(table)

        // Draw help icon on top right corner
        val helpIcon = Image(TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("help_icon.png")))))
        helpIcon.setSize(50f, 58f)
        helpIcon.setPosition(stage.width - 60f, stage.height - 66f)
        val btnHelp = VisTextButton("")
        btnHelp.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                println("HELP")
                val slides = ArrayList<ImageSlideshowComponent>()
                slides.add(ImageSlideshowComponent("tutorial1.png", "This is the screen when you create a lobby. oThe game status is at the top with the lobby code under it. This code you give to your friend or someone else for them to join your lobby."))
                slides.add(ImageSlideshowComponent("tutorial2.png", "When your opponent has joined the game status will update. Now you can move your ufo on the left side and your target on your right side. You move it by clicking on one square in the position or target grid. NB! The opponent might move"))
                slides.add(ImageSlideshowComponent("tutorial3.png", "When you have positioned your UFO and target at your desired place you click on the 'End Turn' button at the bottom. Now you will see that the status on top will change and you will have to wait for the your opponent to end their turn."))
                slides.add(ImageSlideshowComponent("tutorial4.png", "When your friend and you have ended your turns the status will be updated and you can se where your opponent moves. If anyone got hit they will loose one heart. After this you can change your UFO and target position again."))
                slides.add(ImageSlideshowComponent("tutorial5.png", "There is also some power-ups you can use. A power-up can only be used once. You choose it by clicking on the power-up. You can not unclick the power-up. The shield power-up will protect you if hit but you only have it one round"))
                slides.add(ImageSlideshowComponent("tutorial6.png", "When someone are have 0 hearts the game ends. The winner will get points added to their total score and the looser will be deducted points from theirs. If it a tie no points will be deducted or added. A players total score represent how many games they have won and it is used for the leaderboard"))
                slides.add(ImageSlideshowComponent("tutorial7.png", "Here is a summary of the most important parts of the game. You can access this slideshow in game by clicking on the help screen button. By clicking on the menu screen you can leave the lobby, but you can't return. Have fun!"))
                stage.addActor(PopupComponent(SlideshowComponent(slides), true))
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