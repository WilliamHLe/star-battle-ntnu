package group16.project.game.ecs.utils

import com.badlogic.ashley.core.ComponentMapper
import group16.project.game.ecs.component.*

object ComponentMapper {
    val body = ComponentMapper.getFor(BodyComponent::class.java)
    val position = ComponentMapper.getFor(PositionComponent::class.java)
    val velocity = ComponentMapper.getFor(VelocityComponent::class.java)
    val ufo = ComponentMapper.getFor(UfoComponent::class.java)
    val target = ComponentMapper.getFor(TargetComponent::class.java)
    val texture = ComponentMapper.getFor(TextureComponent::class.java)
    val trajectory = ComponentMapper.getFor(TrajectoryComponent::class.java)
    val transform = ComponentMapper.getFor(TransformComponent::class.java)
}
