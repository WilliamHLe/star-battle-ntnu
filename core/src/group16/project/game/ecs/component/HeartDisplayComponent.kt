package group16.project.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import group16.project.game.models.Game
import group16.project.game.models.GameFactory

class HeartDisplayComponent : Component, HealthListener {
    private lateinit var health: HealthComponent
    private val positionComponentMapper = ComponentMapper.getFor(PositionComponent::class.java)
    private var displays: ArrayList<Entity> = ArrayList()

    public fun listenTo(health : HealthComponent) {
        health.addListener(this)
        this.health = health
        updateDisplay()
    }

    override fun healthChanged() {
        updateDisplay()
    }
    private fun updateDisplay() {
        for (display : Entity in displays) {
            // remove entity
        }

        for (i in 1..health.get()) {
            // create entity
                // TODO
            //var baseX = positionComponentMapper.get()
            var distance = 50f*i
            GameFactory.createHearts(distance,0f)
        }
    }
}