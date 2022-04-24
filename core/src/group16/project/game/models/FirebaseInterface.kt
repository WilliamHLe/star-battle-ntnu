package group16.project.game.models

import group16.project.game.StarBattle
import group16.project.game.views.GameScreen
import group16.project.game.views.JoinLobbyScreen
import group16.project.game.views.LeaderboardScreen

/**
 * Interface used for the platform based classes that are used to interact with our firebase project
 */
interface FirebaseInterface {
    fun signInAnonymously()
    fun createLobby(lobbyName: String, gameController: StarBattle)
    fun joinLobby(lobbyCode: String, screen: JoinLobbyScreen)
    fun skinListener(player: String, screen: GameScreen)
    fun heartListener(player: String, screen: GameScreen)
    fun getHighScoreListener(screen: LeaderboardScreen)
    fun updateCurrentGameState(state: GameState)
    fun setPlayersChoice(position: Int, targetPosition: Int, shieldPosition: Int, gameScreen: GameScreen)
    fun checkIfOpponentReady(screen: GameScreen)
    fun fire(gameScreen: GameScreen)
    fun getCurrentState(game: Game)
    fun updateScore(points: Int)
    fun updatePlayerHealth()
    fun reduceHeartsAmount()
    fun resetReady()
    fun deleteLobby()
    fun removeShield()
}