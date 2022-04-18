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
import group16.project.game.views.components.ImageSlideshowComponent
import group16.project.game.views.components.PopupComponent
import group16.project.game.views.components.SlideshowComponent

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

        // Add popup with slideshow
        val slides = ArrayList<ImageSlideshowComponent>()
        slides.add(ImageSlideshowComponent("background.png", "Example text 1: background.png"))
        slides.add(ImageSlideshowComponent("background2.png", "Example text 2: background2.png"))
        slides.add(ImageSlideshowComponent("background_dark.png", "Example text 3: background_dark.png"))
        slides.add(ImageSlideshowComponent("background2.png", "Example text 4: background2.png"))
        stage.addActor(PopupComponent(SlideshowComponent(slides), true))
    }

    override fun draw(delta: Float) {}
}