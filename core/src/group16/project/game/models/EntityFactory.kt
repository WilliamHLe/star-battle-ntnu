package group16.project.game.models


import com.badlogic.gdx.graphics.Texture
import group16.project.game.Configuration
import group16.project.game.ecs.Engine
import group16.project.game.ecs.component.BodyComponent
import group16.project.game.ecs.component.PositionComponent
import group16.project.game.ecs.component.TextureComponent
import group16.project.game.ecs.component.UfoComponent

class EntityFactory {

    companion object {
        fun createBG(engine: Engine, posx: Float, posy: Float) = engine.createEntity().also { entity ->
            entity.add(engine.createComponent(PositionComponent::class.java).apply {
                z = 0f
                x = posx
                y = posy
            })
            entity.add(engine.createComponent(BodyComponent::class.java).apply {
                rectangle.setWidth(Configuration.gameWidth)
                rectangle.setHeight(Configuration.gameHeight)
                rectangle.setPosition(posx, posy)
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

        fun createUfo(engine: Engine, posx: Float, posy: Float, isPlayer: Boolean) = engine.createEntity().also { entity ->
            entity.add(engine.createComponent(PositionComponent::class.java).apply {
                z = 1f
                x = posx
                y = posy
            })
            entity.add(engine.createComponent(BodyComponent::class.java).apply {
                rectangle.setWidth(150f)
                rectangle.setHeight(100f)
                rectangle.setPosition(posx, posy)
            })
            entity.add(engine.createComponent(TextureComponent::class.java).apply {
                texture = Texture("ufo.png")
            })
            entity.add(engine.createComponent(UfoComponent::class.java).apply {
                player = isPlayer
                isTarget = false
            })
        }

        fun createTarget(engine: Engine, posx: Float, posy: Float, isPlayer: Boolean) = engine.createEntity().also { entity ->
            entity.add(engine.createComponent(PositionComponent::class.java).apply {
                z = 1f
                x = posx
                y = posy
            })
            entity.add(engine.createComponent(BodyComponent::class.java).apply {
                rectangle.setWidth(100f)
                rectangle.setHeight(100f)
                rectangle.setPosition(posx, posy)
            })
            entity.add(engine.createComponent(TextureComponent::class.java).apply {
                texture = Texture("target.png")
            })
            entity.add(engine.createComponent(UfoComponent::class.java).apply {
                player = isPlayer
                isTarget = true
            })
        }

    }
}