package group16.project.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import group16.project.game.ecs.component.*
import group16.project.game.ecs.notNull
import com.badlogic.gdx.graphics.g2d.Batch
import group16.project.game.Configuration
import group16.project.game.ecs.utils.ComponentMapper
import group16.project.game.ecs.utils.ComponentMapper.position

class ShieldSystem(  //
    private val batch: Batch
): IteratingSystem(
    Family.all(
        BodyComponent::class.java,
        PositionComponent::class.java,
        ShieldComponent::class.java
    ).get()) {

    override fun update(deltaTime: Float) {
        batch.begin()
        super.update(deltaTime)
        batch.end()
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        notNull(
                ComponentMapper.position[entity],
                ComponentMapper.shield[entity],
                ::render
        )
    }

    private fun render(positionComponent: PositionComponent, shieldComponent: ShieldComponent) {

        val padding = (Configuration.gameHeight - 4*100) / 2
        val buttonHeight = 100

        positionComponent.y = shieldComponent.position*buttonHeight + padding
    }
}