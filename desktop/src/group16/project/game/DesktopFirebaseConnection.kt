package group16.project.game

import group16.project.game.models.FirebaseInterface
import group16.project.game.views.JoinLobbyScreen
import pl.mk5.gdx.fireapp.GdxFIRApp
import pl.mk5.gdx.fireapp.GdxFIRAuth
import pl.mk5.gdx.fireapp.GdxFIRDatabase
import pl.mk5.gdx.fireapp.GdxFIRLogger
import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser

class DesktopFirebaseConnection : FirebaseInterface {
    override fun signInAnonymously() {
        println("Desktop anonymous")
    }

    override fun setValueInDb(target: String, value: String) {
        println("Desktop connection class")
    }

    override fun createLobby(lobbyName: String) {
        println("Desktop create lobby")
    }

    override fun joinLobby(lobbyCode: String, screen: JoinLobbyScreen) {
        println("Desktop connection class")
    }
}