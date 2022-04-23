package group16.project.game.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import group16.project.game.StarBattle

class SkinScreen(val gameController: StarBattle): View(){

    override fun init() {
        Gdx.app.log("VIEW", "Skin loaded")
    }

    override fun draw(delta: Float) {}

    private fun updateTable() {

    }
}