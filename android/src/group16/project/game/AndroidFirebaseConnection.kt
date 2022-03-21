package group16.project.game

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

import group16.project.game.models.FirebaseInterface

class AndroidFirebaseConnection : FirebaseInterface {
    //the link should may be stored in a separate file, be put here through a reference.
    private val database = Firebase.database("https://tdt4240-battlestar-default-rtdb.europe-west1.firebasedatabase.app/")


    override fun setValueInDb(target: String, value: String) {
        //TODO("Not yet implemented")
        var myRef=database.getReference(target)
        //Gdx.app.log("Firebase", "før")
        if(myRef != null) {
            myRef.setValue(value)
            println("hhhhh")
        } else {
            println("Databaseref was not found.")
            //Gdx.app.log("Firebase", "error")
        }
    }

    //override fun checkoIfExistInDb(target: String, value: String) {
        /*var myRef=database.getReference(target)
        if(myRef != null) {
            myRef.child(value).get().addOnSuccessListener {
                Gdx.app.log("Firebase", "Got value ${it.value}")
            }.addOnFailureListener{
                Gdx.app.log("Firebase", "Error getting data", it)
                //Log.e("firebase", "Error getting data", it)
            }
            //var doExitst = myRef.exists()
            //return doExitst
            //return "hei"
        } else {
            println("Databaseref was not found.")
        }*/
    //}

}