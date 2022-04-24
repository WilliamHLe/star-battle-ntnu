package group16.project.game.ecs.system

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import group16.project.game.ecs.component.*
import group16.project.game.ecs.notNull
import com.badlogic.gdx.graphics.g2d.Batch
import group16.project.game.models.GameInfo

class HeartSystem (
        private val batch: Batch
): IteratingSystem(
        Family.all(
                BodyComponent::class.java,
                PositionComponent::class.java,
                HeartDisplayComponent::class.java
        ).get()
) {

    private val bodyComponentMapper = ComponentMapper.getFor(BodyComponent::class.java)
    private val positionComponentMapper = ComponentMapper.getFor(PositionComponent::class.java)
    private val heartDisplayComponentMapper = ComponentMapper.getFor(HeartDisplayComponent::class.java)

    override fun update(deltaTime: Float) {
        batch.begin()
        super.update(deltaTime)
        batch.end()
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        notNull(
            positionComponentMapper[entity],
            bodyComponentMapper[entity],
            heartDisplayComponentMapper[entity],
            ::render
        )
    }

    private fun render(positionComponent: PositionComponent, bodyComponent: BodyComponent, heartDisplayComponent: HeartDisplayComponent) {
        val rectShape = bodyComponent.rectangle
        var isPlayer = false
        if (positionComponent.x < 100)
            isPlayer = true

        for (i in 0 until GameInfo.health) {
            if(heartDisplayComponent.hearts < i+1 && isPlayer || heartDisplayComponent.hearts < GameInfo.health-i && !isPlayer)
                batch.draw(heartDisplayComponent.textureEmpty, positionComponent.x + i*rectShape.width, positionComponent.y, rectShape.width, rectShape.height)
            else
                batch.draw(heartDisplayComponent.texture, positionComponent.x + i*rectShape.width, positionComponent.y, rectShape.width, rectShape.height)
        }
    }
}