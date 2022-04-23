package group16.project.game.views.components

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.kotcrab.vis.ui.layout.FloatingGroup
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import group16.project.game.Configuration
import group16.project.game.StarBattle
import group16.project.game.models.Game
import group16.project.game.views.MainMenuScreen
import group16.project.game.views.TextureHandler

class EndGameComponent(score: Int, game: Game, gameController: StarBattle): FloatingGroup() {
    private val gWidth = Configuration.gameWidth
    private val gHeight = Configuration.gameHeight
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

        // Win box
        val winbox = Image(TextureRegionDrawable(TextureRegion(TextureHandler.textures["UI_WINBOX"])))
        winbox.setSize(gWidth*0.5f, gHeight*0.5f)
        winbox.setPosition(gWidth/2 - gWidth*0.5f/2, gHeight/2 - gHeight*0.5f/2)
        this.addActor(winbox)

        // Texts
        header.setAlignment(1)
        header.setFontScale(1.5f)
        winOrLooseText.setAlignment(1)
        text.setAlignment(1)

        // Root table
        val table = VisTable()
        table.columnDefaults(0).pad(10f)
        table.setFillParent(true)
        table.add(header).size(gWidth*0.8f, 35f)
        table.row()
        table.add(winOrLooseText).size(gWidth*0.7f, 35f)
        table.row()
        table.add(text).size(gWidth*0.7f, 35f)
        table.row()
        table.add(btnBack).size(gWidth*0.2f, 45f)

        this.addActor(table)
    }


}