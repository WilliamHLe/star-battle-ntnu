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
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.kotcrab.vis.ui.widget.ButtonBar
import group16.project.game.controllers.GameController
import java.util.ArrayList

class GameScreen(val game: StarBattle) : View() {
    private val screenRect = Rectangle(0f, 0f, Configuration.gameWidth, Configuration.gameHeight)
    private val camera = OrthographicCamera()
    private val visualGame = Game(screenRect, camera)
    private val controller = GameController(game, this.stage)

    override fun draw(delta: Float) {
        visualGame.render(delta)
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        screenRect.width = stage.viewport.worldWidth
        screenRect.height = stage.viewport.worldHeight
    }

    override fun dispose() {
        super.dispose()
        visualGame.dispose()
    }

    override fun pause() {}
    override fun resume() {}
    override fun hide() {
        visualGame.hide()
    }

    override fun init() {
        controller.init()


        // Create the layout
        controller.table.columnDefaults(0).pad(10f)
        controller.table.columnDefaults(1).pad(10f)
        controller.table.setFillParent(true)
        controller.table.add(controller.txtDescription).size(stage.width / 2, 100.0f)
        controller.table.row()
        controller.table.add(controller.btnStart).size(stage.width / 2, 45.0f)
        stage.addActor(controller.table)

        for (i in 0..1) {
            val vbox = VisTable()
            vbox.width = 100f
            vbox.height = stage.height
            vbox.setPosition(i * (stage.width - 100f), 0f)
            val btn1 = VisTextButton("POS1")
            val btn2 = VisTextButton("POS2")
            val btn3 = VisTextButton("POS3")
            val btn4 = VisTextButton("POS4")
            val btns = arrayOf(btn1, btn2, btn3, btn4)
            for ((i, btn) in btns.withIndex()) {
                btn.addListener(object : ChangeListener() {
                    override fun changed(event: ChangeEvent, actor: Actor) {
                        println(btn.text)
                        visualGame.move(i)
                    }
                })
                vbox.add(btn).size(100f, 100f)
                vbox.row()
            }
            stage.addActor(vbox)
        }
        Gdx.app.log("VIEW", "Game loaded")

        // Init game model and camera
        camera.setToOrtho(false, Configuration.gameWidth, Configuration.gameHeight)
        visualGame.init()
    }
}