package group16.project.game.models

enum class GameState(val state: Int) {

    NO_STATE(0),
    WAITING_FOR_PLAYER_TO_JOIN(1),
    PLAYERS_CHOOSING(2),
    SHOTS_FIRING(3),
    POINTS_UPDATING(4),
    GAME_ENDED(5)

}