package group16.project.game.ecs.component

import com.badlogic.ashley.core.Component

class OpponentTrajectoryComponent : Component {
    var fromPlayer = false;

    var shootingPosX = 0f
    var shootingPosY = 0f
}