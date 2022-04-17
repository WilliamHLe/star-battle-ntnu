package group16.project.game.models

import group16.project.game.StarBattle
import group16.project.game.views.GameScreen
import group16.project.game.views.JoinLobbyScreen
import group16.project.game.views.HighScoreScreen

class CoreFirebaseConnection: FirebaseInterface {

    override fun signInAnonymously() {
        //TODO("Not yet implemented")
    }

    override fun setValueInDb(target: String, value: String) {
        //TODO("Not yet implemented")
        println("Desktop connection class")
    }

    override fun createLobby(lobbyName: String, gameController: StarBattle){
        //TODO("Not yet implemented")
    }

    override fun joinLobby(lobbyCode: String, screen: JoinLobbyScreen){
        //TODO("Not yet implemented")
    }

    override fun heartListener(lobbyCode: String, player: String, screen: GameScreen) {

    }

    override fun updateScore(points: Int) {

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

    override fun playerIsReadyToFire(lobbyCode: String, gameScreen: GameScreen) {
        //TODO("Not yet implemented")
        //return false
    }

    override fun getCurrentState(lobbyCode: String, game: Game) {
        //TODO("Not yet implemented")
    }

    override fun checkIfYouGotHit(lobbyCode: String, player: String) {
        //TODO("Not yet implemented")
    }

    override fun player2HitPlayer1(lobbyCode: String) {
        //TODO("Not yet implemented")

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