package group16.project.game

import android.app.Activity
import com.badlogic.gdx.Gdx
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth

import group16.project.game.models.FirebaseInterface
import group16.project.game.views.JoinLobbyScreen


class AndroidFirebaseConnection : FirebaseInterface, Activity() {
    //the link should may be stored in a separate file, be put here through a reference.
    private val database = Firebase.database("https://tdt4240-battlestar-default-rtdb.europe-west1.firebasedatabase.app/")
    private var auth: FirebaseAuth = Firebase.auth

    override fun signInAnonymously() {
        // [START signin_anonymously]
        var myRef: DatabaseReference = database.getReference("users")
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    println("signInAnonymously:success")
                    val currentUser = auth.currentUser
                    // If new user make user in db
                    if (currentUser != null) {
                        myRef.child(currentUser.uid).get().addOnSuccessListener {
                            if (it.value == null) {
                                val userName = "user-" + currentUser.uid.toString()
                                myRef.child(currentUser.uid).child("userName").setValue(userName)
                                Gdx.app.log("Firebase", "Success on crating user")
                            } else {
                                Gdx.app.log("Firebase", "Success user already exist")
                            }
                        }.addOnFailureListener {
                            Gdx.app.log("Firebase", "Error getting data", it)
                        }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    println("signInAnonymously:failure" + task.exception)
                }
            }
        // [END signin_anonymously]
    }


    override fun setValueInDb(target: String, value: String) {
        //TODO("Not yet implemented")
        var myRef=database.getReference(target)
        //Gdx.app.log("Firebase", "før")
        if(myRef != null) {
            myRef.setValue(value)
        } else {
            println("Databaseref was not found.")
            //Gdx.app.log("Firebase", "error")
        }
    }

    override fun createLobby(lobbyName: String){
        var myRef: DatabaseReference = database.getReference("lobbies")
        var randomLobbyCode: String = ""
        //https://stackoverflow.com/questions/46943860/idiomatic-way-to-generate-a-random-alphanumeric-string-in-kotlin
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        randomLobbyCode = (1..6)
                .map { allowedChars.random() }
                .joinToString("")
        val user = auth.currentUser

        myRef.child(randomLobbyCode).get().addOnSuccessListener {
            if (it.value == null && user != null && user.uid != null) {
                myRef.child(randomLobbyCode).child("name").setValue(lobbyName)
                myRef.child(randomLobbyCode).child("host").setValue(user.uid)
                database.getReference("users").child(user.uid).child(randomLobbyCode).setValue(true)
                Gdx.app.log("Firebase", "Success on creating lobby")
            }
            else {
                createLobby(lobbyName)
            }
        }.addOnFailureListener {
            Gdx.app.log("Firebase", "Error getting data", it)
        }
    }

    override fun joinLobby(lobbyCode: String, screen: JoinLobbyScreen){
        var myRef: DatabaseReference = database.getReference("lobbies")
        val user = auth.currentUser
        myRef.child(lobbyCode).get().addOnSuccessListener {
            var lobby = it.value
            if (lobby != null && lobby is Map<*, *> && user != null) {
                if (!lobby.containsKey("player_2")) {
                    myRef.child(lobbyCode).child("player_2").setValue(user.uid)
                    database.getReference("users")
                            .child(user.uid).child(lobbyCode).setValue(true)
                    Gdx.app.log("Firebase", "Success joining lobby")
                    screen.errorMessage("Success")
                } else {
                    screen.errorMessage("Lobby is full")
                }
            } else {
                screen.errorMessage("Lobby do not exist")
            }
        }.addOnFailureListener {
            screen.errorMessage("Something went wrong, not connected to server")
            Gdx.app.log("Firebase", "Error getting data", it)
        }
    }


}