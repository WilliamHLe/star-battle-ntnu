package group16.project.game.views
import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport
import group16.project.game.Configuration
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.StretchViewport

abstract class View : Screen {
    protected val camera = OrthographicCamera()
    protected var stage = Stage(FitViewport(Configuration.gameWidth, Configuration.gameHeight, camera));
    override fun show() {
        // Set debug status
        stage.isDebugAll = Configuration.debug
        Gdx.app.logLevel = if (Configuration.debug) Application.LOG_DEBUG else Application.LOG_INFO

        // Map the controller to VisUI events
        val input = InputMultiplexer()
        input.addProcessor(stage)
        Gdx.input.inputProcessor = input

        // Screen-specific initialization
        init()
        Gdx.app.log("VIEW", "SCREEN loaded")
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

    override fun pause() {}

    override fun resume() {}

    override fun hide() {
        stage.clear()
    }

    override fun dispose() {
        stage.dispose()
    }
}