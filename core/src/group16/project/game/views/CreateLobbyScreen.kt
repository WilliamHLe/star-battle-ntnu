package group16.project.game.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.VisTextField
import group16.project.game.StarBattle

class CreateLobbyScreen(val gameController: StarBattle) : View() {


    override fun init() {
        var table = VisTable()

        // Create title
        val txtTitle = VisLabel("Create game lobby")
        txtTitle.setAlignment(1)

        // Text input
        val nameField = VisTextField()
        nameField.setAlignment(1)
        nameField.messageText = "Lobby name"

        val btnCreate = VisTextButton("Create")
        btnCreate.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                gameController.changeScreen(MainMenuScreen::class.java)
            }
        })
        // Return to menu button
        val btnReturn = VisTextButton("Return to main menu")
        btnReturn.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                gameController.changeScreen(MainMenuScreen::class.java)
            }
        })

        table.columnDefaults(0).pad(10f)
        table.columnDefaults(1).pad(10f)
        table.setFillParent(true)
        table.add(txtTitle).size(stage.width / 2, 45.0f)
        table.row()
        table.add(nameField).size(stage.width / 2, 45.0f)
        table.row()
        table.add(btnCreate).size(stage.width / 3, 45.0f)
        table.row()
        table.add(btnReturn).size(stage.width / 3, 45.0f)

        stage.addActor(table)
        Gdx.app.log("VIEW", "Create lobby loaded")
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
    }

    override fun draw(delta: Float) {}

    override fun pause() {
        super.pause()
    }

    override fun resume() {
        super.resume()
    }

    override fun hide() {
        super.hide()
    }

    override fun dispose() {
        super.dispose()
    }
}