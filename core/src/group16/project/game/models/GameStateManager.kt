package group16.project.game.models

import java.util.*

class GameStateManager(val lobbyCode: String, val player: String, var fbic: FirebaseInterface) {


    var player1Hearts : Int
    var player2Hearts : Int


    init {
        //currentState = fbic.getCurrentState(lobbyCode)
        player1Hearts = 3
        player2Hearts = 3


    }


    fun lobbyCreated() {
        fbic.updateCurrentGameState(lobbyCode, GameState.WAITING_FOR_PLAYER_TO_JOIN)
    }

    fun player2Joined() {
        fbic.updateCurrentGameState(lobbyCode, GameState.PLAYERS_CHOOSING)
    }

    fun fireShots() {
        if (player == "host") {
            fbic.updateCurrentGameState(lobbyCode, GameState.SHOTS_FIRING)
        }
        updating_points()
        fbic.playerChoosingPostion(lobbyCode)
    }

    fun updating_points() {
        if (player == "host") {
            fbic.updateCurrentGameState(lobbyCode, GameState.POINTS_UPDATING)
        }
        /*if (check_if_player_got_hit("host")) {
            decrementLiveOfPlayer("host")
        }
        if (check_if_player_got_hit("player_2")) {
            decrementLiveOfPlayer("player_2")
        }*/
        //check_if_player_got_hit()
        fbic.checkIfYouGotHit(lobbyCode, player)

        //check_if_player_got_hit("player_2")

        /*var player1_won = check_if_player_lost("host")
        var player2_won = check_if_player_lost("player_2")

        if (player1_won) {
            player_won("player_2")
            fbic.updateCurrentGameState(lobbyCode, GameState.GAME_ENDED)
        }
        if(player2_won) {
            player_won("host")
            fbic.updateCurrentGameState(lobbyCode, GameState.GAME_ENDED)
        }

        if(!player1_won and !player2_won) {
            fbic.updateCurrentGameState(lobbyCode, GameState.PLAYERS_CHOOSING)
        }*/


    }

    /*private fun check_if_player_got_hit() : Boolean {
        var returnBoolean = false
        println(returnBoolean)
        //if(player == "host") returnBoolean = fbic.player2HitPlayer1(lobbyCode)
        fbic.checkIfYouGotHit(lobbyCode, player)
        println(returnBoolean)
        //if(player == "player_2") returnBoolean = bic.player1HitPlayer2(lobbyCode)
        //fbic.player1HitPlayer2(lobbyCode)
        println(returnBoolean)
        return returnBoolean
    }*/

    /*private fun decrementLiveOfPlayer(player: String) {
        fbic.reduceHeartsAmount(lobbyCode, player)

    }

    private fun check_if_player_lost(player: String) : Boolean {
        //var returnBoolean: false
        return fbic.getAmountOfLives(lobbyCode, player) == 0
    }

    private fun player_won(player: String) {
        //todo:
    }*/




}