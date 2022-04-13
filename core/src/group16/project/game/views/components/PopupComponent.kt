package group16.project.game.views.components

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.kotcrab.vis.ui.layout.FloatingGroup


class PopupComponent(isFullscreen: Boolean = false): FloatingGroup() {
    init {
        // Root widget
        this.setPosition(0f, 0f)
        this.setSize(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        // Semi-transparent fullscreen
        if (isFullscreen) drawSemiTransparentBackground()

        // Child widget
        this.addActor(SlideshowComponent())


    }
    private fun drawSemiTransparentBackground() {
        val pixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
        pixmap.setColor(Color.BLACK)
        pixmap.fillRectangle(0, 0, 1, 1)
        val texture = Texture(pixmap)
        pixmap.dispose()

        val image = Image(texture)
        image.color.a=.8f;
        image.setSize(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        this.addActor(image)
    }
}