package group16.project.game.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.VisTextField
import group16.project.game.StarBattle
import group16.project.game.models.FirebaseInterface
import group16.project.game.models.GameInfo

class CreateLobbyScreen(val gameController: StarBattle, private val fbic: FirebaseInterface) : View() {

    override fun init() {
        val table = VisTable()

        val bg = Image(TextureRegionDrawable(TextureRegion(TextureHandler.textures["BACKGROUND"])))
        bg.setSize(stage.width, stage.height)
        bg.setPosition(0f, 0f)
        stage.addActor(bg)

        // Create title
        val txtTitle = VisLabel("Create game lobby")
        txtTitle.setAlignment(1)
        txtTitle.setFontScale(1.5f)

        //Error label
        val errorMessageLabel = Label("", Label.LabelStyle(BitmapFont(), Color.RED))
        errorMessageLabel.setAlignment(1)

        // Text input
        val nameField = VisTextField()
        nameField.setAlignment(1)
        nameField.messageText = "Lobby name"

        val btnCreate = VisTextButton("Create")
        btnCreate.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                //Hide keyboard
                nameField.onscreenKeyboard.show(false)
                if (nameField.toString() == "") {
                    //Update error message
                    errorMessageLabel.setText("Need a lobby name")
                }else {
                    //Create lobby and change to game screen.
                    fbic.createLobby(nameField.toString(), gameController)

                }
            }
        })
        // Return to menu button
        val btnReturn = VisTextButton("Return to main menu")
        btnReturn.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                //Hide keyboard and set error message to blank
                errorMessageLabel.setText("")
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
        table.add(btnCreate).size(stage.width / 3, 45.0f)
        table.row()
        table.add(btnReturn).size(stage.width / 3, 45.0f)
        table.row()
        table.add(errorMessageLabel).size(stage.width / 2, 45.0f)

        stage.addActor(table)
        Gdx.app.log("VIEW", "Create lobby loaded")
    }


    override fun draw(delta: Float) {
        if (GameInfo.currentGame != "null") {
            gameController.changeScreen(GameScreen::class.java)
        }
    }
}