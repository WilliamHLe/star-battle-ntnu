package group16.project.game.models

import group16.project.game.views.JoinLobbyScreen
import group16.project.game.views.HighScoreScreen

class CoreFirebaseConnection: FirebaseInterface {

    override fun signInAnonymously() {
        println("core")
    }

    override fun setValueInDb(target: String, value: String) {
        //TODO("Not yet implemented")
        println("Core conenction class")
    }

    override fun createLobby(lobbyName: String) : String{
        //TODO("Not yet implemented")
        return ""
    }

    override fun joinLobby(lobbyCode: String, screen: JoinLobbyScreen) : String{
        //TODO("Not yet implemented")
        return ""

    }

    override fun getHighScoreListner(screen: HighScoreScreen) {

    }

    override fun updateCurrentGameState(lobbyCode: String, state: GameState) {
        //TODO("Not yet implemented")
    }

    override fun setPlayersChoice(lobbyCode: String, position: Int, targetPosition: Int) {
        TODO("Not yet implemented")
    }

    override fun playerIsReadyToFire(lobbyCode: String) : Boolean{
        TODO("Not yet implemented")
    }





}