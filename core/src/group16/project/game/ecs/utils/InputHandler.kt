package group16.project.game.ecs.utils

import com.badlogic.gdx.math.MathUtils

object InputHandler {
    var playerPosition = 0
    var playerShieldPosition = -1
    var enemyPosition = MathUtils.random(0, 3)
    var playerTrajectoryPosition = 0
    var enemyTrajectoryPosition = 0
    var enemyShieldPosition = -1
}
