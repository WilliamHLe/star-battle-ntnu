package group16.project.game.controllers

import com.badlogic.gdx.math.MathUtils


object InputHandler {
    var playerPosition = 0
    var enemyPosition = MathUtils.random(0, 3)

    var playerTrajectoryPosition = 0
    var enemyTrajectoryPosition = 0

    var fireShots = false;
}
