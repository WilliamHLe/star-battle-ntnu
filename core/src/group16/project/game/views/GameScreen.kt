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
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import group16.project.game.models.GameState


class GameScreen(val gameController: StarBattle) : View() {
    private val screenRect = Rectangle(0f, 0f, Configuration.gameWidth, Configuration.gameHeight)
    private val camera = OrthographicCamera()
    private val game = Game(screenRect, camera)
    lateinit private var gameState: GameState;

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
        gameState = GameState.PLAYERS_CHOOSING
    }
    fun drawLayout() {
        var table = VisTable()

        val btnBack = VisTextButton("Return to main menu")
        btnBack.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                gameController.changeScreen(MainMenuScreen::class.java)
            }
        })

        // Draw topbox
        val tbox = Image(TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("topbox.png")))))
        tbox.setSize(360f, 60f)
        tbox.setPosition((stage.width/2) - 180f, stage.height - 60f)
        stage.addActor(tbox)

        // Draw bottombox
        val bbox = Image(TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("bottombox.png")))))
        bbox.setSize(670f, 90f)
        bbox.setPosition((stage.width/2) - 670/2f, -15f)
        stage.addActor(bbox)
        val tubox = Image(TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("turnbox.png")))))
        tubox.setSize(260f, 70f)
        tubox.setPosition((stage.width/2) - 130f, 50f)
        stage.addActor(tubox)

        val btnFire = VisTextButton("Fire!")
        btnFire.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                if (gameController.currentGame == "null") {

                    game.fireShots()
                } else {

                    println(gameController.currentGame)
                    gameController.getDBConnection().setPlayersChoice(
                        gameController.currentGame,
                        InputHandler.playerPosition,
                        InputHandler.playerTrajectoryPosition
                    )
                    var bothReady = gameController.getDBConnection()
                        .playerIsReadyToFire(gameController.currentGame)
                    print("BOTH PLAYERS ARE: ")
                    if(bothReady) {
                        game.fireShots()
                        gameController.gameStateManager.fireShots()
                    }


                }
            }
        });
        // Create the layout
        table.columnDefaults(0).pad(10f)
        table.setFillParent(true)
        table.add(btnBack).size(stage.width / 2, 45.0f)
        table.row()
        table.add(btnFire).size(stage.width/2, 45.0f)
        stage.addActor(table)

        for (i in 0..1) {
            // Draw table
            val vbox = VisTable()
            vbox.width = 150f
            vbox.height = stage.height
            vbox.setPosition(i * (stage.width - 150f), 0f)

            // Draw selection markers
            val padding = (Configuration.gameHeight - 4*100) / 2
            val longbar1 = Image(TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("longbar.png")))))
            longbar1.setSize(40f, 100f*4 + 40f + 20f)
            longbar1.setPosition((stage.width - 150f)*i - 10f, padding - 20f - 10f)
            stage.addActor(longbar1)
            val longbar2 = Image(TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("longbar.png")))))
            longbar2.setSize(40f, 100f*4 + 40f + 20f)
            longbar2.setPosition((stage.width - 155f)*i + 125f, padding - 20f - 10f)
            stage.addActor(longbar2)
            val bbar = Image(TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("bar.png")))))
            bbar.setSize(200f, 40f)
            bbar.setPosition((stage.width - 150f)*i - 20f, padding - 20f)
            stage.addActor(bbar)

            // Draw buttons
            val btn1 = VisTextButton("3")
            val btn2 = VisTextButton("2")
            val btn3 = VisTextButton("1")
            val btn4 = VisTextButton("0")
            val btns = arrayOf(btn1, btn2, btn3, btn4)
            for ((j, btn) in btns.withIndex()) {
                btn.setColor(0f,0f,0f,0.2f)
                if (i == 0)
                    btn.addListener(object : ChangeListener() {
                        override fun changed(event: ChangeEvent, actor: Actor) {
                            InputHandler.playerPosition =
                                Integer.parseInt(btn.text.toString())
                            print("PS ")
                            println(InputHandler.playerPosition)
                        }
                    })
                else
                    btn.addListener(object : ChangeListener() {
                        override fun changed(event: ChangeEvent, actor: Actor) {
                            InputHandler.playerTrajectoryPosition = Integer.parseInt(btn.text.toString())
                            print("PTS ")
                            println(InputHandler.playerTrajectoryPosition)
                        }
                    })
                vbox.add(btn).size(150f, 100f)
                vbox.row()
                // BAR
                val bar = Image(TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("bar.png")))))
                bar.setSize(200f, 40f)
                bar.setPosition((stage.width - 160f)*i - 20f, padding - 20f + 100f*(j+1))
                stage.addActor(bar)
            }
            stage.addActor(vbox)
        }
        Gdx.app.log("VIEW", "Game loaded")

    }
            }