package group16.project.game.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.kotcrab.vis.ui.layout.FloatingGroup
import com.kotcrab.vis.ui.layout.GridGroup
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisScrollPane
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import group16.project.game.Configuration
import group16.project.game.StarBattle
import group16.project.game.ecs.utils.InputHandler
import group16.project.game.views.components.SkinComponent


class SkinScreen(val gameController: StarBattle): View(){
    private val skinSize = 300f
    private val skinPad = 50f
    private val group = GridGroup(skinSize, skinPad) //item size 64px, spacing 4px
    override fun init() {
        updateTable()
        Gdx.app.log("VIEW", "Skin loaded")
    }

    override fun draw(delta: Float) {}

    private fun skinActor(skin: SkinComponent, i: Int): FloatingGroup {
        val skinImage = skin.image
        val skinText = VisLabel(skin.name)
        skinText.setAlignment(1) // center text

        val table = VisTable()
        table.setSize(skinSize, skinSize)
        table.add(skinImage).growX().minWidth(0f).height(skinSize - skinPad - skinSize/4)
        table.row()
        table.add(skinText).size(skinSize, 60f)

        val pane = FloatingGroup()
        pane.setSize(skinSize, skinSize)
        pane.touchable = Touchable.enabled
        pane.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                InputHandler.playerSkin = i
                Gdx.app.debug("VIEW","Clicked $skinText ($i)")
                updateGroup()
            }
        })
        if (InputHandler.playerSkin == i) {
            val selected = Image(TextureRegionDrawable(TextureRegion(TextureHandler.textures["UI_SELECTION"])))
            selected.setSize(skinSize, skinSize)
            selected.setPosition(0f, 0f)
            pane.addActor(selected)
        }
        pane.addActor(table)
        return pane
    }

    private fun updateGroup() {
        group.clear()
        for ((i, skin) in Configuration.skins.withIndex()) {
            group.addActor(skinActor(skin, i))
        }
    }

    private fun updateTable() {
        stage.clear()
        updateGroup()

        // Return to menu button
        val btnReturn = VisTextButton("Return to main menu")
        btnReturn.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                gameController.changeScreen(MainMenuScreen::class.java)
            }
        })

        val scrollPane = VisScrollPane(group)
        scrollPane.setScrollingDisabled(true, false) //disable X scrolling
        val table = VisTable()
        table.columnDefaults(0).pad(10f)
        table.setFillParent(true)
        table.add(scrollPane).size(stage.width - 50, stage.height - 150)
        table.row()
        table.add(btnReturn).size(stage.width / 2, 45f)
        stage.addActor(table)
    }
}