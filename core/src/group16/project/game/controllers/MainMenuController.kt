package group16.project.game.controllers

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.VisTextField
import group16.project.game.StarBattle
import group16.project.game.views.GameScreen
import group16.project.game.views.MainMenuScreen
import group16.project.game.views.View

class MainMenuController(game: StarBattle, stage: Stage) : Controller(game, stage) {

    var table = VisTable()
    var txtDescription  = VisTextField("Model-View-Controller pattern :) - MAIN MENU")
    var btnStart = VisTextButton("Start the game")
    var btnCreateLobby = VisTextButton("Create lobby")


    override fun init() {


        // Create the description field

        txtDescription.isDisabled = true
        txtDescription.setAlignment(1) // center text

        // Add a "StartGame" button
        btnStart.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                game.changeScreen(GameScreen::class.java)
                println("START GAME BUTTON")
            }
        })

        // Add a "Create Lobby" button
        //TODO: this button and function were just used to verify that app can put value to our database. Feel free to change it when
        // the real join lobby function is implemented.
        btnCreateLobby.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                dbconnection.setValueInDb("message", "Value changed from app")
                println("BTN WORKED")

            }
        })

    }

}