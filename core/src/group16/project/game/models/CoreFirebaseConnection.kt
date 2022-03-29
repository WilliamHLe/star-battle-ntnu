package group16.project.game.models

import group16.project.game.views.JoinLobbyScreen

class CoreFirebaseConnection: FirebaseInterface {

    override fun signInAnonymously() {
        println("core")
    }

    override fun setValueInDb(target: String, value: String) {
        //TODO("Not yet implemented")
        println("Core conenction class")
    }

    override fun createLobby(lobbyName: String) {
        //TODO("Not yet implemented")
    }

    override fun joinLobby(lobbyCode: String, screen: JoinLobbyScreen) {
        //TODO("Not yet implemented")

    }
}