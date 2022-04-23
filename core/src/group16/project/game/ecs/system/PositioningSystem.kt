package group16.project.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import group16.project.game.Configuration
import group16.project.game.ecs.utils.InputHandler
import group16.project.game.ecs.component.*
import group16.project.game.ecs.lerp
import group16.project.game.ecs.utils.ComponentMapper

class PositioningSystem : IteratingSystem(
        Family.all(
                BodyComponent::class.java,
                PositionComponent::class.java,
                PlayerComponent::class.java
        ).one(TypeComponent::class.java, ShieldComponent::class.java).get()
) {
    private val padding = (Configuration.gameHeight - 4*100) / 2
    private val buttonHeight = 100

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        if (ComponentMapper.type[entity] != null) { // Ufo & Target
            this.position(ComponentMapper.position[entity],
                    ComponentMapper.body[entity],
                    ComponentMapper.player[entity],
                    ComponentMapper.type[entity])
        } else if (ComponentMapper.shield[entity] != null) { // Shield
            this.position(ComponentMapper.position[entity],
                    ComponentMapper.body[entity],
                    ComponentMapper.shield[entity],
                    ComponentMapper.player[entity])
        }
    }

    private fun position(
            positionComponent: PositionComponent,
            bodyComponent: BodyComponent,
            playerComponent: PlayerComponent,
            typeComponent: TypeComponent,
    ) {
        if (typeComponent.type == "ufo") {
            var target = InputHandler.enemyPosition* buttonHeight + padding
            if (playerComponent.player)
                target = InputHandler.playerPosition* buttonHeight + padding

            bodyComponent.rectangle.y = lerp(positionComponent.y, target, 0.1f)
            positionComponent.y = lerp(positionComponent.y, target, 0.1f)
        } else if (typeComponent.type == "target") {
            val padding = (Configuration.gameHeight - 4*115) / 2
            var target = InputHandler.enemyTrajectoryPosition * buttonHeight + padding
            if (playerComponent.player)
                target = InputHandler.playerTrajectoryPosition * buttonHeight + padding
            bodyComponent.rectangle.y = target
            positionComponent.y = target
        }
    }

    private fun position(
            positionComponent: PositionComponent,
            bodyComponent: BodyComponent,
            shieldComponent: ShieldComponent,
            playerComponent: PlayerComponent
    ) {
        var shield = InputHandler.enemyShieldPosition
        if (playerComponent.player)
            shield = InputHandler.playerShieldPosition
        val shieldPosition = shield * buttonHeight + padding

        shieldComponent.position = shield
        bodyComponent.rectangle.y = lerp(positionComponent.y, shieldPosition, 0.1f)
        positionComponent.y = lerp(positionComponent.y, shieldPosition, 0.1f)
    }
}
