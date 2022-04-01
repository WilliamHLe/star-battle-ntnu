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
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.kotcrab.vis.ui.widget.ButtonBar
import java.util.ArrayList
import com.badlogic.gdx.physics.box2d.World
import group16.project.game.controllers.InputHandler


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
        // Redraw on resize
        stage.clear()
        drawLayout()
    }

    override fun dispose() {
        super.dispose()
        game.dispose()
    }

    override fun pause() {}
    override fun resume() {}
    override fun hide() {
        game.hide()
        stage.clear()
    }
    override fun init() {
        drawLayout()
        // Init game model and camera
        camera.setToOrtho(false, Configuration.gameWidth, Configuration.gameHeight)
        game.init()
    }
    fun drawLayout() {
        var table = VisTable()

        val btnBack = VisTextButton("Return to main menu")
        btnBack.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                gameController.changeScreen(MainMenuScreen::class.java)
            }
        })

        val btnFire = VisTextButton("Fire!")
        btnFire.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                InputHandler.fireShots = true
                println("Fire shots")
            //gameController.changeScreen(MainMenuScreen::class.java)
            }
        })
        // Create the layout
        table.columnDefaults(0).pad(10f)
        table.setFillParent(true)
        table.add(btnBack).size(stage.width / 2, 45.0f)
        table.add(btnFire).size(stage.width/2, 45.0f)
        stage.addActor(table)

        for (i in 0..1) {
            val vbox = VisTable()
            vbox.width = 150f
            vbox.height = stage.height
            vbox.setPosition(i * (stage.width - 150f), 0f)
            val btn1 = VisTextButton("3")
            val btn2 = VisTextButton("2")
            val btn3 = VisTextButton("1")
            val btn4 = VisTextButton("0")
            val btns = arrayOf(btn1, btn2, btn3, btn4)
            for (btn in btns) {
                btn.setColor(0f,0f,0f,0.2f)
                if (i == 0)
                    btn.addListener(object : ChangeListener() {
                        override fun changed(event: ChangeEvent, actor: Actor) {
                            InputHandler.playerPosition = Integer.parseInt(btn.text.toString()).toFloat()
                            print("PS ")
                            println(InputHandler.playerPosition)
                        }
                    })
                else
                    btn.addListener(object : ChangeListener() {
                        override fun changed(event: ChangeEvent, actor: Actor) {
                            InputHandler.playerTrajectoryPosition = Integer.parseInt(btn.text.toString()).toFloat()
                            print("PTS ")
                            println(InputHandler.playerTrajectoryPosition)
                        }
                    })
                vbox.add(btn).size(150f, 100f)
                vbox.row()
            }
            stage.addActor(vbox)
        }
        Gdx.app.log("VIEW", "Game loaded")
    }
}