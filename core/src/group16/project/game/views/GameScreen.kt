package group16.project.game.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import group16.project.game.Configuration
import group16.project.game.StarBattle
import group16.project.game.models.Game
import com.badlogic.gdx.utils.Array
import group16.project.game.ecs.utils.InputHandler
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.kotcrab.vis.ui.widget.*
import group16.project.game.views.components.PopupComponent
import group16.project.game.views.components.SlideshowComponent
import group16.project.game.ecs.component.HealthComponent
import group16.project.game.ecs.utils.ComponentMapper
import group16.project.game.models.GameState
import group16.project.game.ecs.component.ShieldComponent
import group16.project.game.models.FirebaseInterface
import group16.project.game.models.GameInfo
import group16.project.game.views.components.EndGameComponent

class GameScreen(val gameController: StarBattle, val fbic: FirebaseInterface) : View() {
    private val game = Game(camera, this)
    private val statusText = VisLabel("")
    private val btnPlaceShield = VisImageButton(TextureRegionDrawable(TextureRegion(TextureHandler.textures["ICON_SHIELD"])))
    private lateinit var healths: HashMap<String, HealthComponent>
    private var bothHit = false
    private lateinit var shields: HashMap<String, ShieldComponent>
    val btnEndTurn = VisTextButton("End Turn")
    val timer = VisProgressBar(0f, 60f, 0.1f, false)
    var clicked = false
    var usedShield = false

    override fun draw(delta: Float) {
        game.render(delta)
    }

    fun updateHealth(player: String, health: Int){
        healths[player]!!.set(health)
        if (!bothHit && (healths[GameInfo.player]!!.hp == 0 || healths[GameInfo.opponent]!!.hp == 0)) endGame()
        else bothHit = false
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        // Redraw on resize
        stage.clear()
        drawLayout()
    }

    override fun dispose() {
        super.dispose()
        game.dispose()
    }

    override fun hide() {
        game.hide()
        stage.clear()
    }

    override fun init() {
        // Init game model and camera
        game.init()
        drawLayout()
        camera.setToOrtho(false, Configuration.gameWidth, Configuration.gameHeight)

        healths = HashMap()
        healths[GameInfo.player] = ComponentMapper.health.get(game.ship1)
        healths[GameInfo.opponent] = ComponentMapper.health.get(game.ship2)

        shields = HashMap()
        shields[GameInfo.player] = ComponentMapper.shield.get(game.shield1)
        shields[GameInfo.opponent] = ComponentMapper.shield.get(game.shield2)

        // Heart listeners
        fbic.heartListener("host", this)
        fbic.heartListener("player_2", this)

        // GameState listener
        fbic.getCurrentState(game)
        // Opponent ready listener
        fbic.checkIfOpponentReady(this)
        btnPlaceShield.isDisabled = false
    }
    fun bothReady(opponentMovingTo : Int, opponentShooting : Int, bothHit: Boolean, shieldPosition: Int) {
        this.bothHit = bothHit
        InputHandler.enemyPosition = opponentMovingTo
        InputHandler.enemyTrajectoryPosition = opponentShooting
        InputHandler.enemyShieldPosition = shieldPosition
        game.fireShots()
    }

    private fun endGame() {
        // A player has won
        fbic.updateCurrentGameState(GameState.GAME_OVER)
        val score: Int
        if (healths[GameInfo.player]!!.hp == 0 && healths[GameInfo.opponent]!!.hp == 0){
            score = 0
            fbic.updateScore(score)
        }else if (healths[GameInfo.player]!!.hp != 0) {
            score = 10+healths[GameInfo.player]!!.hp*5
            fbic.updateScore(score)
        }else {
            score = -5
            fbic.updateScore(score)
        }
        stage.addActor(PopupComponent(EndGameComponent(score, game, gameController), isFullscreen = false, hasCloseButton = false))
    }

    fun updateUi() {
        // Update display text
        statusText.setText(game.state.text)
        // Update end turn
        btnEndTurn.isDisabled = game.state != GameState.SETUP
        btnPlaceShield.isDisabled = game.state == GameState.START || usedShield || clicked

    }

