package group16.project.game.views.components

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.kotcrab.vis.ui.layout.FloatingGroup
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import group16.project.game.StarBattle
import group16.project.game.models.Game
import group16.project.game.views.MainMenuScreen

class EndGameComponent(score: Int, game: Game, gameController: StarBattle): FloatingGroup() {
    private val gWidth = Gdx.graphics.width.toFloat()
    private val gHeight = Gdx.graphics.height.toFloat()
    private val header = VisLabel("Game Ended!")
    private var winOrLooseText = VisLabel("")
    private var text = VisLabel("")

    init {
        this.setPosition(0f ,0f)
        this.setSize(gWidth, gHeight)

        if (score == 0) {
            winOrLooseText.setText("Tie :|")
            text.setText("No point will be added or deducted from your score")
        } else if (score < 0) {
            winOrLooseText.setText("You lost :'( ")
            text.setText("${-score} points will be deducted from your score")
        } else {
            winOrLooseText.setText("You won!! :)")
            text.setText("$score points will be added to your score")
        }

        val btnPlayAgain = VisTextButton("Play Again")
        btnPlayAgain.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                game.deleteLobby()
                gameController.changeScreen(MainMenuScreen::class.java)
            }
        })

        val btnBack = VisTextButton("Return to main menu")
        btnBack.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                game.deleteLobby()
                gameController.changeScreen(MainMenuScreen::class.java)
            }
        })


        // Texts
        header.setAlignment(1)
        winOrLooseText.setAlignment(1)
        text.setAlignment(1)

        // Root table
        val table = VisTable()
        table.columnDefaults(0).pad(10f)
        table.setFillParent(true)
        table.add(header).size(gWidth*0.8f, gHeight*0.05f)
        table.row()
        table.add(winOrLooseText).size(gWidth*0.7f, gHeight*0.05f)
        table.row()
        table.add(text).size(gWidth*0.7f, gHeight*0.05f)
        table.row()
        //table.add(btnPlayAgain)
        //table.row()
        table.add(btnBack)

        this.addActor(table)
    }


}