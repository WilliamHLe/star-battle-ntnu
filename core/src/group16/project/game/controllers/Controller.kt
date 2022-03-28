package group16.project.game.controllers

import com.badlogic.gdx.scenes.scene2d.Stage
import group16.project.game.StarBattle
import group16.project.game.views.View


abstract class Controller(val game: StarBattle, val stage: Stage) {

    val dbconnection = game.getDBConnection()


    abstract fun init();


}