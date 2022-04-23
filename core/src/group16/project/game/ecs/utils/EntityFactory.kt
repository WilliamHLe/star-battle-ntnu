package group16.project.game.ecs.utils

import com.badlogic.ashley.core.Entity
import group16.project.game.Configuration
import group16.project.game.ecs.Engine
import group16.project.game.ecs.component.*
import group16.project.game.views.TextureHandler

class EntityFactory {

    companion object {
        fun createBG(engine: Engine, x: Float, y: Float): Entity =
            engine.createEntity().also { entity ->
                entity.add(engine.createComponent(PositionComponent::class.java).apply {
                    z = 0f
                    this.x = x
                    this.y = y
                })
                entity.add(engine.createComponent(BodyComponent::class.java).apply {
                    rectangle.setWidth(Configuration.gameWidth)
                    rectangle.setHeight(Configuration.gameHeight)
                    rectangle.setPosition(x, y)
                })
                entity.add(engine.createComponent(TextureComponent::class.java).apply {
                    texture = TextureHandler.textures["GAME_BACKGROUND"]
                })
            }

        fun createHearts(engine: Engine, x: Float, y: Float, listensTo: HealthComponent): Entity =
            engine.createEntity().also { entity ->
                entity.add(engine.createComponent(PositionComponent::class.java).apply {
                    z = 1f
                    this.x = x
                    this.y = y
                })
                entity.add(engine.createComponent(BodyComponent::class.java).apply {
                    rectangle.setWidth(70f)
                    rectangle.setHeight(60f)
                    rectangle.setPosition(x, y)
                })
                entity.add(engine.createComponent(HeartDisplayComponent::class.java).apply {
                    listenTo(listensTo)
                    texture = TextureHandler.textures["HEART_FULL"]
                    textureEmpty = TextureHandler.textures["HEART_EMPTY"]
                })
            }


        fun createUfo(engine: Engine, x: Float, y: Float, hp: Int, isPlayer: Boolean): Entity =
            engine.createEntity().also { entity ->
                entity.add(engine.createComponent(PositionComponent::class.java).apply {
                    z = 1f
                    this.x = x
                    this.y = y
                })
                entity.add(engine.createComponent(BodyComponent::class.java).apply {
                    rectangle.setWidth(190f)
                    rectangle.setHeight(110f)
                    rectangle.setPosition(x, y)
                })
                entity.add(engine.createComponent(TextureComponent::class.java).apply {
                    texture = if (isPlayer) Configuration.skins[InputHandler.playerSkin].texture else Configuration.skins[InputHandler.enemySkin].texture
                })
                entity.add(engine.createComponent(PlayerComponent::class.java).apply {
                    player = isPlayer
                })
                entity.add(engine.createComponent(TypeComponent::class.java).apply {
                    type = "ufo"
                })
                entity.add(engine.createComponent(HealthComponent::class.java).apply {
                    this.hp = hp
                })
                entity.add(engine.createComponent(TransformComponent::class.java).apply {
                    rotation = 0f
                    flipped = !isPlayer
                    opacity = 1f
                })
            }

        fun createTarget(engine: Engine, x: Float, y: Float, isPlayer: Boolean): Entity =
            engine.createEntity().also { entity ->
                entity.add(engine.createComponent(PositionComponent::class.java).apply {
                    z = 1f
                    this.x = x
                    this.y = y
                })
                entity.add(engine.createComponent(BodyComponent::class.java).apply {
                    rectangle.setWidth(160f)
                    rectangle.setHeight(160f)
                    rectangle.setPosition(x, y)
                })
                entity.add(engine.createComponent(TextureComponent::class.java).apply {
                    texture = TextureHandler.textures["TARGET"]
                })
                entity.add(engine.createComponent(PlayerComponent::class.java).apply {
                    player = isPlayer
                })
                entity.add(engine.createComponent(TypeComponent::class.java).apply {
                    type = "target"
                })
                entity.add(engine.createComponent(TransformComponent::class.java).apply {
                    rotation = 0f
                    flipped = false
                    opacity = if (isPlayer) 1f else 0f
                })
            }
        fun createShield(engine: Engine, x: Float, y: Float, isPlayer: Boolean): Entity =
            engine.createEntity().also { entity ->
                entity.add(engine.createComponent(PositionComponent::class.java).apply {
                    z = 1f
                    this.x = x
                    this.y = y
                })
                entity.add(engine.createComponent(BodyComponent::class.java).apply {
                    rectangle.setWidth(64f)
                    rectangle.setHeight(120f)
                    rectangle.setPosition(x, y)
                })
                entity.add(engine.createComponent(TextureComponent::class.java).apply {
                    texture = TextureHandler.textures["SHIELD"]
                })
                entity.add(engine.createComponent(PlayerComponent::class.java).apply {
                    player = isPlayer
                })
                entity.add(engine.createComponent(ShieldComponent::class.java))
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
        ): Entity = engine.createEntity().also { entity ->
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
                texture = TextureHandler.textures["TRAJECTORY"]
            })
            entity.add(engine.createComponent(PlayerComponent::class.java).apply {
                player = isPlayer
            })
            entity.add(engine.createComponent(TrajectoryComponent::class.java).apply {
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
