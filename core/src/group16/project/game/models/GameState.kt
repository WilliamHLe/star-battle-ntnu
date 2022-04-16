package group16.project.game.models

enum class GameState {
    START {
        override var  text = "Starting..."
        override fun signal() = SETUP
    },

    SETUP {
        override var  text = "Player's Turn"
        override fun signal() = WAITING
    },

    WAITING {
        override var  text = "Waiting For Other Player..."
        override fun signal() = ANIMATION
    },

    ANIMATION {
        override var  text = "Executing Move..."
        override fun signal() = SETUP
    },

    GAME_OVER {
        override var text = "Game Over"
        override fun signal() = GAME_OVER
    };
    open var text = ""
    abstract fun signal(): GameState
}