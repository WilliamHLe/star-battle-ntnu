package group16.project.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Texture


class HeartDisplayComponent : Component, HealthListener {
    private lateinit var health: HealthComponent
    private val positionComponentMapper = ComponentMapper.getFor(PositionComponent::class.java)
    private var displays: ArrayList<Entity> = ArrayList()
    val texture: Texture = Texture("heart.png")
    var hearts = -1

    public fun updateHearts(){
        hearts = health.get()
    }

    public fun listenTo(health : HealthComponent) {
        health.addListener(this)
        this.health = health
        updateHearts()
    }

    override fun healthChanged() {
        updateHearts()
    }

}