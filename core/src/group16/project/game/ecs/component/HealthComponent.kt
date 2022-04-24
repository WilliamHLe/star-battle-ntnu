package group16.project.game.ecs.component

import com.badlogic.ashley.core.Component

class HealthComponent : Component {
    private var listeners = ArrayList<HealthListener>()

    var hp = 2

    fun addListener(listener : HealthListener) {
        listeners.add(listener)
    }

    fun set(hp: Int) {
        this.hp = hp
        fireHealthChanged()
    }

    private fun fireHealthChanged() {
        for (listener : HealthListener in listeners) {
            listener.healthChanged()
        }
    }
}