package group16.project.game.models


import com.badlogic.gdx.graphics.Texture
import group16.project.game.Configuration
import group16.project.game.ecs.Engine
import group16.project.game.ecs.component.BodyComponent
import group16.project.game.ecs.component.PositionComponent
import group16.project.game.ecs.component.TextureComponent

class GameFactory() {

    companion object {
        private val WIDTH = Configuration.gameWidth
        private val HEIGHT = Configuration.gameHeight

        fun createBG(engine: Engine) = engine.createEntity().also { entity ->
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
        fun createHearts(engine: Engine, posx: Float, posy: Float) = engine.createEntity().also { entity ->
            entity.add(engine.createComponent(PositionComponent::class.java).apply {
                z = 1f
                x = posx
                y = posy
            })
            entity.add(engine.createComponent(BodyComponent::class.java).apply {
                rectangle.setWidth(50f)
                rectangle.setHeight(50f)
                rectangle.setPosition(posx, posy)
            })
            entity.add(engine.createComponent(TextureComponent::class.java).apply {
                texture = Texture("heart.png")
            })
        }
    }
}