package group16.project.game.models

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.InputEvent
import group16.project.game.Configuration
import group16.project.game.ecs.utils.InputHandler
import group16.project.game.ecs.Engine
import group16.project.game.ecs.component.HealthComponent
import group16.project.game.ecs.utils.EntityFactory
import group16.project.game.views.GameScreen

class Game(private val camera: OrthographicCamera, private val gameScreen: GameScreen) {
    private val batch = SpriteBatch()
    private val engine by lazy {
        Engine(
                batch = batch,
                camera = camera,
        )
    }
    lateinit var state: GameState

    lateinit var ship1: Entity
    lateinit var ship2: Entity

    lateinit var shield1: Entity
    lateinit var shield2: Entity
    private var timerValue = 0f

    fun init() {
        state = GameState.START
        // Ship
        ship1 = EntityFactory.createUfo(engine, -10f, 0f, true)
        ship2 = EntityFactory.createUfo(engine, Configuration.gameWidth - 170f, 0f, false)
        engine.addEntity(ship1)
        engine.addEntity(ship2)
        // Shields
        shield1 = EntityFactory.createShield(engine, 190f, 0f, true)
        shield2 = EntityFactory.createShield(engine, Configuration.gameWidth - 190f - 64f, 0f, false)

        engine.addEntity(shield1)
        engine.addEntity(shield2)
        // Target
        engine.addEntity(EntityFactory.createTarget(engine, Configuration.gameWidth - 160f, 0f, true))
        engine.addEntity(EntityFactory.createTarget(engine, 10f, 0f, false))
        // Background
        engine.addEntity(EntityFactory.createBG(engine, 0f, 0f))
        //Hearts
        engine.addEntity(EntityFactory.createHearts(engine, 10f, Configuration.gameHeight - 60f, ship1.getComponent(HealthComponent::class.java)))
        engine.addEntity(EntityFactory.createHearts(engine, Configuration.gameWidth - 60f - 160f, Configuration.gameHeight - 60f, ship2.getComponent(HealthComponent::class.java)))
        Gdx.app.log("GAME", "Engine loaded")
    }

    fun updatePosition() {
        if (state == GameState.SETUP) {
            gameScreen.fbic.setPlayersChoice(
                InputHandler.playerPosition,
                InputHandler.playerTrajectoryPosition,
                InputHandler.playerShieldPosition,
                gameScreen
            )
        }
    }

    fun deleteLobby() {
        gameScreen.fbic.updateCurrentGameState(GameState.LOBBY_DELETED)
        gameScreen.fbic.deleteLobby()
        gameScreen.usedShield = false
        InputHandler.playerShieldPosition = -1
        InputHandler.enemyShieldPosition = -1
        gameScreen.gameController.updateCurrentGame("null", "null", "null")
    }

    fun fireShots() {
        Gdx.app.debug("GAME", "Firing shots")
        if (GameInfo.player == "host") {
            gameScreen.fbic.updateCurrentGameState(GameState.ANIMATION)
        }
        gameScreen.fbic.updatePlayerHealth()
        gameScreen.fbic.resetReady()

        val padding = (Configuration.gameHeight - 4*100) / 2
        val buttonHeight = 100

        var startPosY = InputHandler.playerPosition * buttonHeight + padding
        var shootPosX = Configuration.gameWidth - 10f - 100f
        var shootPosY = InputHandler.playerTrajectoryPosition * buttonHeight + padding
        Gdx.app.debug("GAME", "Creating host trajectory")
        engine.addEntity(EntityFactory.createTrajectory(engine, 10f, startPosY, true, shootPosX, shootPosY))

        startPosY = InputHandler.enemyPosition * buttonHeight + padding
        shootPosX = 10f
        shootPosY = InputHandler.enemyTrajectoryPosition * buttonHeight + padding
        Gdx.app.debug("GAME", "Creating player_2 trajectory")
        engine.addEntity(EntityFactory.createTrajectory(engine, Configuration.gameWidth - 10f - 100f, startPosY, false, shootPosX, shootPosY))

        if (GameInfo.player == "host") {
            gameScreen.fbic.updateCurrentGameState(GameState.SETUP)
        }
    }

    fun changeState(state: GameState) {
        Gdx.app.debug("GAME", "changeState called ${this.state} -> $state")
        if (!(this.state == GameState.SETUP && state == GameState.ANIMATION || this.state == GameState.WAITING && state == GameState.SETUP)) {
            this.state = state
            gameScreen.updateUi()
            timerValue = 0f
            gameScreen.timer.value = 0f
        }
        if (this.state == GameState.SETUP){
            InputHandler.playerShieldPosition = -1
            InputHandler.enemyShieldPosition = -1
            gameScreen.clicked = false
        }

    }

    fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        engine.update(delta)

        // Setup phase timer
        if (this.state == GameState.SETUP) {
            timerValue += Gdx.graphics.deltaTime
            gameScreen.timer.value = timerValue
            if (timerValue >= 100f) {
                // Programmatically click on "End Turn" button after timer ends
                val ie = InputEvent()
                ie.type = InputEvent.Type.touchDown
                gameScreen.btnEndTurn.fire(ie)
                ie.type = InputEvent.Type.touchUp
                gameScreen.btnEndTurn.fire(ie)
                // Reset timer
                timerValue = 0f
                gameScreen.timer.value = 0f
            }
        }
    }

    fun dispose() {
        engine.dispose()
    }

    fun hide() {
        engine.removeAllEntities()
        engine.clearPools()
    }
}
