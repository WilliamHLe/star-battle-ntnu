package group16.project.game.models

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import group16.project.game.Configuration
import group16.project.game.controllers.InputHandler
import group16.project.game.ecs.Engine
import group16.project.game.ecs.utils.EntityFactory

class Game(private val screenRect: Rectangle, private val camera: OrthographicCamera) {
    private val batch = SpriteBatch()
    private val engine by lazy {
        Engine(
                batch = batch,
                camera = camera,
                screenRect = screenRect
        )
    }

    fun init() {
        // Ship
        val ship1: Entity = EntityFactory.createUfo(engine, -10f, 0f, true)
        val ship2: Entity = EntityFactory.createUfo(engine, Configuration.gameWidth - 170f, 0f, false)
        engine.addEntity(ship1)
        engine.addEntity(ship2)
        // Target
        engine.addEntity(EntityFactory.createTarget(engine, Configuration.gameWidth - 160f, 0f, true))
        engine.addEntity(EntityFactory.createTarget(engine, 10f, 0f, false))
        // Background
        engine.addEntity(EntityFactory.createBG(engine, 0f, 0f))
        // Hearts
        engine.addEntity(EntityFactory.createHearts(engine, 10f, Configuration.gameHeight - 70f))
        engine.addEntity(EntityFactory.createHearts(engine, 70f, Configuration.gameHeight - 70f))
        engine.addEntity(EntityFactory.createHearts(engine, 130f, Configuration.gameHeight - 70f))

        engine.addEntity(EntityFactory.createHearts(engine, Configuration.gameWidth - 70f - 10f, Configuration.gameHeight - 70f))
        engine.addEntity(EntityFactory.createHearts(engine, Configuration.gameWidth - 70f - 70f, Configuration.gameHeight - 70f))
        engine.addEntity(EntityFactory.createHearts(engine, Configuration.gameWidth - 70f - 130f, Configuration.gameHeight - 70f))

        // Trajectories
        engine.addEntity(EntityFactory.createTrajectory(engine, Configuration.gameWidth - 160f, 0f, true, 10f, 0f))
        engine.addEntity(EntityFactory.createTrajectory(engine, 10f, 0f, false, Configuration.gameWidth - 160f, 0f))

        Gdx.app.log("MODEL", "Engine loaded")
    }
    fun fireShots() {
        val padding = (Configuration.gameHeight - 4*100) / 2
        val buttonHeight = 100
        var startPosY = InputHandler.playerPosition * buttonHeight + padding
        var shootPosX = Configuration.gameWidth - 10f - 100f;
        var shootPosY = InputHandler.playerTrajectoryPosition * buttonHeight + padding
        InputHandler.fireShots = true
        println("Fire shots")
        engine.addEntity(EntityFactory.createTrajectory(engine, 10f, startPosY, true, shootPosX, shootPosY))
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
