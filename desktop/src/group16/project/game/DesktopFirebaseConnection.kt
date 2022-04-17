package group16.project.game

import group16.project.game.models.FirebaseInterface
import group16.project.game.models.GameState
import group16.project.game.views.HighScoreScreen
import group16.project.game.views.JoinLobbyScreen
import pl.mk5.gdx.fireapp.GdxFIRApp
import pl.mk5.gdx.fireapp.GdxFIRAuth
import pl.mk5.gdx.fireapp.GdxFIRDatabase
import pl.mk5.gdx.fireapp.GdxFIRLogger
import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser

//import
/**
 * TODO
 * We need to find out if we need the desktop version.
 * -If so we need to create at way to interact with the firebase project.
 * -If not, we need to do some changes in core, remove this class, etc
 */
class DesktopFirebaseConnection : FirebaseInterface {
    override fun signInAnonymously() {
        //TODO("Not yet implemented")
    }

    override fun setValueInDb(target: String, value: String) {
        //TODO("Not yet implemented")
        println("Desktop connection class")
    }

    override fun createLobby(lobbyName: String) : String{
        //TODO("Not yet implemented")
        return ""
    }

    override fun joinLobby(lobbyCode: String, screen: JoinLobbyScreen) : String{
        //TODO("Not yet implemented")
        return ""
    }

    override fun getHighScoreListener(screen: HighScoreScreen) {
        //TODO("Not yet implemented")
    }

    override fun updateCurrentGameState(lobbyCode: String, state: GameState) {
        //TODO("Not yet implemented")
    }

    override fun setPlayersChoice(lobbyCode: String, position: Int, targetPosition: Int) {
        //TODO("Not yet implemented")
    }

    override fun playerIsReadyToFire(lobbyCode: String) : Boolean{
        //TODO("Not yet implemented")
        return false
    }

    override fun getCurrentState(lobbyCode: String): GameState {
        //TODO("Not yet implemented")
        return GameState.NO_STATE
    }

    override fun player1HitPlayer2(lobbyCode: String): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun player2HitPlayer1(lobbyCode: String): Boolean {
        //TODO("Not yet implemented")
        return false

    }

    override fun getAmountOfLives(lobbyCode: String, player: String): Int {
        //TODO("Not yet implemented")
        return 2
    }

    override fun reduceHeartsAmount(lobbyCode: String, player: String) {
        //TODO("Not yet implemented")
    }

    override fun playerChoosingPostion(lobbyCode: String) {
        //TODO("Not yet implemented")
    }


}