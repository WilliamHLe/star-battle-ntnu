package group16.project.game.ecs.component

import com.badlogic.ashley.core.Component

class TrajectoryComponent : Component {
    var fireShot = false;
    var isPlayer = false;

    var startPosX = 0f
    var startPosY = 0f

    var shootingPosX = 0f
    var shootingPosY = 0f
}