package group16.project.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import group16.project.game.ecs.component.*
import group16.project.game.ecs.notNull
import com.badlogic.gdx.graphics.g2d.Batch
import group16.project.game.Configuration
import group16.project.game.ecs.utils.ComponentMapper
import javax.xml.crypto.dsig.Transform

class ShieldSystem: IteratingSystem(
    Family.all(
        PositionComponent::class.java,
            BodyComponent::class.java,
        ShieldComponent::class.java,
        TransformComponent::class.java,
    ).get()) {


    override fun processEntity(entity: Entity?, deltaTime: Float) {
        notNull(
                ComponentMapper.position[entity],
                ComponentMapper.shield[entity],
                ComponentMapper.body[entity],
                ComponentMapper.transform[entity],
                ::render
        )
    }

    private fun render(positionComponent: PositionComponent, shieldComponent: ShieldComponent, bodyComponent: BodyComponent, transformComponent: TransformComponent) {


        //println("yeah "+positionComponent.x)
        val padding = (Configuration.gameHeight - 4*100) / 2
        val buttonHeight = 100

        //println(positionComponent.y)
        if (shieldComponent.destroyed || shieldComponent.position < 0) {
            transformComponent.opacity = 0f
        } else {
            transformComponent.opacity = 1f
        }

        //println(positionComponent.y.toString() + " "+shieldComponent.position)
    }
}