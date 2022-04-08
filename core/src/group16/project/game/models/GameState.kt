package group16.project.game.models

enum class GameState {
    START {
        override fun signal() = SETUP
    },

    SETUP {
        override fun signal() = WAITING
    },

    WAITING {
        override fun signal() = ANIMATION
    },

    ANIMATION {
        override fun signal() = SETUP
    },

    GAME_OVER {
        override fun signal() = GAME_OVER
    };

    abstract fun signal(): GameState
}