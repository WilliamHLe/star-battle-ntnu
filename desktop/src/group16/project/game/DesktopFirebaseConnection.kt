package group16.project.game

import group16.project.game.models.FirebaseInterface
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

    override fun createLobby(lobbyName: String) {
        //TODO("Not yet implemented")
    }

    override fun joinLobby(lobbyCode: String, screen: JoinLobbyScreen) {
        //TODO("Not yet implemented")
    }
}