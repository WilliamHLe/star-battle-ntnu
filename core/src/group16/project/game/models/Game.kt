package group16.project.game.models

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import group16.project.game.Configuration
import group16.project.game.ecs.Engine
import group16.project.game.ecs.component.BodyComponent
import group16.project.game.ecs.component.PositionComponent
import group16.project.game.ecs.component.TextureComponent
import group16.project.game.ecs.component.VelocityComponent

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
        engine.addEntity(createHero())
        engine.addEntity(createBG())
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

    // Should probably move these create methods to a Factory class
    private fun createBG() = engine.createEntity().also { entity ->
        entity.add(engine.createComponent(PositionComponent::class.java).apply {
            z = 0f
        })
        entity.add(engine.createComponent(BodyComponent::class.java).apply {
            rectangle.setWidth(WIDTH)
            rectangle.setHeight(HEIGHT)
        })
        entity.add(engine.createComponent(TextureComponent::class.java).apply {
            texture = Texture("bg.png")
        })
    }

    private fun createHero() = engine.createEntity().also { entity ->
        entity.add(engine.createComponent(PositionComponent::class.java).apply {
            z = 1f
        })
        entity.add(engine.createComponent(BodyComponent::class.java).apply {
            rectangle.setWidth(100f)
            rectangle.setHeight(100f)
        })
        entity.add(engine.createComponent(TextureComponent::class.java).apply {
            texture = Texture("badlogic.jpg")
        })
        entity.add(engine.createComponent(VelocityComponent::class.java).apply {
            x = -80f
            y = -100f
        })
    }
    companion object {
        private val WIDTH = Configuration.gameWidth
        private val HEIGHT = Configuration.gameHeight
    }
}
