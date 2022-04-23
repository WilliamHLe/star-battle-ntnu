package group16.project.game

import group16.project.game.models.FirebaseInterface
import group16.project.game.models.Game
import group16.project.game.models.GameState
import group16.project.game.views.GameScreen
import group16.project.game.views.LeaderboardScreen
import group16.project.game.views.JoinLobbyScreen

class DesktopFirebaseConnection : FirebaseInterface {
    override fun signInAnonymously() {
        //TODO("Not yet implemented")
    }

    override fun createLobby(lobbyName: String, gameController: StarBattle) {
        //TODO("Not yet implemented")
    }

    override fun joinLobby(lobbyCode: String, screen: JoinLobbyScreen) {
        //TODO("Not yet implemented")
    }

    override fun heartListener(player: String, screen: GameScreen) {
        //TODO("Not yet implemented")
    }

    override fun getHighScoreListener(screen: LeaderboardScreen) {
        //TODO("Not yet implemented")
    }

    override fun updateCurrentGameState(state: GameState) {
        //TODO("Not yet implemented")
    }

    override fun setPlayersChoice(position: Int, targetPosition: Int, shieldPosition: Int, gameScreen: GameScreen) {
        //TODO("Not yet implemented")
    }

    override fun checkIfOpponentReady(screen: GameScreen) {
        //TODO("Not yet implemented")
    }

    override fun fire(gameScreen: GameScreen) {
        //TODO("Not yet implemented")
    }

    override fun getCurrentState(game: Game) {
        //TODO("Not yet implemented")
    }

    override fun updateScore(points: Int) {
        //TODO("Not yet implemented")
    }

    override fun updatePlayerHealth() {
        //TODO("Not yet implemented")
    }

    override fun reduceHeartsAmount() {
        //TODO("Not yet implemented")
    }

    override fun resetReady() {
        //TODO("Not yet implemented")
    }

    override fun deleteLobby() {
        //TODO("Not yet implemented")
    }

    override fun removeShield() {
        //TODO("Not yet implemented")
    }

}