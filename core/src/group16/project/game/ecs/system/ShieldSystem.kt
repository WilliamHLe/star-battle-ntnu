package group16.project.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import group16.project.game.ecs.component.*
import group16.project.game.ecs.notNull
import group16.project.game.ecs.utils.ComponentMapper

class ShieldSystem: IteratingSystem(
    Family.all(
            PositionComponent::class.java,
            BodyComponent::class.java,
            ShieldComponent::class.java,
            TransformComponent::class.java,
    ).get()) {


    override fun processEntity(entity: Entity?, deltaTime: Float) {
        notNull(
            ComponentMapper.shield[entity],
            ComponentMapper.transform[entity],
            ::render
        )
    }

    private fun render(shieldComponent: ShieldComponent, transformComponent: TransformComponent) {
        if (shieldComponent.destroyed || shieldComponent.position < 0) {
            transformComponent.opacity = 0f
        } else {
            transformComponent.opacity = 1f
        }
    }
}