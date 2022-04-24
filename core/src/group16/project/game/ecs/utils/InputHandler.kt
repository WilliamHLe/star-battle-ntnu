package group16.project.game.ecs.utils

import com.badlogic.gdx.math.MathUtils
import group16.project.game.models.GameInfo

object InputHandler {
    var playerPosition = 0
    var playerShieldPosition = -1
    var playerTrajectoryPosition = 0
    var playerSkin = 0

    var enemyPosition = MathUtils.random(0, GameInfo.slots-1)
    var enemyTrajectoryPosition = 0
    var enemyShieldPosition = -1
    var enemySkin = 0
}
