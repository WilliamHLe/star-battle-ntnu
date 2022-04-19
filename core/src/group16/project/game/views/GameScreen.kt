package group16.project.game.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import group16.project.game.Configuration
import group16.project.game.StarBattle
import com.badlogic.gdx.math.Rectangle
import group16.project.game.models.Game
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import group16.project.game.controllers.InputHandler
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import group16.project.game.ecs.component.HealthComponent
import group16.project.game.ecs.utils.ComponentMapper
import group16.project.game.models.GameState
import com.kotcrab.vis.ui.widget.*
import group16.project.game.models.FirebaseInterface
import group16.project.game.models.GameInfo
import group16.project.game.views.components.EndGameComponent
import group16.project.game.views.components.PopupComponent

class GameScreen(val gameController: StarBattle, val fbic: FirebaseInterface) : View() {
    private val screenRect = Rectangle(0f, 0f, Configuration.gameWidth, Configuration.gameHeight)
    private val camera = OrthographicCamera()
    private val game = Game(screenRect, camera, this)

    private val statusText = VisLabel("")
    private val btnEndTurn = VisTextButton("End Turn")
    private lateinit var healths: HashMap<String, HealthComponent>
    var bothHit = false

    override fun draw(delta: Float) {
        game.render(delta)
    }

    fun updateHealth(player: String, health: Int){
        healths[player]!!.set(health)
        println(!bothHit && (healths[GameInfo.player]!!.get() == 0 || healths[GameInfo.opponent]!!.get() == 0))
        if (!bothHit && (healths[GameInfo.player]!!.get() == 0 || healths[GameInfo.opponent]!!.get() == 0)) endGame()
        else bothHit = false
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        screenRect.width = stage.viewport.worldWidth
        screenRect.height = stage.viewport.worldHeight
        // Redraw on resize
        stage.clear()
        drawLayout()
    }

    fun updateLayout(){
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
        game.init()
        drawLayout()
        // Init game model and camera
        camera.setToOrtho(false, Configuration.gameWidth, Configuration.gameHeight)

        healths = HashMap<String, HealthComponent>()
        healths.set(GameInfo.player, ComponentMapper.health.get(game.ship1))
        healths.set(GameInfo.opponent, ComponentMapper.health.get(game.ship2))

        //Hearth listeners
        fbic.heartListener("host", this)
        fbic.heartListener("player_2", this)
        //gameState listener
        fbic.getCurrentState(game)
        //Opponent ready listener
        fbic.checkIfOpponentReady(this)
    }
    fun bothReady(opponentMovingTo : Int, opponentShooting : Int, bothHit: Boolean) {
        println("BOTH READY, SCREEN")
        this.bothHit = bothHit
        InputHandler.enemyPosition = opponentMovingTo
        InputHandler.enemyTrajectoryPosition = opponentShooting
        game.fireShots()
    }

    fun endGame() {
        //This player won
        fbic.updateCurrentGameState(GameState.GAME_OVER)
        var score: Int
        if (healths[GameInfo.player]!!.get() != 0) {
            score = 10+healths[GameInfo.player]!!.get()*5
            fbic.updateScore(score)
        }else {
            score = -5
            fbic.updateScore(score)
        }
        stage.addActor(PopupComponent(false, EndGameComponent(score, game, gameController)))
    }

    fun updateUi() {
        // Update display text
        statusText.setText(game.state.text)
        // Update end turn
        btnEndTurn.isDisabled = game.state != GameState.SETUP
    }

    fun drawLayout() {
        var table = VisTable()

        val btnBack = VisTextButton("Return to main menu")
        btnBack.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                gameController.updateCurrentGame("null", "null", "null")
                gameController.changeScreen(MainMenuScreen::class.java)
            }
        })
        // Draw topbox
        val tbox = Image(TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("topbox.png")))))
        tbox.setSize(360f, 60f)
        tbox.setPosition((stage.width/2) - 180f, stage.height - 60f)
        stage.addActor(tbox)

        // Draw status display text
        statusText.setPosition((stage.width/2) - 180f, stage.height - 50f)
        statusText.setSize(360f, 60f)
        statusText.setAlignment(1) // center text
        stage.addActor(statusText)

        // Draw bottombox
        val bbox = Image(TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("bottombox.png")))))
        bbox.setSize(670f, 90f)
        bbox.setPosition((stage.width/2) - 670/2f, -15f)
        stage.addActor(bbox)
        val tubox = Image(TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("turnbox.png")))))
        tubox.setSize(260f, 70f)
        tubox.setPosition((stage.width/2) - 130f, 50f)
        stage.addActor(tubox)

        // Draw end turn button
        btnEndTurn.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                println("end turn clicked")
                game.updatePosition()
                game.changeState(game.state.signal())
            }
        })
        btnEndTurn.setSize(110f, 25f)
        btnEndTurn.setPosition((stage.width/2) - 55f, 70f)
        stage.addActor(btnEndTurn)

        val btnChangeState = VisTextButton("Change state")
        btnChangeState.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                game.changeState(game.state.signal())
            }
        })
        if (game.state == GameState.LOBBY_DELETED || GameInfo.currentGame == "null") {
            // Create the layout
            table.columnDefaults(0).pad(10f)
            table.setFillParent(true)
            table.add(btnBack).size(stage.width / 2, 45.0f)
            table.row()
            //table.add(btnChangeState).size(stage.width/2, 45.0f)
            stage.addActor(table)
        }

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

            var lobbycode = VisLabel(GameInfo.currentGame)
            lobbycode.setPosition(stage.width/2-lobbycode.width/2, 0+15f)
            stage.addActor(lobbycode)

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
                            if (game.state == GameState.SETUP) { // Let player move position only if state is on SETUP
                                InputHandler.playerPosition = Integer.parseInt(btn.text.toString())
                                print("PS ")
                                println(InputHandler.playerPosition)
                            }
                        }
                    })
                else
                    btn.addListener(object : ChangeListener() {
                        override fun changed(event: ChangeEvent, actor: Actor) {
                            if (game.state == GameState.SETUP) {
                                InputHandler.playerTrajectoryPosition = Integer.parseInt(btn.text.toString())
                                print("PTS ")
                                println(InputHandler.playerTrajectoryPosition)
                            }
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
        updateUi()
        Gdx.app.log("VIEW", "Game loaded")
    }
}