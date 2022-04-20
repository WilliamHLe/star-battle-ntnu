package group16.project.game.models

import group16.project.game.StarBattle
import group16.project.game.views.GameScreen
import group16.project.game.views.JoinLobbyScreen
import group16.project.game.views.HighScoreScreen

/**
 * Interface used for the platform based classes that are used to interact with our firebase project
 */
interface FirebaseInterface {

    fun signInAnonymously()
    fun createLobby(lobbyName: String, gameController: StarBattle)
    fun joinLobby(lobbyCode: String, screen: JoinLobbyScreen)

    fun heartListener(player: String, screen: GameScreen)

    fun getHighScoreListener(screen: HighScoreScreen)

    fun updateCurrentGameState(state: GameState)

    fun setPlayersChoice(position: Int, targetPostion: Int, gameScreen: GameScreen)

    fun checkIfOpponentReady(screen: GameScreen)
    fun fire(gameScreen: GameScreen)
    fun getCurrentState(game: Game)
    fun updateScore(points: Int)

    fun updatePlayerHealth()
    fun reduceHeartsAmount()
    fun resetReady()

    fun deleteLobby()


    //TODO: more functions should be added based on what we need for our project.
}