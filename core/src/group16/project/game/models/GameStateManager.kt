package group16.project.game.models

import java.util.*

class GameStateManager(val lobbyCode: String, val player: String, var fbic: FirebaseInterface) {


    fun fireShots() {
        if (player == "host") {
            fbic.updateCurrentGameState(lobbyCode, GameState.ANIMATION)
        }
        fbic.checkIfYouGotHit(lobbyCode, player)
        fbic.playerChoosingPostion(lobbyCode)
    }


}