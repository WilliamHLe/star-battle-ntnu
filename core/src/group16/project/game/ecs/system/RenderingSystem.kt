package group16.project.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import group16.project.game.ecs.compareEntityByPosition
import group16.project.game.ecs.component.BodyComponent
import group16.project.game.ecs.component.TextureComponent
import group16.project.game.ecs.component.TransformComponent
import group16.project.game.ecs.notNull
import group16.project.game.ecs.utils.ComponentMapper

class RenderingSystem(
        private val batch: Batch,
        private val camera: OrthographicCamera
) : SortedIteratingSystem(
        Family.all(BodyComponent::class.java, TextureComponent::class.java).get(),
        Comparator(::compareEntityByPosition)
) {
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
        if (ComponentMapper.transform[entity] != null)
            notNull(ComponentMapper.body[entity], ComponentMapper.texture[entity], ComponentMapper.transform[entity], ::render)
        else
            notNull(ComponentMapper.body[entity], ComponentMapper.texture[entity], ::render)
    }

    fun dispose() {
        batch.dispose()
        entities.forEach {
            ComponentMapper.texture[it]?.texture?.dispose()
        }
    }

    private fun render(bodyComponent: BodyComponent, textureComponent: TextureComponent) {
        val rectShape = bodyComponent.rectangle
        batch.draw(textureComponent.texture, rectShape.x, rectShape.y, rectShape.width, rectShape.height)
    }
    private fun render(bodyComponent: BodyComponent, textureComponent: TextureComponent, transformComponent: TransformComponent) {
        val rectShape = bodyComponent.rectangle
        val sprite = Sprite(textureComponent.texture);

        sprite.setPosition(rectShape.x, rectShape.y)
        sprite.setOrigin(rectShape.width/2, rectShape.height/2)
        sprite.setSize(rectShape.width, rectShape.height)
        sprite.rotation = transformComponent.rotation
        sprite.setAlpha(transformComponent.opacity)
        sprite.flip(transformComponent.flipped, false);
        sprite.draw(batch)
    }
}