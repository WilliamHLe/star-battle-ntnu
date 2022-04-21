package group16.project.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import group16.project.game.Configuration
import group16.project.game.controllers.InputHandler
import group16.project.game.ecs.component.*
import group16.project.game.ecs.lerp
import group16.project.game.ecs.utils.ComponentMapper

class PositioningSystem () : IteratingSystem(
        Family.all(
                BodyComponent::class.java,
                PositionComponent::class.java
        ).one(UfoComponent::class.java, TargetComponent::class.java, ShieldComponent::class.java).get()
) {


    override fun processEntity(entity: Entity?, deltaTime: Float) {
        // Ufo
        if (ComponentMapper.ufo[entity] != null) {
            this.position(ComponentMapper.position[entity],
                    ComponentMapper.body[entity],
                    ComponentMapper.ufo[entity])
        }else if (ComponentMapper.shield[entity] != null) {
            this.position(ComponentMapper.position[entity],
                ComponentMapper.body[entity],
                ComponentMapper.shield[entity])
        } else { // Target
            this.position(ComponentMapper.position[entity],
                    ComponentMapper.body[entity],
                    ComponentMapper.target[entity])
        }
    }

    private fun position(
            positionComponent: PositionComponent,
            bodyComponent: BodyComponent,
            ufoComponent: UfoComponent
    ) {
        val padding = (Configuration.gameHeight - 4*100) / 2
        val buttonHeight = 100

        var target = InputHandler.enemyPosition* buttonHeight + padding
        if (ufoComponent.player)
            target = InputHandler.playerPosition* buttonHeight + padding

        bodyComponent.rectangle.y = lerp(positionComponent.y, target, 0.1f)
        positionComponent.y = lerp(positionComponent.y, target, 0.1f)
    }
    private fun position(
        positionComponent: PositionComponent,
        bodyComponent: BodyComponent,
        shieldComponent: ShieldComponent
    ) {
        val padding = (Configuration.gameHeight - 4*100) / 2
        val buttonHeight = 100

        var shield = InputHandler.enemyShieldPosition
        if (shieldComponent.player)
            shield = InputHandler.playerShieldPosition
        val shieldPosition = shield * buttonHeight + padding

        shieldComponent.position = shield
        bodyComponent.rectangle.y = lerp(positionComponent.y, shieldPosition, 0.1f)
        positionComponent.y = lerp(positionComponent.y, shieldPosition, 0.1f)
    }
    private fun position(
            positionComponent: PositionComponent,
            bodyComponent: BodyComponent,
            targetComponent: TargetComponent
    ) {
        val padding = (Configuration.gameHeight - 4*115) / 2
        val buttonHeight = 100

        var target = InputHandler.enemyTrajectoryPosition * buttonHeight + padding
        if (targetComponent.player)
            target = InputHandler.playerTrajectoryPosition * buttonHeight + padding
        bodyComponent.rectangle.y = target
        positionComponent.y = target
    }
}
