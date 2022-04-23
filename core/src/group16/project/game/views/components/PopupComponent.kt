package group16.project.game.views.components

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.kotcrab.vis.ui.layout.FloatingGroup
import com.kotcrab.vis.ui.widget.VisTextButton
import group16.project.game.Configuration

class PopupComponent(child: Actor?, isFullscreen: Boolean = false, hasCloseButton: Boolean = true): FloatingGroup() {

    init {
        // Root widget
        this.setPosition(0f, 0f)
        this.setSize(Configuration.gameWidth, Configuration.gameHeight)
        // Semi-transparent fullscreen
        if (isFullscreen) drawSemiTransparentBackground()

        // Child widget
        this.setChild(child)

        // Close button
        if (hasCloseButton) this.drawCloseButton()
    }

    private fun drawCloseButton() {
        val btnClosePopup = VisTextButton("X")
        btnClosePopup.setSize(60f, 60f)
        btnClosePopup.setPosition(Configuration.gameWidth - 60f ,Configuration.gameHeight - 60f)
        btnClosePopup.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                closePopup()
            }
        })
        this.addActor(btnClosePopup)
    }

    fun closePopup() {
        this.parent.removeActor(this)
    }

    fun setChild(child: Actor?) {
        if (child != null) this.addActor(child)
    }

    private fun drawSemiTransparentBackground() {
        val pixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
        pixmap.setColor(Color.BLACK)
        pixmap.fillRectangle(0, 0, 1, 1)
        val texture = Texture(pixmap)
        pixmap.dispose()

        val image = Image(texture)
        image.color.a=.8f
        image.setSize(Configuration.gameWidth, Configuration.gameHeight)
        this.addActor(image)
    }
}