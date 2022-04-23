package group16.project.game.views

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
import group16.project.game.models.FirebaseInterface

class JoinLobbyScreen(val gameController: StarBattle, private val fbic: FirebaseInterface) : View() {

    //Error label
    var errorMessageLabel: Label =Label("", LabelStyle(BitmapFont(), Color.RED))

    override fun init() {
        val table = VisTable()

        // Create title
        val txtTitle = VisLabel("Join game lobby")
        txtTitle.setAlignment(1)
        txtTitle.setFontScale(1.5f)

        //Set error message to blank
        this.errorMessageLabel.setText("")
        this.errorMessageLabel.setAlignment(1)

        // Text input
        val nameField = VisTextField()
        nameField.setAlignment(1)
        nameField.messageText = "Lobby code"

        val btnJoin = VisTextButton("Join")
        btnJoin.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                //Remove keyboard
                nameField.onscreenKeyboard.show(false)
                //set error message to blank
                errorMessageLabel.setText("")
                //If lobby code is blank display error message
                if (nameField.toString() == "") {
                    errorMessageLabel.setText("Need a lobby name")
                //If lobby code is not 6 character
                }else if(nameField.toString().length != 6) {
                    errorMessageLabel.setText("Lobby name is 6 character and/or numbers long")
                //Else join lobby and display waiting
                }else {
                    //If could not join lobby error message will be updated in errorMessage function
                    fbic.joinLobby(nameField.toString(), this@JoinLobbyScreen)
                    errorMessageLabel.setText("Waiting...")
                }
            }
        })
        // Return to menu button
        val btnReturn = VisTextButton("Return to main menu")
        btnReturn.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                //Remove keyboard
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

    /**
     * Update error message
     */
    fun errorMessage(message: String) {
        errorMessageLabel.setText(message)
    }

    override fun draw(delta: Float) {
        //Only change screen to change screen to game screen if error message is "Success"
        if (errorMessageLabel.textEquals("Success")) {
            gameController.changeScreen(GameScreen::class.java)
        }
    }
}
