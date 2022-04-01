package group16.project.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import group16.project.game.Configuration
import group16.project.game.controllers.InputHandler
import group16.project.game.ecs.component.*

class ShootingSystem ():  IteratingSystem(
    Family.all(
        BodyComponent::class.java,
        PositionComponent::class.java
    ).one(TrajectoryComponent::class.java).get()
) {


    override fun processEntity(entity: Entity?, deltaTime: Float) {
        // Ufo
        if (ComponentMapper.trajectory[entity] != null) {
            this.position(
                ComponentMapper.position[entity],
                ComponentMapper.body[entity],
                ComponentMapper.trajectory[entity])
        }
    }

    private fun position(
        positionComponent: PositionComponent,
        bodyComponent: BodyComponent,
        trajectoryComponent: TrajectoryComponent
    ) {
        val padding = (Configuration.gameHeight - 4*100) / 2
        val buttonHeight = 100
        if (trajectoryComponent.fromPlayer) {
            bodyComponent.rectangle.y = InputHandler.playerTrajectoryPosition * buttonHeight + padding
            positionComponent.y = InputHandler.playerTrajectoryPosition * buttonHeight + padding
        } else {
            bodyComponent.rectangle.y = InputHandler.enemyTrajectoryPosition * buttonHeight + padding
            positionComponent.y = InputHandler.enemyTrajectoryPosition * buttonHeight + padding
        }
    }
}
