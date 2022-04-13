package group16.project.game.views.components

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.kotcrab.vis.ui.layout.FloatingGroup
import com.kotcrab.vis.ui.layout.GridGroup
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton

class SlideshowComponent: FloatingGroup() {
    private val WIDTH = Gdx.graphics.width.toFloat()
    private val HEIGHT = Gdx.graphics.height.toFloat()

    private val text = VisLabel("")
    private val display = VisTable()
    private val dots = GridGroup(20f, 10f)

    private val slides = ArrayList<ImageSlideshowComponent>()
    private var currentSlide = 0

    fun updateSlide() {
        val imgSlideshow = slides[currentSlide]
        // Update display
        display.clear()
        val img = imgSlideshow.image
        img.setSize(WIDTH*0.7f, HEIGHT*0.6f)
        display.addActor(img)
        // Update dots
        updateDots()
        // Update text
        text.setText(imgSlideshow.text)
    }
    fun updateDots() {
        dots.clear()
        for (i in 0 until slides.size) {
            if (i == currentSlide) {
                var markedDot = Image(TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("heart_full.png")))))
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
    init {
        this.setPosition(0f ,0f)
        this.setSize(WIDTH, HEIGHT)

        // Navigation buttons
        val btnLeft = VisTextButton("left")
        btnLeft.setSize(100f, 200f)
        btnLeft.setPosition(0f ,HEIGHT/2 - 100f)
        btnLeft.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                currentSlide -= 1
                if (currentSlide < 0) currentSlide = slides.size-1
                updateSlide()
                println("Left")
            }
        })
        this.addActor(btnLeft)

        val btnRight = VisTextButton("right")
        btnRight.setSize(100f, 200f)
        btnRight.setPosition(WIDTH - 100f ,HEIGHT/2 - 100f)
        btnRight.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                currentSlide = (currentSlide+1)%slides.size
                updateSlide()
                println("Right")
            }
        })
        this.addActor(btnRight)

        // Display
        slides.add(ImageSlideshowComponent("background.png", "Example text 1: background.png"))
        slides.add(ImageSlideshowComponent("background2.png", "Example text 2: background2.png"))
        slides.add(ImageSlideshowComponent("background_dark.png", "Example text 3: background_dark.png"))
        slides.add(ImageSlideshowComponent("background2.png", "Example text 4: background2.png"))

        // Dots
        updateDots()
        dots.setSize((dots.itemWidth + dots.spacing) * slides.size + dots.spacing, 40f)

        // Text
        text.setText("Lorem ipsum...")
        text.setAlignment(1)

        // Root table
        var table = VisTable()
        table.columnDefaults(0).pad(10f)
        table.columnDefaults(1).pad(10f)
        table.setFillParent(true)
        table.add(display).size(WIDTH*0.7f, HEIGHT*0.6f)
        table.row()
        table.add(dots)
        table.row()
        table.add(text).size(WIDTH*0.7f, HEIGHT*0.1f)

        this.addActor(table)
        updateSlide()
    }
}