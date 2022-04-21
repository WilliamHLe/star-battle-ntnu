package group16.project.game.models

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import group16.project.game.Configuration
import group16.project.game.controllers.InputHandler
import group16.project.game.ecs.Engine
import group16.project.game.ecs.component.HealthComponent
import group16.project.game.ecs.utils.EntityFactory
import group16.project.game.views.GameScreen

class Game(private val screenRect: Rectangle, private val camera: OrthographicCamera, private val gameScreen: GameScreen) {
    private val batch = SpriteBatch()
    private val engine by lazy {
        Engine(
                batch = batch,
                camera = camera,
                screenRect = screenRect
        )
    }
    lateinit var state: GameState

    lateinit var ship1: Entity
    lateinit var ship2: Entity

    lateinit var shield1: Entity
    lateinit var shield2: Entity

    fun init() {
        state = GameState.START
        // Ship
        ship1 = EntityFactory.createUfo(engine, -10f, 0f, true)
        ship2 = EntityFactory.createUfo(engine, Configuration.gameWidth - 170f, 0f, false)
        engine.addEntity(ship1)
        engine.addEntity(ship2)
        // Shields
        shield1 = EntityFactory.createShield(engine, 190f, 0f, true)
        shield2 = EntityFactory.createShield(engine, Configuration.gameWidth - 370f, 0f, false)

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
        Gdx.app.log("MODEL", "Engine loaded")
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
        println("FIRE SHOT, SCREEN")
        if (GameInfo.player == "host") {
            gameScreen.fbic.updateCurrentGameState(GameState.ANIMATION)
        }
        gameScreen.fbic.updatePlayerHealth()
        gameScreen.fbic.resetReady()

        val padding = (Configuration.gameHeight - 4*100) / 2
        val buttonHeight = 100

        var startPosY = InputHandler.playerPosition * buttonHeight + padding
        var shootPosX = Configuration.gameWidth - 10f - 100f;
        var shootPosY = InputHandler.playerTrajectoryPosition * buttonHeight + padding
        println("Fire shots")
        engine.addEntity(EntityFactory.createTrajectory(engine, 10f, startPosY, true, shootPosX, shootPosY))

        startPosY = InputHandler.enemyPosition * buttonHeight + padding
        shootPosX = 10f;
        shootPosY = InputHandler.enemyTrajectoryPosition * buttonHeight + padding
        println("Enemy fire shots")
        engine.addEntity(EntityFactory.createTrajectory(engine, Configuration.gameWidth - 10f - 100f, startPosY, false, shootPosX, shootPosY))

        if (GameInfo.player == "host") {
            gameScreen.fbic.updateCurrentGameState(GameState.SETUP)
        }
    }

    fun changeState(state: GameState) {
        println(state.toString() + this.state.toString())
        //state = state.signal()
        if (!(this.state == GameState.SETUP && state == GameState.ANIMATION)) {
            this.state = state
            gameScreen.updateUi()
        }
        if (this.state == GameState.SETUP){
            InputHandler.playerShieldPosition = -1
            InputHandler.enemyShieldPosition = -1
            gameScreen.clicked = false
        }
    }

    fun render(delta: Float) {
        Gdx.gl.glClearColor(0.5f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        engine.update(delta)
    }

    fun dispose() {
        engine.dispose()
    }

    fun hide() {
        engine.removeAllEntities()
        engine.clearPools()
    }
}
