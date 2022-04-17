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


    fun init() {
        state = GameState.START
        // Ship
        ship1 = EntityFactory.createUfo(engine, -10f, 0f, true)
        ship2 = EntityFactory.createUfo(engine, Configuration.gameWidth - 170f, 0f, false)
        engine.addEntity(ship1)
        engine.addEntity(ship2)
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
                gameScreen
            )
        }
    }

    fun fireShots() {
        if (GameInfo.player == "host") {
            gameScreen.fbic.updateCurrentGameState(GameState.ANIMATION)
        }
        gameScreen.fbic.updatePlayerHealth()
        gameScreen.fbic.resetReady()

        val padding = (Configuration.gameHeight - 4*100) / 2
        val buttonHeight = 100
        val startPosY = InputHandler.playerPosition * buttonHeight + padding
        val shootPosX = Configuration.gameWidth - 10f - 100f;
        val shootPosY = InputHandler.playerTrajectoryPosition * buttonHeight + padding
        InputHandler.fireShots = true
        println("Fire shots")
        engine.addEntity(EntityFactory.createTrajectory(engine, 10f, startPosY, true, shootPosX, shootPosY))
    }



    fun changeState(state: GameState) {
        //state = state.signal()
        this.state = state
        gameScreen.updateUi()
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