    private fun drawLayout() {
        // Draw topbox
        val tbox = Image(TextureRegionDrawable(TextureRegion(TextureHandler.textures["UI_TOPBOX"])))
        tbox.setSize(360f, 60f)
        tbox.setPosition((stage.width/2) - 180f, stage.height - 60f)
        stage.addActor(tbox)

        // Draw status display text
        statusText.setPosition((stage.width/2) - 180f, stage.height - 50f)
        statusText.setSize(360f, 60f)
        statusText.setAlignment(1) // center text
        stage.addActor(statusText)

        // Draw bottombox
        val tubox = Image(TextureRegionDrawable(TextureRegion(TextureHandler.textures["UI_TURNBOX"])))
        tubox.setSize(260f, 70f)
        tubox.setPosition((stage.width/2) - 130f, 58f)
        stage.addActor(tubox)
        val bbox = Image(TextureRegionDrawable(TextureRegion(TextureHandler.textures["UI_BOTTOMBOX"])))
        bbox.setSize(670f, 90f)
        bbox.setPosition((stage.width/2) - 670/2f, -5f)
        stage.addActor(bbox)

        // Draw setup timer progress bar
        timer.setSize(stage.width, 50f)
        timer.setPosition(0f, -20f)
        stage.addActor(timer)

        //Draw lobbyCode label
        val lobbycode = VisLabel(GameInfo.currentGame)
        lobbycode.setPosition(stage.width/2-lobbycode.width/2, stage.height-tbox.height-20)
        stage.addActor(lobbycode)

        // Draw place shield button
        btnPlaceShield.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                Gdx.app.debug("GAME","Shield power-up clicked")
                if (!clicked) {
                    Gdx.app.debug("GAME","Placed a shield")
                    InputHandler.playerShieldPosition = InputHandler.playerPosition
                    btnPlaceShield.setPosition(-10000f, -1000f)
                }
            }
        })
        btnPlaceShield.setSize(50f, 50f)
        btnPlaceShield.setPosition((stage.width-bbox.width)/2+50, 16f)
        stage.addActor(btnPlaceShield)

        // Draw menu icon
        val cogIcon = Image(TextureRegionDrawable(TextureRegion(TextureHandler.textures["ICON_MENU"])))
        cogIcon.setSize(50f, 58f)
        cogIcon.setPosition((stage.width/2) + 670/2f - 100f, 11f)
        val btnMenu = VisTextButton("")
        btnMenu.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                // Continue button
                val popup = PopupComponent(null, isFullscreen = true, hasCloseButton = false)
                val btnContinue = VisTextButton("Continue game")
                btnContinue.addListener(object : ChangeListener() {
                    override fun changed(event: ChangeEvent, actor: Actor) {
                        popup.closePopup()
                    }
                })
                // Return to main menu button
                val btnBack = VisTextButton("Return to main menu")
                btnBack.addListener(object : ChangeListener() {
                    override fun changed(event: ChangeEvent, actor: Actor) {
                        gameController.updateCurrentGame("null", "null", "null")
                        usedShield = false
                        InputHandler.playerShieldPosition = -1
                        InputHandler.enemyShieldPosition = -1
                        gameController.changeScreen(MainMenuScreen::class.java)
                    }
                })
                // Create table layout
                val pauseTable = VisTable()
                pauseTable.columnDefaults(0).pad(10f)
                pauseTable.setFillParent(true)
                pauseTable.add(btnContinue).size(stage.width/2, 45.0f)
                pauseTable.row()
                pauseTable.add(btnBack).size(stage.width / 2, 45.0f)

                popup.setChild(pauseTable)
                stage.addActor(popup)
            }
        })
        btnMenu.setSize(50f, 58f)
        btnMenu.setPosition((stage.width/2) + 670/2f - 100f, 11f)
        btnMenu.setColor(0f,0f,0f,0f)
        stage.addActor(cogIcon)
        stage.addActor(btnMenu)

        // Draw help icon
        val helpIcon = Image(TextureRegionDrawable(TextureRegion(TextureHandler.textures["ICON_HELP"])))
        helpIcon.setSize(50f, 58f)
        helpIcon.setPosition((stage.width/2) + 670/2f - 155f, 11f)
        val btnHelp = VisTextButton("")
        btnHelp.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                stage.addActor(PopupComponent(SlideshowComponent(Configuration.slides), true))
            }
        })
        btnHelp.setSize(50f, 58f)
        btnHelp.setPosition((stage.width/2) + 670/2f - 155f, 11f)
        btnHelp.setColor(0f,0f,0f,0f)
        stage.addActor(helpIcon)
        stage.addActor(btnHelp)

        // Draw end turn button
        btnEndTurn.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                if (!clicked) {
                    println("end turn clicked")
                    clicked = true
                    if (InputHandler.playerShieldPosition >= 0) usedShield = true
                    game.updatePosition()
                    game.changeState(game.state.signal())
                }
            }
        })
        btnEndTurn.setSize(140f, 30f)
        btnEndTurn.setPosition((stage.width/2) - 70f, 78f)
        stage.addActor(btnEndTurn)


        // Draw spots
        for (i in 0..1) {
            // Draw table
            val vbox = VisTable()
            vbox.width = 150f
            vbox.height = stage.height
            vbox.setPosition(i * (stage.width - 150f), 0f)

            // Draw selection markers
            val padding = (Configuration.gameHeight - GameInfo.slots*100) / 2
            val longbar1 = Image(TextureRegionDrawable(TextureRegion(TextureHandler.textures["LONGBAR"])))
            longbar1.setSize(40f, 100f*GameInfo.slots + 40f + 20f)
            longbar1.setPosition((stage.width - 150f)*i - 10f, padding - 20f - 10f)
            stage.addActor(longbar1)
            val longbar2 = Image(TextureRegionDrawable(TextureRegion(TextureHandler.textures["LONGBAR"])))
            longbar2.setSize(40f, 100f*GameInfo.slots + 40f + 20f)
            longbar2.setPosition((stage.width - 155f)*i + 125f, padding - 20f - 10f)
            stage.addActor(longbar2)
            val bbar = Image(TextureRegionDrawable(TextureRegion(TextureHandler.textures["BAR"])))
            bbar.setSize(200f, 40f)
            bbar.setPosition((stage.width - 150f)*i - 20f, padding - 20f)
            stage.addActor(bbar)

            // Draw buttons
            val buttons: Array<VisTextButton> = Array()
            for (btnNum in (GameInfo.slots-1) downTo 0) { // Index starting at 0
                buttons.add(VisTextButton(btnNum.toString()))
            }
            println(buttons.size)
            for ((j, btn) in buttons.withIndex()) {
                btn.setColor(0f,0f,0f,0.2f)
                if (i == 0)
                    btn.addListener(object : ChangeListener() {
                        override fun changed(event: ChangeEvent, actor: Actor) {
                            if (game.state == GameState.SETUP) { // Let player move position only if state is on SETUP
                                InputHandler.playerPosition = Integer.parseInt(btn.text.toString())
                                if (!usedShield && InputHandler.playerShieldPosition >= 0) InputHandler.playerShieldPosition = InputHandler.playerPosition
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
                val bar = Image(TextureRegionDrawable(TextureRegion(TextureHandler.textures["BAR"])))
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