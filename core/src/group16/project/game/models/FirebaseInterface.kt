package group16.project.game.models

import group16.project.game.views.JoinLobbyScreen
import group16.project.game.views.HighScoreScreen

/**
 * Interface used for the platform based classes that are used to interact with our firebase project
 */
interface FirebaseInterface {

    fun signInAnonymously()

    /**
     * Method used to set given value to given database
     * @param target the reference we want to set value to in the database
     * @param value the value we want to set to
     */
    fun setValueInDb(target: String, value:String)

    fun createLobby(lobbyName: String)

    fun joinLobby(lobbyCode: String, screen: JoinLobbyScreen)

    fun getHighScoreListner(screen: HighScoreScreen)

    fun updateCurrentGameState(lobbyCode: String, state: GameState)

    fun setPlayersChoice(lobbyCode: String, player: String, position: Int, targetPostion: Int)


    //TODO: more functions should be added based on what we need for our project.
}