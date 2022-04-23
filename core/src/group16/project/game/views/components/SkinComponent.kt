package group16.project.game.views.components

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

class SkinComponent(val imagePath: String, val name: String) {
    val image = Image(TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal(imagePath)))))
}