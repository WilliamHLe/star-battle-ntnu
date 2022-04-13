package group16.project.game.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
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
import group16.project.game.views.components.PopupComponent

class PlaceholderTutorialScreen(val gameController: StarBattle) : View() {

    override fun init() {
        val bg = Image(TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("background.png")))))
        bg.setSize(stage.width, stage.height)
        bg.setPosition(0f, 0f)
        stage.addActor(bg)

        var table = VisTable()

        // Create title
        val txtTitle = VisLabel("Create game lobby")
        txtTitle.setAlignment(1)

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
        table.add(btnReturn).size(stage.width / 3, 45.0f)

        //stage.addActor(table)
        Gdx.app.log("VIEW", "Create lobby loaded")

        // Add popup
        stage.addActor(PopupComponent(true))
    }

    override fun draw(delta: Float) {}
}