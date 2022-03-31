package group16.project.game.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.VisTextField
import group16.project.game.StarBattle

class HighScoreScreen(val gameController: StarBattle): View(){

    //Players username and score of top 10
    var players = hashMapOf<String, Int>()
    val txtTitle = VisLabel("High score List")
    val btnReturn = VisTextButton("Return to main menu")




    override fun init() {
        // High score title
        txtTitle.setAlignment(1)

        // Return to menu button
        btnReturn.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                gameController.changeScreen(MainMenuScreen::class.java)
            }
        })

        Gdx.app.log("VIEW", "Join lobby loaded")
    }

    override fun draw(delta: Float) {
    }

    fun childMoved(userName: String, score: Int) {
        players[userName] = score
        updateTable()
    }

    fun userRemoved(userName: String) {
        players.remove(userName)
        updateTable()
    }

    fun updateTable() {
        val result = players.toList().sortedBy { (_, value) -> value}.toMap()
        stage.clear()

        var table = VisTable()

        table.columnDefaults(0).pad(5f)
        table.columnDefaults(1).pad(5f)
        table.columnDefaults(2).pad(5f)
        table.setFillParent(true)
        table.add(txtTitle).size(table.width, 10f).colspan(3)
        table.row()
        var i: Int = 1
        for (player in result) {
            val playerLabel = VisLabel(player.key)
            playerLabel.setAlignment(1)
            val scoreLabel = VisLabel((-player.value).toString())
            scoreLabel.setAlignment(0)
            table.add(VisLabel(i.toString())).size(stage.width / 14, 10.0f)
                .padRight(0.0f)
            table.add(playerLabel).size((stage.width / 14) * 4, 10.0f)
                .padRight(0.0f).padLeft(0.0f)
            table.add(scoreLabel).size((stage.width / 14)*2, 10.0f)
                .padLeft(0.0f)
            table.row()
            i++
        }
        table.add(btnReturn).size(stage.width/2, 35f)
            .padLeft(0.1f).padRight(0.1f).colspan(3)


        stage.addActor(table)
    }


}