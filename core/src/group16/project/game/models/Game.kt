package group16.project.game.models

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import group16.project.game.Configuration
import group16.project.game.ecs.Engine

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
        // Background
        engine.addEntity(GameFactory.createBG(engine))
        // Ship
        // Todo: split ship out to own model
        val ship1: Entity = ShipFactory.createShip(engine, 10f, 0f)
        val ship2: Entity = ShipFactory.createShip(engine, WIDTH - 110f, 0f)
        engine.addEntity(ship1)
        engine.addEntity(ship2)
        // Hearts
        engine.addEntity(GameFactory.createHearts(engine, true))
        engine.addEntity(GameFactory.createHearts(engine, false))

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

    companion object {
        private val WIDTH = Configuration.gameWidth
        private val HEIGHT = Configuration.gameHeight
    }

}
