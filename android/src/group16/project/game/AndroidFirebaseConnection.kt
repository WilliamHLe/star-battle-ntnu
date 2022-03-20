package group16.project.game

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import group16.project.game.models.FirebaseInterface

class AndroidFirebaseConnection : FirebaseInterface {
    private val database = Firebase.database("https://tdt4240-battlestar-default-rtdb.europe-west1.firebasedatabase.app/")
    val myRef = database.getReference("message")

    override fun someFunction() {
        //TODO("Not yet implemented")
        println("Android conenction class")
        if(myRef != null) {
            myRef.setValue("Hello World!")
        } else {
            println("Databaseref was not set.")
        }
    }

    override fun test() {
        TODO("Not yet implemented")
    }

    override fun setValueInDb(target: String, value: String) {
        //TODO("Not yet implemented")
        var ref=database.getReference(target)
        if(ref != null) {
            ref.setValue(value)
        } else {
            println("Databaseref was not found.")
        }
    }
}