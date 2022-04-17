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

    /**
     * Method used to set given value to given database
     * @param target the reference we want to set value to in the database
     * @param value the value we want to set to
     */
    fun setValueInDb(target: String, value:String)

    fun createLobby(lobbyName: String, gameController: StarBattle)

    fun joinLobby(lobbyCode: String, screen: JoinLobbyScreen)

    fun heartListener(lobbyCode: String, player: String, screen: GameScreen)

    fun getHighScoreListener(screen: HighScoreScreen)

    fun updateCurrentGameState(lobbyCode: String, state: GameState)

    fun setPlayersChoice(lobbyCode: String, position: Int, targetPosition: Int)

    fun playerIsReadyToFire(lobbyCode: String, gameScreen: GameScreen)
    fun getCurrentState(lobbyCode: String, game: Game)
    fun updateScore(points: Int)

     fun checkIfYouGotHit(lobbyCode: String, player: String)
     fun player2HitPlayer1(lobbyCode: String)
     fun getAmountOfLives(lobbyCode: String, player: String) : Int
    fun reduceHeartsAmount(lobbyCode: String, player: String)
    fun playerChoosingPostion(lobbyCode: String)




    //TODO: more functions should be added based on what we need for our project.
}