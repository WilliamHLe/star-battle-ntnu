package group16.project.game.ecs

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Rectangle
import group16.project.game.ecs.system.MovementSystem
import group16.project.game.ecs.system.PositioningSystem
import group16.project.game.ecs.system.RenderingSystem

class Engine(
        batch: Batch,
        camera: OrthographicCamera,
        screenRect: Rectangle
) : PooledEngine() {

    private val renderingSystem = RenderingSystem(batch, camera)

    init {
        addSystem(MovementSystem(screenRect))
        addSystem(PositioningSystem())
        addSystem(renderingSystem)
    }

    fun dispose() {
        renderingSystem.dispose()
    }
}