package group16.project.game

import group16.project.game.models.FirebaseInterface
import pl.mk5.gdx.fireapp.GdxFIRApp
import pl.mk5.gdx.fireapp.GdxFIRAuth
import pl.mk5.gdx.fireapp.GdxFIRDatabase
import pl.mk5.gdx.fireapp.GdxFIRLogger
import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser

//import
/**
 * TODO
 * We need to find out if we need the desktop version.
 * -If so we need to create at way to interact with the firebase project.
 * -If not, we need to do some changes in core, remove this class, etc
 */
class DesktopFirebaseConnection : FirebaseInterface {

    override fun setValueInDb(target: String, value: String) {
        //TODO("Not yet implemented")
        println("Desktop connection class")
    }
}