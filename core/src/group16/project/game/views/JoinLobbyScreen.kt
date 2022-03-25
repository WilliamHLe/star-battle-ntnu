package group16.project.game.views;

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.VisTextField
import group16.project.game.StarBattle

class JoinLobbyScreen(val gameController: StarBattle) : View() {

    //Error label
    var errorMessageLabel: Label =Label("", LabelStyle(BitmapFont(), Color.RED))


    override fun init() {
        var table = VisTable()
        var dbconnection = gameController.getDBConnection()


        // Create title
        val txtTitle = VisLabel("Join game lobby")
        txtTitle.setAlignment(1)

        this.errorMessageLabel.setText("")
        this.errorMessageLabel.setAlignment(1)

        // Text input
        val nameField = VisTextField()
        nameField.setAlignment(1)
        nameField.messageText = "Lobby code"

        val btnJoin = VisTextButton("Join")
        btnJoin.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                nameField.onscreenKeyboard.show(false)
                errorMessageLabel.setText("")
                if (nameField.toString() == "") {
                    errorMessageLabel.setText("Need a lobby code")
                }else if(nameField.toString().length != 6) {
                    errorMessageLabel.setText("Lobby code is 6 charaters and/or numbers long")
                }else {
                    dbconnection.joinLobby(nameField.toString(), this@JoinLobbyScreen)
                    errorMessageLabel.setText("Waiting...")
                }
            }
        })
        // Return to menu button
        val btnReturn = VisTextButton("Return to main menu")
        btnReturn.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                nameField.onscreenKeyboard.show(false)
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
        table.add(btnJoin).size(stage.width / 3, 45.0f)
        table.row()
        table.add(btnReturn).size(stage.width / 3, 45.0f)
        table.row()
        table.add(errorMessageLabel).size(stage.width / 2, 45.0f)

        stage.addActor(table)
        Gdx.app.log("VIEW", "Join lobby loaded")
    }

    fun errorMessage(message: String) {
        errorMessageLabel.setText(message)
        if (message == " ") {

        }
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
    }

    override fun draw(delta: Float) {
        if (errorMessageLabel.textEquals("Success")) {
            gameController.changeScreen(GameScreen::class.java)
        }
    }

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
