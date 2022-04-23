package group16.project.game

import com.badlogic.gdx.utils.Array
import group16.project.game.views.components.ImageSlideshowComponent
import group16.project.game.views.components.SkinComponent

// Singleton configuration
object Configuration {
    const val gameIcon = "badlogic.jpg"
    const val gameTitle = "Star Battle NTNU"
    const val debug = false
    var gameWidth = 1280.0f
    var gameHeight = 720.0f
    val slides = Array<ImageSlideshowComponent>()
    val skins = Array<SkinComponent>()
    init {
        // Init tutorial slides
        slides.add(ImageSlideshowComponent("tutorial1.png", "This is the screen when you create a lobby. The game status is at the top with the lobby code under it. Give the code to your friend or someone else for them to join your lobby."))
        slides.add(ImageSlideshowComponent("tutorial2.png", "When your opponent has joined, the game status will update. Now you can move your ufo on the left side and your target on your right side. You move it by clicking on one square in the position or target grid. NB! The opponent might move"))
        slides.add(ImageSlideshowComponent("tutorial3.png", "When you have positioned your UFO and target at your desired place you click on the 'End Turn' button at the bottom. Now you will see that the status on top will change and you will have to wait for the your opponent to end their turn."))
        slides.add(ImageSlideshowComponent("tutorial4.png", "When both you and the opponent have ended your turns the status will be updated and you can see where your opponent moves. If anyone got hit they will loose one heart. After this you can change your UFO and target position again."))
        slides.add(ImageSlideshowComponent("tutorial5.png", "There is also some power-ups you can use. A power-up can only be used once. You choose it by clicking on the power-up. You can not un-click the power-up. The shield power-up will protect you if hit but you only have it one round"))
        slides.add(ImageSlideshowComponent("tutorial6.png", "When either you or the opponent are left with 0 hearts, the game ends. The winner will get points added to their total score and the looser will be deducted points from theirs. If it a tie no points will be deducted or added. A players total score represent how many games they have won and it is used for the leaderboard list"))
        slides.add(ImageSlideshowComponent("tutorial7.png", "Here is a summary of the most important parts of the game. You can access this slideshow in game by clicking on the help screen button. By clicking on the menu screen you can leave the lobby, but you can't return. Have fun!"))

        // Init skins
        skins.add(SkinComponent("ufo_default.png", "Default ufo"))
        skins.add(SkinComponent("ufo_moe.png", "Moe ufo"))
        skins.add(SkinComponent("ufo_boss.png", "Boss ufo"))
        skins.add(SkinComponent("ufo_doge.png", "Doge ufo"))
        skins.add(SkinComponent("ufo_bongo.png", "Bongo ufo"))
        skins.add(SkinComponent("ufo_alien.png", "Alien ufo"))
    }
}