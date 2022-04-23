package group16.project.game.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.ObjectMap

object TextureHandler {
    val textures: ObjectMap<String, Texture> = ObjectMap()

    fun initTextures() {
        textures.put("BACKGROUND", Texture(Gdx.files.internal("background.png")))
        textures.put("LOGO", Texture(Gdx.files.internal("logo.png")))
        textures.put("LONGBAR", Texture(Gdx.files.internal("longbar.png")))
        textures.put("BAR", Texture(Gdx.files.internal("bar.png")))
        textures.put("TRAJECTORY", Texture(Gdx.files.internal("trajectory.png")))
        textures.put("SHIELD", Texture(Gdx.files.internal("Shield.png")))
        textures.put("TARGET", Texture(Gdx.files.internal("target.png")))
        textures.put("GAME_BACKGROUND", Texture(Gdx.files.internal("background_dark.png")))
        textures.put("HEART_FULL", Texture(Gdx.files.internal("heart_full.png")))
        textures.put("HEART_EMPTY", Texture(Gdx.files.internal("heart.png")))
        textures.put("ICON_HELP", Texture(Gdx.files.internal("help_icon.png")))
        textures.put("ICON_SHIELD", Texture(Gdx.files.internal("Shieldbtn.png")))
        textures.put("ICON_MENU", Texture(Gdx.files.internal("cog_icon.png")))
        textures.put("UI_TOPBOX", Texture(Gdx.files.internal("topbox.png")))
        textures.put("UI_TURNBOX", Texture(Gdx.files.internal("turnbox.png")))
        textures.put("UI_BOTTOMBOX", Texture(Gdx.files.internal("bottombox.png")))
        textures.put("UI_SELECTION", Texture(Gdx.files.internal("selection.png")))
        textures.put("UI_WINBOX", Texture(Gdx.files.internal("winbox.png")))
        textures.put("DOT_MARKED", Texture(Gdx.files.internal("heart_full.png")))
        textures.put("DOT", Texture(Gdx.files.internal("heart.png")))
    }
}