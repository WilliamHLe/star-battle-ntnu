package group16.project.game.models

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import group16.project.game.Configuration
import group16.project.game.ecs.Engine
import group16.project.game.ecs.component.BodyComponent
import group16.project.game.ecs.component.PositionComponent

class Game(private val screenRect: Rectangle, private val camera: OrthographicCamera) {
    private val batch = SpriteBatch()
    private val engine by lazy {
        Engine(
                batch = batch,
                camera = camera,
                screenRect = screenRect
        )
    }
    // Ship
    // Todo: split ship and hearts out to own model later on?
    private val ship1: Entity = ShipFactory.createShip(engine, 10f, 0f)
    private val ship2: Entity = ShipFactory.createShip(engine, WIDTH - 160f, 0f)
    fun init() {
        // Background
        engine.addEntity(GameFactory.createBG(engine))
        engine.addEntity(ship1)
        engine.addEntity(ship2)
        // Hearts
        engine.addEntity(GameFactory.createHearts(engine, 10f, HEIGHT - 60f))
        engine.addEntity(GameFactory.createHearts(engine, 70f, HEIGHT - 60f))
        engine.addEntity(GameFactory.createHearts(engine, 130f, HEIGHT - 60f))

        engine.addEntity(GameFactory.createHearts(engine, WIDTH - 50f - 10f, HEIGHT - 60f))
        engine.addEntity(GameFactory.createHearts(engine, WIDTH - 50f - 70f, HEIGHT - 60f))
        engine.addEntity(GameFactory.createHearts(engine, WIDTH - 50f - 130f, HEIGHT - 60f))

        Gdx.app.log("MODEL", "Engine loaded")
    }
    fun move(pos: Int) {
        val bodyComponentMapper = ComponentMapper.getFor(BodyComponent::class.java)
        val positionComponentMapper = ComponentMapper.getFor(PositionComponent::class.java)
        // bodyComponentMapper[ship1].rectangle
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

    companion object {
        private val WIDTH = Configuration.gameWidth
        private val HEIGHT = Configuration.gameHeight
    }

}
