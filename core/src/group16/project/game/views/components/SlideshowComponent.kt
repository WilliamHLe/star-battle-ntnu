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

class SlideshowComponent(private val slides: ArrayList<ImageSlideshowComponent>): FloatingGroup() {
    private val WIDTH = Gdx.graphics.width.toFloat()
    private val HEIGHT = Gdx.graphics.height.toFloat()

    private val text = VisLabel("")
    private val display = VisTable()
    private val dots = GridGroup(20f, 10f)

    private var currentSlide = 0

    init {
        this.setPosition(0f ,0f)
        this.setSize(WIDTH, HEIGHT)

        // Navigation buttons
        val btnLeft = VisTextButton("<")
        btnLeft.setSize(50f, 70f)
        btnLeft.setPosition(0f ,HEIGHT/2 - 35f)
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
        btnRight.setPosition(WIDTH - 50f ,HEIGHT/2 - 35f)
        btnRight.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                currentSlide = (currentSlide+1)%slides.size
                updateSlide()
                println("Right")
            }
        })
        this.addActor(btnRight)

        // Dots
        updateDots()
        dots.setSize((dots.itemWidth + dots.spacing) * slides.size + dots.spacing, 20f)

        // Text
        text.setAlignment(1)
        text.wrap = true;
        text.width = WIDTH*0.8f;

        // Root table
        var table = VisTable()
        table.columnDefaults(0)
        table.columnDefaults(1)
        table.setFillParent(true)
        table.add(display).size(WIDTH*0.85f, HEIGHT*0.7f)
        table.row()
        table.add(dots)
        table.row()
        table.add(text).size(WIDTH*0.8f, HEIGHT*0.15f)
        table.row()
        //table.add("heifpewijDFJEWINFIPEW FIWEJG IJF  JEIP GJEWOFI EWO JPIWEJGPIWEJhiposerghjip hgio ehroøgh iueghr eighr ueghuerh guhreousgh reughohr eugh ohg hgaeo øghreoøsgl.ndlrhieg g roehgo hh eohgaw-lgø sozgeug  vihpevn  nljdjgd iejg p  jpfwejg pe jg i gg eoaa æoigg  æogi")

        this.addActor(table)
        updateSlide()
    }

    fun updateSlide() {
        val imgSlideshow = slides[currentSlide]
        // Update display
        display.clear()
        val img = imgSlideshow.image
        img.setSize(WIDTH*0.85f, HEIGHT*0.7f)
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
}