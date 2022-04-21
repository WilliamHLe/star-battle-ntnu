package group16.project.game.ecs.utils


import com.badlogic.gdx.graphics.Texture
import group16.project.game.Configuration
import group16.project.game.ecs.Engine
import group16.project.game.ecs.component.*

class EntityFactory {

    companion object {
        fun createBG(engine: Engine, posx: Float, posy: Float) =
            engine.createEntity().also { entity ->
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
                    texture = Texture("background_dark.png")
                })
            }

        fun createHearts(engine: Engine, posx: Float, posy: Float, listensTo: HealthComponent) =
            engine.createEntity().also { entity ->
                entity.add(engine.createComponent(PositionComponent::class.java).apply {
                    z = 1f
                    x = posx
                    y = posy
                })
                entity.add(engine.createComponent(BodyComponent::class.java).apply {
                    rectangle.setWidth(70f)
                    rectangle.setHeight(60f)
                    rectangle.setPosition(posx, posy)
                })
                entity.add(engine.createComponent(HeartDisplayComponent::class.java).apply {
                    listenTo(listensTo)
                })
            }


        fun createUfo(engine: Engine, posx: Float, posy: Float, isPlayer: Boolean) =
            engine.createEntity().also { entity ->
                entity.add(engine.createComponent(PositionComponent::class.java).apply {
                    z = 1f
                    x = posx
                    y = posy
                })
                entity.add(engine.createComponent(BodyComponent::class.java).apply {
                    rectangle.setWidth(190f)
                    rectangle.setHeight(110f)
                    rectangle.setPosition(posx, posy)
                })
                entity.add(engine.createComponent(TextureComponent::class.java).apply {
                    texture = Texture("ufo3.png")
                })
                entity.add(engine.createComponent(UfoComponent::class.java).apply {
                    player = isPlayer
                })
                entity.add(engine.createComponent(HealthComponent::class.java).apply {
                    hp = 3
                })
                entity.add(engine.createComponent(TransformComponent::class.java).apply {
                    rotation = 0f
                    flipped = !isPlayer
                    opacity = 1f
                })
            }

        fun createTarget(engine: Engine, posx: Float, posy: Float, isPlayer: Boolean) =
            engine.createEntity().also { entity ->
                entity.add(engine.createComponent(PositionComponent::class.java).apply {
                    z = 1f
                    x = posx
                    y = posy
                })
                entity.add(engine.createComponent(BodyComponent::class.java).apply {
                    rectangle.setWidth(160f)
                    rectangle.setHeight(160f)
                    rectangle.setPosition(posx, posy)
                })
                entity.add(engine.createComponent(TextureComponent::class.java).apply {
                    texture = Texture("target.png")
                })
                entity.add(engine.createComponent(TargetComponent::class.java).apply {
                    player = isPlayer
                })
                entity.add(engine.createComponent(TransformComponent::class.java).apply {
                    rotation = 0f
                    flipped = false
                    opacity = if (isPlayer) 1f else 0f
                })
            }
        fun createShield(engine: Engine, posx: Float, posy: Float, isPlayer: Boolean) =
            engine.createEntity().also { entity ->
                entity.add(engine.createComponent(PositionComponent::class.java).apply {
                    z = 1f
                    x = posx
                    y = posy
                })
                entity.add(engine.createComponent(BodyComponent::class.java).apply {
                    rectangle.setWidth(64f)
                    rectangle.setHeight(120f)
                    rectangle.setPosition(posx, posy)
                })
                entity.add(engine.createComponent(TextureComponent::class.java).apply {
                    texture = Texture("Shield.png")
                })
                entity.add(engine.createComponent(ShieldComponent::class.java).apply {
                    player = isPlayer
                })
                entity.add(engine.createComponent(TransformComponent::class.java).apply {
                    rotation = 0f
                    flipped = !isPlayer
                    opacity = 1f
                })
            }

        fun createTrajectory(
            engine: Engine,
            startPosX: Float,
            startPosY: Float,
            isPlayer: Boolean,
            shootPosX: Float,
            shootPosY: Float
        ) = engine.createEntity().also { entity ->
            entity.add(engine.createComponent(PositionComponent::class.java).apply {
                z = 1f
                x = startPosX
                y = startPosY
            })
            entity.add(engine.createComponent(BodyComponent::class.java).apply {
                rectangle.setWidth(100f)
                rectangle.setHeight(50f)
                rectangle.setPosition(startPosX, startPosY)
            })

            entity.add(engine.createComponent(TextureComponent::class.java).apply {
                texture = Texture("trajectory.png")
            })


            entity.add(engine.createComponent(TrajectoryComponent::class.java).apply {
                this.isPlayer = isPlayer
                this.shootingPosX = shootPosX
                this.shootingPosY = shootPosY
            })
            entity.add(engine.createComponent(TransformComponent::class.java).apply {
                rotation = 0f
                flipped = false
                opacity = 1f
            })

        }
    }
}
