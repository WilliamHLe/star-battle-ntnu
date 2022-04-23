package group16.project.game.views.components

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.layout.FloatingGroup
import com.kotcrab.vis.ui.layout.GridGroup
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import group16.project.game.Configuration

class SlideshowComponent(private val slides: Array<ImageSlideshowComponent>): FloatingGroup() {
    private val gWidth = Configuration.gameWidth
    private val gHeight = Configuration.gameHeight

    private val text = VisLabel("")
    private val display = VisTable()
    private val dots = GridGroup(20f, 10f)

    private var currentSlide = 0

    init {
        this.setPosition(0f ,0f)
        this.setSize(gWidth, gHeight)

        // Navigation buttons
        val btnLeft = VisTextButton("<")
        btnLeft.setSize(50f, 70f)
        btnLeft.setPosition(0f ,gHeight/2 - 35f)
        btnLeft.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                currentSlide -= 1
                if (currentSlide < 0) currentSlide = slides.size-1
                updateSlide()
                println("Left")
            }
        })
        this.addActor(btnLeft)

        val btnRight = VisTextButton(">")
        btnRight.setSize(50f, 70f)
        btnRight.setPosition(gWidth- 50f ,gHeight/2 - 35f)
        btnRight.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                currentSlide = (currentSlide+1)%slides.size
                updateSlide()
                println("Right")
            }
        })
        this.addActor(btnRight)

        // Display
        display.columnDefaults(0).pad(5f)
        display.setFillParent(true)

        // Dots
        updateDots()
        dots.setSize((dots.itemWidth + dots.spacing) * slides.size + dots.spacing, 20f)

        // Text
        text.setAlignment(1)
        text.wrap = true
        text.width = gWidth*0.8f

        // Root table
        val table = VisTable()
        table.columnDefaults(0).pad(10f)
        table.columnDefaults(1).pad(10f)
        table.setFillParent(true)
        table.add(display).size(gWidth*0.7f, gHeight*0.7f)
        table.row()
        table.add(dots)
        table.row()
        table.add(text).size(gWidth*0.8f, gHeight*0.15f)

        this.addActor(table)
        updateSlide()
    }

    fun updateSlide() {
        val imgSlideshow = slides[currentSlide]
        // Update display
        display.clear()
        val img = imgSlideshow.image
        img.setSize(gWidth*0.7f, gHeight*0.7f)
        display.addActor(img)
        // Update dots
        updateDots()
        // Update text
        text.setText(imgSlideshow.text)
    }

    private fun updateDots() {
        dots.clear()
        for (i in 0 until slides.size) {
            if (i == currentSlide) {
                val markedDot = Image(TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("heart_full.png")))))
                markedDot.name = "marked"
                markedDot.setSize(dots.itemWidth, dots.itemHeight)
                dots.addActor(markedDot)
            } else {
                val dot = Image(TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("heart.png")))))
                dot.setSize(dots.itemWidth, dots.itemHeight)
                dots.addActor(dot)
            }
        }
    }
}