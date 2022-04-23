package group16.project.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Texture

class HeartDisplayComponent : Component, HealthListener {
    private lateinit var health: HealthComponent
    lateinit var texture: Texture
    lateinit var textureEmpty: Texture
    var hearts = -1

    private fun updateHearts(){
        hearts = health.hp
    }

    fun listenTo(health : HealthComponent) {
        health.addListener(this)
        this.health = health
        updateHearts()
    }

    override fun healthChanged() {
        updateHearts()
    }

}