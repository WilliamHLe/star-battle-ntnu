package group16.project.game.ecs.system

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import group16.project.game.ecs.compareEntityByPosition
import group16.project.game.ecs.component.BodyComponent
import group16.project.game.ecs.component.TextureComponent
import group16.project.game.ecs.notNull

class RenderingSystem(
        private val batch: Batch,
        private val camera: OrthographicCamera
) : SortedIteratingSystem(
        Family.all(BodyComponent::class.java, TextureComponent::class.java).get(),
        Comparator(::compareEntityByPosition)
) {
    private val bodyComponentMapper = ComponentMapper.getFor(BodyComponent::class.java)
    private val textureComponentMapper = ComponentMapper.getFor(TextureComponent::class.java)

    override fun update(deltaTime: Float) {
        // Tell the camera to update its matrices
        camera.update()

        // Tell the SpriteBatch to render in the coordinate system specified by the camera
        batch.projectionMatrix = camera.combined
        batch.enableBlending()
        batch.begin()

        super.update(deltaTime)

        batch.end()
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        notNull(bodyComponentMapper[entity], textureComponentMapper[entity], ::render)
    }

    fun dispose() {
        batch.dispose()
        entities.forEach {
            textureComponentMapper[it]?.texture?.dispose()
        }
    }

    private fun render(bodyComponent: BodyComponent, textureComponent: TextureComponent) {
        val rectShape = bodyComponent.rectangle
        batch.draw(textureComponent.texture, rectShape.x, rectShape.y, rectShape.width, rectShape.height)
    }
}