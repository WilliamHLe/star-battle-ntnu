package group16.project.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import group16.project.game.Configuration
import group16.project.game.controllers.InputHandler
import group16.project.game.ecs.component.*

class PositioningSystem () : IteratingSystem(
        Family.all(
                BodyComponent::class.java,
                PositionComponent::class.java
        ).one(UfoComponent::class.java, TargetComponent::class.java).get()
) {


    override fun processEntity(entity: Entity?, deltaTime: Float) {
        // Ufo
        if (ComponentMapper.ufo[entity] != null) {
            this.position(ComponentMapper.position[entity],
                    ComponentMapper.body[entity],
                    ComponentMapper.ufo[entity])
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
        if (ufoComponent.player) {
            bodyComponent.rectangle.y = InputHandler.playerPosition * buttonHeight + padding
            positionComponent.y = InputHandler.playerPosition * buttonHeight + padding
        } else {
            bodyComponent.rectangle.y = InputHandler.enemyPosition * buttonHeight + padding
            positionComponent.y = InputHandler.enemyPosition * buttonHeight + padding
        }
    }
    private fun position(
            positionComponent: PositionComponent,
            bodyComponent: BodyComponent,
            targetComponent: TargetComponent
    ) {
        val padding = (Configuration.gameHeight - 4*100) / 2
        val buttonHeight = 100
        if (targetComponent.player) {
            bodyComponent.rectangle.y = InputHandler.playerTrajectoryPosition * buttonHeight + padding
            positionComponent.y = InputHandler.playerTrajectoryPosition * buttonHeight + padding
        } else {
            bodyComponent.rectangle.y = InputHandler.enemyTrajectoryPosition * buttonHeight + padding
            positionComponent.y = InputHandler.enemyTrajectoryPosition * buttonHeight + padding
        }
    }
}
