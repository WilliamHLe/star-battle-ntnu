package group16.project.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.Body

class BodyComponent: Component {
    val rectangle = Rectangle()
}