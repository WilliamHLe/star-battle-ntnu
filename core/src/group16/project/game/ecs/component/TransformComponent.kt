package group16.project.game.ecs.component

import com.badlogic.ashley.core.Component

class TransformComponent : Component {
    var rotation = 0f
    var flipped = false
    var opacity = 0f
}