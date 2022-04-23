package group16.project.game.views.components

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

class ImageSlideshowComponent(imagePath: String, val text: String) {
    val texture = Texture(Gdx.files.internal(imagePath))
    val image = Image(TextureRegionDrawable(TextureRegion(texture)))
    init {
        image.setPosition(0f, 0f)
    }
    fun dispose() {
        texture.dispose()
    }
}