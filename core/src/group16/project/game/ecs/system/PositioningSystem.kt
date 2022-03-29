package group16.project.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import group16.project.game.controllers.InputHandler
import group16.project.game.ecs.component.*

class PositioningSystem () : IteratingSystem(
        Family.all(
                BodyComponent::class.java,
                PositionComponent::class.java,
                UfoComponent::class.java
        ).get()
) {


    override fun processEntity(entity: Entity?, deltaTime: Float) {
        this.position(ComponentMapper.position[entity],
                ComponentMapper.body[entity],
                ComponentMapper.ufo[entity])
    }

    private fun position(
            positionComponent: PositionComponent,
            bodyComponent: BodyComponent,
            ufoComponent: UfoComponent
    ) {
        if (ufoComponent.player) {
            bodyComponent.rectangle.y = InputHandler.playerPosition * 100 + 120
            positionComponent.y = InputHandler.playerPosition * 100 + 120
            if (ufoComponent.isTarget) {
                bodyComponent.rectangle.y = InputHandler.playerTrajectoryPosition * 100 + 120
                positionComponent.y = InputHandler.playerTrajectoryPosition * 100 + 120
            }
        } else {
            bodyComponent.rectangle.y = InputHandler.enemyPosition * 100 + 120
            positionComponent.y = InputHandler.enemyPosition * 100 + 120
            if (ufoComponent.isTarget) {
                bodyComponent.rectangle.y = InputHandler.enemyTrajectoryPosition * 100 + 120
                positionComponent.y = InputHandler.enemyTrajectoryPosition * 100 + 120
            }
        }
    }
}
