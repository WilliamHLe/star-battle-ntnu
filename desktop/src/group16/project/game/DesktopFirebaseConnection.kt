package group16.project.game

import group16.project.game.models.FirebaseInterface
import pl.mk5.gdx.fireapp.GdxFIRApp
import pl.mk5.gdx.fireapp.GdxFIRAuth
import pl.mk5.gdx.fireapp.GdxFIRDatabase

//import

class DesktopFirebaseConnection : FirebaseInterface {

    init {
        //TODO: Error hher
    println(GdxFIRApp.inst().configure());

    }


    override fun someFunction() {
        //TODO("Not yet implemented")
        println("Desktop connection class")
        GdxFIRAuth.inst().signInAnonymously()
            .then(
                GdxFIRDatabase.inst()
                    .inReference("message")
                    .setValue("Worked from dekstop!")
            )

    }

    override fun test() {
        TODO("Not yet implemented")
    }

    override fun setValueInDb(target: String, value: String) {
        TODO("Not yet implemented")
    }
}