package group16.project.game.controllers

import com.badlogic.gdx.math.MathUtils


object InputHandler {
    var playerPosition = 0f
    var enemyPosition = MathUtils.random(0f, 3f)

    var playerTrajectoryPosition = 0f
    var enemyTrajectoryPosition = 0f

    var fireShots = false;
}
