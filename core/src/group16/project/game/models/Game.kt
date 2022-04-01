package group16.project.game.models

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import group16.project.game.Configuration
import group16.project.game.ecs.Engine
import group16.project.game.ecs.component.HealthComponent
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
        val ship1: Entity = EntityFactory.createUfo(engine, 10f, 0f, true)
        val ship2: Entity = EntityFactory.createUfo(engine, Configuration.gameWidth - 160f, 0f, false)
        engine.addEntity(ship1)
        engine.addEntity(ship2)
        // Target
        engine.addEntity(EntityFactory.createTarget(engine, Configuration.gameWidth - 130f, 0f, true))
        engine.addEntity(EntityFactory.createTarget(engine, 30f, 0f, false))
        // Background
        engine.addEntity(EntityFactory.createBG(engine, 0f, 0f))
        // Hearts
        engine.addEntity(EntityFactory.createHearts(engine, 10f, Configuration.gameHeight - 60f, ship1.getComponent(HealthComponent::class.java)))
        engine.addEntity(EntityFactory.createHearts(engine, Configuration.gameWidth - 50f - 130f, Configuration.gameHeight - 60f, ship1.getComponent(HealthComponent::class.java)))
        Gdx.app.log("MODEL", "Engine loaded")
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
