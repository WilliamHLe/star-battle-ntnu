package group16.project.game.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.VisTextField
import group16.project.game.StarBattle
//import group16.project.game.models.CoreFirebaseConnection

class MainMenuScreen(val gameController: StarBattle) : View() {
    override fun draw(delta: Float) {}
    override fun pause() {}
    override fun resume() {}

    override fun init() {
        var table = VisTable()
        var dbconnection = gameController.getDBConnection()

        //Log in user
        dbconnection.signInAnonymously()

        // Create the description field
        val txtDescription = VisTextField("Model-View-Controller pattern :) - MAIN MENU")
        txtDescription.isDisabled = true
        txtDescription.setAlignment(1) // center text

        // Add a "StartGame" button
        val btnStart = VisTextButton("Start the game")
        btnStart.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                gameController.changeScreen(GameScreen::class.java)
            }
        })
        // Add a "JoinLobby" button
        val btnJoin = VisTextButton("Join lobby game")
        btnJoin.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                gameController.changeScreen(JoinLobbyScreen::class.java)
            }
        })

        // Add a "Create Lobby" button
        val btnCreateLobby = VisTextButton("Create lobby")
        btnCreateLobby.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                gameController.changeScreen(CreateLobbyScreen::class.java)
            }
        })

        // Create the layout
        table.columnDefaults(0).pad(10f)
        table.columnDefaults(1).pad(10f)
        table.setFillParent(true)
        table.add(txtDescription).size(stage.width / 2, 100.0f)
        table.row()
        table.add(btnStart).size(stage.width / 2, 45.0f)
        table.row()
        table.add(btnJoin).size(stage.width/2, 45.0f)
        table.row()
        table.add(btnCreateLobby).size(stage.width / 2, 45.0f)

        stage.addActor(table)
        Gdx.app.log("VIEW", "Main Menu loaded")
    }
}