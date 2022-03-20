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
        if(myRef != null) {
            myRef.setValue(value)
        } else {
            println("Databaseref was not found.")
        }
    }
}