package group16.project.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Texture
import group16.project.game.ecs.utils.ComponentMapper


class HeartDisplayComponent : Component, HealthListener {
    private lateinit var health: HealthComponent
    private val positionComponentMapper = ComponentMapper.position
    private var displays: ArrayList<Entity> = ArrayList()
    val texture: Texture = Texture("heart_full.png")
    var hearts = -1
    val textureEmpty: Texture = Texture("heart.png")

    fun updateHearts(){
        hearts = health.get()
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