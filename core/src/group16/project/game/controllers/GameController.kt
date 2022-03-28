package group16.project.game.controllers

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.VisTextField
import group16.project.game.StarBattle
import group16.project.game.views.MainMenuScreen

class GameController(game: StarBattle, stage: Stage) : Controller(game, stage) {
    var table = VisTable()
    var txtDescription  = VisTextField("Model-View-Controller pattern :) - GAME")
    var btnStart = VisTextButton("Return to main menu")


    override fun init() {
        // Create the description field
        txtDescription.isDisabled = true
        txtDescription.setAlignment(1) // center text

        // Add a "StartGame" button
        btnStart.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                game.changeScreen(MainMenuScreen::class.java)
            }
        })
    }



}