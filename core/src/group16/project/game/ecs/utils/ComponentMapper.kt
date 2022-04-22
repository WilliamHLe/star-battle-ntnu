package group16.project.game.ecs.utils

import com.badlogic.ashley.core.ComponentMapper
import group16.project.game.ecs.component.*

object ComponentMapper {
    val body: ComponentMapper<BodyComponent> = ComponentMapper.getFor(BodyComponent::class.java)
    val position: ComponentMapper<PositionComponent> = ComponentMapper.getFor(PositionComponent::class.java)
    val player: ComponentMapper<PlayerComponent> = ComponentMapper.getFor(PlayerComponent::class.java)
    val texture: ComponentMapper<TextureComponent> = ComponentMapper.getFor(TextureComponent::class.java)
    val trajectory: ComponentMapper<TrajectoryComponent> = ComponentMapper.getFor(TrajectoryComponent::class.java)
    val transform: ComponentMapper<TransformComponent> = ComponentMapper.getFor(TransformComponent::class.java)
    val type: ComponentMapper<TypeComponent> = ComponentMapper.getFor(TypeComponent::class.java)
    val health: ComponentMapper<HealthComponent> = ComponentMapper.getFor((HealthComponent::class.java))
    val shield: ComponentMapper<ShieldComponent> = ComponentMapper.getFor((ShieldComponent::class.java))
}
