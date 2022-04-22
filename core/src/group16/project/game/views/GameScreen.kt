package group16.project.game.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
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
import java.util.ArrayList
import group16.project.game.controllers.InputHandler
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.kotcrab.vis.ui.widget.*
import group16.project.game.views.components.ImageSlideshowComponent
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
    private val screenRect = Rectangle(0f, 0f, Configuration.gameWidth, Configuration.gameHeight)
    private val game = Game(screenRect, camera, this)

    private val statusText = VisLabel("")
    val btnEndTurn = VisTextButton("End Turn")
    val timer = VisProgressBar(0f, 100f, 0.1f, false)
    private val btnPlaceShield = VisImageButton(TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("Shieldbtn.png")))))
    private lateinit var healths: HashMap<String, HealthComponent>
    var bothHit = false
    var clicked = false
    private lateinit var shields: HashMap<String, ShieldComponent>
    var usedShield = false

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

        shields = HashMap<String, ShieldComponent>()
        shields.set(GameInfo.player, ComponentMapper.shield.get(game.shield1))
        shields.set(GameInfo.opponent, ComponentMapper.shield.get(game.shield2))

        //Hearth listeners
        fbic.heartListener("host", this)
        fbic.heartListener("player_2", this)
        //Shield listeners
        //fbic.shieldListener("host", this)
        //fbic.shieldListener("player_2", this)
        //gameState listener
        fbic.getCurrentState(game)
        //Opponent ready listener
        fbic.checkIfOpponentReady(this)
        btnPlaceShield.isDisabled = false
    }
    fun bothReady(opponentMovingTo : Int, opponentShooting : Int, bothHit: Boolean, shieldPosition: Int) {
        println("BOTH READY, SCREEN")
        this.bothHit = bothHit
        InputHandler.enemyPosition = opponentMovingTo
        InputHandler.enemyTrajectoryPosition = opponentShooting
        InputHandler.enemyShieldPosition = shieldPosition
        game.fireShots()
    }

    fun endGame() {
        //This player won
        fbic.updateCurrentGameState(GameState.GAME_OVER)
        var score: Int
        if (healths[GameInfo.player]!!.get() == 0 && healths[GameInfo.opponent]!!.get() == 0){
            score = 0
            fbic.updateScore(score)
        }else if (healths[GameInfo.player]!!.get() != 0) {
            score = 10+healths[GameInfo.player]!!.get()*5
            fbic.updateScore(score)
        }else {
            score = -5
            fbic.updateScore(score)
        }
            stage.addActor(PopupComponent(EndGameComponent(score, game, gameController), false, false))
    }

    fun updateUi() {
        // Update display text
        statusText.setText(game.state.text)
        // Update end turn
        btnEndTurn.isDisabled = game.state != GameState.SETUP
        btnPlaceShield.isDisabled = game.state == GameState.START || usedShield

    }

    fun drawLayout() {
        var table = VisTable()

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
        bbox.setPosition((stage.width/2) - 670/2f, -5f)
        stage.addActor(bbox)
        val tubox = Image(TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("turnbox.png")))))
        tubox.setSize(260f, 70f)
        tubox.setPosition((stage.width/2) - 130f, 60f)
        stage.addActor(tubox)

        // Draw setup timer progress bar
        timer.setSize(stage.width, 50f)
        timer.setPosition(0f, -20f)
        stage.addActor(timer)

        //Draw lobbyCode label
        var lobbycode = VisLabel(GameInfo.currentGame)
        lobbycode.setPosition(stage.width/2-lobbycode.width/2, stage.height-tbox.height-20)
        stage.addActor(lobbycode)

        // Draw place shield button
        btnPlaceShield.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                println("Placed a shield")
                InputHandler.playerShieldPosition = InputHandler.playerPosition
                btnPlaceShield.setPosition(-10000f, -1000f)
            }
        })
        btnPlaceShield.setSize(50f, 50f)
        btnPlaceShield.setPosition((stage.width-bbox.width)/2+50, 16f)
        stage.addActor(btnPlaceShield)

        // Draw menu icon
        val cogIcon = Image(TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("cog_icon.png")))))
        cogIcon.setSize(50f, 58f)
        cogIcon.setPosition((stage.width/2) + 670/2f - 100f, 11f)
        val btnMenu = VisTextButton("")
        btnMenu.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                println("PAUSE")
                // Continue button
                val popup = PopupComponent(null, true, false)
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
        val helpIcon = Image(TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("help_icon.png")))))
        helpIcon.setSize(50f, 58f)
        helpIcon.setPosition((stage.width/2) + 670/2f - 155f, 11f)
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
                slides.add(ImageSlideshowComponent("tutorial6.png", "When someone are have 0 hearts the game ends. The winner will get points added to their total score and the looser will be deducted points from theirs. If it a tie no points will be deducted or added. A players total score represent how many games they have won and it is used for the highscore list"))
                slides.add(ImageSlideshowComponent("tutorial7.png", "Here is a summary of the most important parts of the game. You can access this slideshow in game by clicking on the help screen button. By clicking on the menu screen you can leave the lobby, but you can't return. Have fun!"))

                stage.addActor(PopupComponent(SlideshowComponent(slides), true))
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
        btnEndTurn.setSize(110f, 30f)
        btnEndTurn.setPosition((stage.width/2) - 55f, 80f)
        stage.addActor(btnEndTurn)


        val btnChangeState = VisTextButton("Change state")
        btnChangeState.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                game.changeState(game.state.signal())
            }
        })


        // Draw spots
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
                val bar = Image(TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("bar.png")))))
                bar.setSize(200f, 40f)
                bar.setPosition((stage.width - 160f)*i - 20f, padding - 20f + 100f*(j+1))
                stage.addActor(bar)
            }
            stage.addActor(vbox)
        }
        updateUi()
        Gdx.app.log("VIEW", "Game loaded")

        println(stage.viewport.worldWidth)
        println(Gdx.graphics.width)
    }
}