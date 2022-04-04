package group16.project.game.ecs.component

import com.badlogic.ashley.core.Component

class HealthComponent : Component {
    private var listeners = ArrayList<HealthListener>()

    var hp = 2


    public fun addListener(listener : HealthListener) {
        listeners.add(listener);
    }

    public fun increase() {
        hp++;
        fireHealthChanged()
    }
    public fun decrease() {
        hp--;
        fireHealthChanged()
    }
    private fun fireHealthChanged() {
        for (listener : HealthListener in listeners) {
            listener.healthChanged()
        }
    }
    public fun get(): Int {
        return hp;
    }
}