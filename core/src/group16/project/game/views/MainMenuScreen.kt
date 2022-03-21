package group16.project.game.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.VisTextField
import group16.project.game.StarBattle
import group16.project.game.models.CoreFirebaseConnection

class MainMenuScreen(val gameController: StarBattle) : View() {
    override fun draw(delta: Float) {}
    override fun pause() {}
    override fun resume() {}
    override fun hide() {}

    override fun init() {
        var table = VisTable()
        var dbconnection = gameController.getDBConnection()

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
        //TODO: this button and function were just used to verify that app can put value to our database. Feel free to change it when
        // the real join lobby function is implemented.
        val btnCreateLobby = VisTextButton("Create lobby")
        btnCreateLobby.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                dbconnection.setValueInDb("message", "Valueged from app")

                //dbconnection.setValueInDb("user", "helene")

                //dbconnection.setValueInDb("user/uId_1/Name",  "Helene")
                /*dbconnection.setValueInDb("user/uId_2/Name", "Helene 2")
                dbconnection.setValueInDb("lobby/lobbyId_1/Name", "Lobby 1")
                dbconnection.setValueInDb("lobby/lobbyId_1/Player_1", "uId_1")
                dbconnection.setValueInDb("lobby/lobbyId_1/Player_2", "uId_2")
                dbconnection.setValueInDb("lobby/lobbyId_1/state", "1")
                dbconnection.setValueInDb("user/uId_1/lobbies/lobbyId_1", "True")
                dbconnection.setValueInDb("user/uId_2/lobbies/lobbyId_1", "True")

                dbconnection.setValueInDb("lobby/lobbyId_2/Name", "lobby 2")
                dbconnection.setValueInDb("lobby/lobbyId_2/Player_1", "uId_1")
                dbconnection.setValueInDb("lobby/lobbyId_2/state", "0")
                dbconnection.setValueInDb("user/uId_1/lobbies/lobbyId_2", "True")
                //dbconnection.checkoIfExistInDb("lobby", "lobbyId_1")*/

                //Gdx.app.log(hei.toString(), "Hellow orld")


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