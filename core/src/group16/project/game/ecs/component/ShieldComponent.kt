package group16.project.game.ecs.component

import com.badlogic.ashley.core.Component


class ShieldComponent: Component  {

    var shield = 0
    var position = -1
    var destroyed = false

    fun setPos(position: Int) {
        println("Set SHIELD POSITION to "+position+" (was "+this.position+")")
        this.position = position
    }

}