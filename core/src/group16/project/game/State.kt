package group16.project.game

import com.badlogic.gdx.graphics.g2d.SpriteBatch

abstract class State {
    abstract fun render(sb: SpriteBatch?);
    abstract fun update(dt: Float);
}