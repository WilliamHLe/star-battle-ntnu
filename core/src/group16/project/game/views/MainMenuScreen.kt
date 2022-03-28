package group16.project.game.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import group16.project.game.StarBattle
import group16.project.game.controllers.MainMenuController

class MainMenuScreen(val game: StarBattle ) : View() {
    private val controller = MainMenuController(game, this.stage)

    override fun draw(delta: Float) {}
    override fun pause() {}
    override fun resume() {}
    override fun hide() {}

    override fun init() {
        controller.init()


        // Create the layout
        controller.table.columnDefaults(0).pad(10f)
        controller.table.columnDefaults(1).pad(10f)
        controller.table.setFillParent(true)
        controller.table.add(controller.txtDescription).size(stage.width / 2, 100.0f)
        controller.table.row()
        controller.table.add(controller.btnStart).size(stage.width / 2, 45.0f)
        controller.table.row()
        controller.table.add(controller.btnCreateLobby).size(stage.width / 2, 45.0f)
        stage.addActor(controller.table)
        Gdx.app.log("VIEW", "Main Menu loaded")
    }
}