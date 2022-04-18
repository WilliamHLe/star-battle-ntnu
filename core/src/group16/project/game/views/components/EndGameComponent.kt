package group16.project.game.views.components

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.kotcrab.vis.ui.layout.FloatingGroup
import com.kotcrab.vis.ui.layout.GridGroup
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import group16.project.game.StarBattle
import group16.project.game.models.Game
import group16.project.game.views.MainMenuScreen

class EndGameComponent(score: Int, game: Game, gameController: StarBattle): FloatingGroup() {
    private val WIDTH = Gdx.graphics.width.toFloat()
    private val HEIGHT = Gdx.graphics.height.toFloat()

    private val header = VisLabel("Game Ended!")
    private var winOrLooseText = VisLabel("")
    private var text = VisLabel("")

    init {
        val pixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
        pixmap.setColor(Color.BLACK)
        pixmap.fillRectangle(0, 0, 1, 1)
        val texture = Texture(pixmap)
        pixmap.dispose()

        val image = Image(texture)
        image.color.a=1.0f
        image.setSize(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        //this.addActor(image)

        this.setPosition(0f ,0f)
        this.setSize(WIDTH, HEIGHT)

        if (score < 0) {
            winOrLooseText.setText("You lost :'( ")
            text.setText("${-score} points will be deducted form your score")
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
        var table = VisTable()
        table.sizeBy(WIDTH/2, HEIGHT/2)
        table.color.set(Color.BLACK)
        table.columnDefaults(0).pad(10f)
        table.setFillParent(true)
        table.add(header).size(WIDTH*0.8f, HEIGHT*0.05f)
        table.row()
        table.add(winOrLooseText).size(WIDTH*0.7f, HEIGHT*0.05f)
        table.row()
        table.add(text).size(WIDTH*0.7f, HEIGHT*0.05f)
        table.row()
        table.add(btnPlayAgain)
        table.row()
        table.add(btnBack)



        this.addActor(table)
    }


}