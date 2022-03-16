package group16.project.game.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.VisTextField
import group16.project.game.Configuration
import group16.project.game.StarBattle
import com.badlogic.gdx.math.Rectangle
import group16.project.game.models.Game
import com.badlogic.gdx.graphics.OrthographicCamera

class GameScreen(val gameController: StarBattle) : View() {
    private val screenRect = Rectangle(0f, 0f, Configuration.gameWidth, Configuration.gameHeight)
    private val camera = OrthographicCamera()
    private val game = Game(screenRect, camera)

    override fun draw(delta: Float) {
        game.render(delta)
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        screenRect.width = stage.viewport.worldWidth
        screenRect.height = stage.viewport.worldHeight
    }

    override fun dispose() {
        super.dispose()
        game.dispose()
    }

    override fun pause() {}
    override fun resume() {}
    override fun hide() {
        game.hide()
    }

    override fun init() {
        var table = VisTable()

        // Create the description field
        val txtDescription = VisTextField("Model-View-Controller pattern :) - GAME")
        txtDescription.isDisabled = true
        txtDescription.setAlignment(1) // center text

        // Add a "StartGame" button
        val btnStart = VisTextButton("Return to main menu")
        btnStart.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                gameController.changeScreen(MainMenuScreen::class.java)
            }
        })

        // Create the layout
        table.columnDefaults(0).pad(10f)
        table.columnDefaults(1).pad(10f)
        table.setFillParent(true)
        table.add(txtDescription).size(stage.width / 2, 100.0f)
        table.row()
        table.add(btnStart).size(stage.width / 2, 45.0f)

        stage.addActor(table)
        Gdx.app.log("VIEW", "Game loaded")

        // Init game model and camera
        camera.setToOrtho(false, Configuration.gameWidth, Configuration.gameHeight)
        game.init()
    }
}