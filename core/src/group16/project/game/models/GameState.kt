package group16.project.game.models

enum class GameState(val state: Int) {
    PLAYERS_CHOOSING(0),
    SHOTS_FIRING(1),
    POINTS_UPDATING(2),
    GAME_ENDED(3)

}