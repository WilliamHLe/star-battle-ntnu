package group16.project.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import group16.project.game.controllers.InputHandler
import group16.project.game.ecs.component.*
import group16.project.game.ecs.lerp
import group16.project.game.ecs.utils.ComponentMapper
import kotlin.math.abs

class ShootingSystem ():  IteratingSystem(
    Family.all(
        BodyComponent::class.java,
        PositionComponent::class.java,
        TransformComponent::class.java
    ).one(TrajectoryComponent::class.java).get()
) {


    override fun processEntity(entity: Entity?, deltaTime: Float) {
        if (ComponentMapper.trajectory[entity] != null) {
            this.position(
                ComponentMapper.position[entity],
                ComponentMapper.body[entity],
                ComponentMapper.trajectory[entity],
                ComponentMapper.transform[entity], entity)
        }
    }
    private fun position(
        positionComponent: PositionComponent,
        bodyComponent: BodyComponent,
        trajectoryComponent: TrajectoryComponent,
        transformComponent: TransformComponent,
        entity: Entity?
    ) {
        var targetX = trajectoryComponent.shootingPosX
        var targetY = trajectoryComponent.shootingPosY
        bodyComponent.rectangle.x = lerp(positionComponent.x, targetX, 0.05f)
        positionComponent.x = lerp(positionComponent.x, targetX, 0.05f)
        bodyComponent.rectangle.y = lerp(positionComponent.y, targetY, 0.05f)
        positionComponent.y = lerp(positionComponent.y, targetY, 0.05f)

        if (abs(positionComponent.x - targetX) < 10 && abs(positionComponent.y - targetY) < 10) {
            engine.removeEntity(entity)
        }
    }
}
