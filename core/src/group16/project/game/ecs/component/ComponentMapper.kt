package group16.project.game.ecs.component

import com.badlogic.ashley.core.ComponentMapper

object ComponentMapper {
    val body = ComponentMapper.getFor(BodyComponent::class.java)
    val position = ComponentMapper.getFor(PositionComponent::class.java)
    val velocity = ComponentMapper.getFor(VelocityComponent::class.java)
    val ufo = ComponentMapper.getFor(UfoComponent::class.java)
    val target = ComponentMapper.getFor(TargetComponent::class.java)
}
