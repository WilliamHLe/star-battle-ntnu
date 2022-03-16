package group16.project.game.views
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport
import group16.project.game.Configuration
import com.badlogic.gdx.InputMultiplexer

abstract class View : Screen {
    protected var stage = Stage(ScreenViewport());
    override fun show() {
        // Set debug status
        stage.isDebugAll = Configuration.debug

        // Map the controller
        val input = InputMultiplexer()
        input.addProcessor(stage)
        Gdx.input.inputProcessor = input

        // Screen-specific initialization
        init()
    }

    abstract fun init()

    override fun render(delta: Float) {
        // Clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        draw(delta)
        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    abstract fun draw(delta: Float)

    override fun pause() {
        TODO("Invoked when your application is paused.")
    }

    override fun resume() {
        TODO("Invoked when your application is resumed after pause.")
    }

    override fun hide() {
        TODO("This method is called when another screen replaces this one.")
    }

    override fun dispose() {
        stage.dispose()
    }
}