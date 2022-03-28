package group16.project.game.models


import com.badlogic.gdx.graphics.Texture
import group16.project.game.ecs.Engine
import group16.project.game.ecs.component.BodyComponent
import group16.project.game.ecs.component.PositionComponent
import group16.project.game.ecs.component.TextureComponent

class ShipFactory() {

    companion object {
        fun createShip(engine: Engine, posx: Float, posy: Float) = engine.createEntity().also { entity ->
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
        }
    }
}