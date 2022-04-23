package group16.project.game.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import group16.project.game.StarBattle

class LeaderboardScreen(val gameController: StarBattle): View(){

    // Players username and score of top 10
    private var players = hashMapOf<String, Int>()
    private val txtTitle = VisLabel("Leaderboard")
    private val btnReturn = VisTextButton("Return to main menu")

    override fun init() {
        // High score title
        txtTitle.setAlignment(1)
        txtTitle.setFontScale(1.5f)

        // Return to menu button
        btnReturn.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                gameController.changeScreen(MainMenuScreen::class.java)
            }
        })

        updateTable()

        Gdx.app.log("VIEW", "Join lobby loaded")
    }

    override fun draw(delta: Float) {}

    /**
     * Update score for child that moved and changed
     */
    fun updateLeaderboard(userName: String, score: Int) {
        players[userName] = score
        updateTable()
    }

    fun deletePlayerFromHighScore(userName: String){
        players.remove(userName)
        updateTable()
    }

    /**
     * Update table with new leaderboard list
     */
    private fun updateTable() {
        val result = players.toList().sortedBy { (_, value) -> value}.reversed().toMap()
        //Clear stage to remove table
        stage.clear()
        val bg = Image(TextureRegionDrawable(TextureRegion(TextureHandler.textures["BACKGROUND"])))
        bg.setSize(stage.width, stage.height)
        bg.setPosition(0f, 0f)
        stage.addActor(bg)

        val table = VisTable()

        table.columnDefaults(0).pad(5f)
        table.columnDefaults(1).pad(5f)
        table.columnDefaults(2).pad(5f)
        table.setFillParent(true)
        table.add(txtTitle).size(table.width, 30f).colspan(3)
        table.row()
        var i = 1
        for (player in result) {
            val playerLabel = VisLabel(player.key)
            playerLabel.setAlignment(1)
            val scoreLabel = VisLabel((player.value).toString())
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
        Gdx.app.log("VIEW", "Leaderboard loaded")
    }
}