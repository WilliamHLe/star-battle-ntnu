package group16.project.game.models

enum class GameState {
    START {
        override fun signal() = WAITING
    },

    WAITING {
        override fun signal() = SETUP
    },

    SETUP {
        override fun signal() = ANIMATION
    },

    ANIMATION {
        override fun signal() = WAITING
    },

    GAME_OVER {
        override fun signal() = GAME_OVER
    };

    abstract fun signal(): GameState
}