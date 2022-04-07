package group16.project.game

import android.app.Activity
import com.badlogic.gdx.Gdx
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ChildEventListener

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

import group16.project.game.models.FirebaseInterface
import group16.project.game.models.GameState
import group16.project.game.views.JoinLobbyScreen
import group16.project.game.views.HighScoreScreen


class AndroidFirebaseConnection : FirebaseInterface, Activity() {
    //the link should may be stored in a separate file, be put here through a reference.
    private val database = Firebase.database("https://tdt4240-battlestar-default-rtdb.europe-west1.firebasedatabase.app/")
    private var auth: FirebaseAuth = Firebase.auth

    /**
     * Sign in the user anonymously, is it is a new user add user to db
     */
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
                        //If user do not exist add user to db with userName
                        myRef.child(currentUser.uid).get().addOnSuccessListener {
                            if (it.value == null) {
                                val userName = "user-" + currentUser.uid
                                myRef.child(currentUser.uid).child("userName").setValue(userName)
                                Gdx.app.log("Firebase", "Success on crating user")
                            } else {
                                Gdx.app.log("Firebase", "Success user already exist")
                            }
                        }.addOnFailureListener { //Failure, not connected to db
                            Gdx.app.log("Firebase", "Error getting data", it)
                        }
                    }
                } else {
                    //Failure, could not sign in user
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

    /**
     * Create new lobby with lobby name.
     */
    override fun createLobby(lobbyName: String): String {
        var myRef: DatabaseReference = database.getReference("lobbies")
        //Creates a random lobby code with letters and numbers (6 char code)
        var randomLobbyCode: String
        //https://stackoverflow.com/questions/46943860/idiomatic-way-to-generate-a-random-alphanumeric-string-in-kotlin
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        randomLobbyCode = (1..6)
                .map { allowedChars.random() }
                .joinToString("")
        val user = auth.currentUser

        myRef.child(randomLobbyCode).get().addOnSuccessListener {
            //If lobby with the randomly created lobby code do not exists create lobby
            //and user logged in
            if (it.value == null && user != null) {
                //Set name of lobby and host with the user id
                myRef.child(randomLobbyCode).child("name").setValue(lobbyName)
                myRef.child(randomLobbyCode).child("host").setValue(user.uid)
                // Adds newly created lobby to user in db
                database.getReference("users").child(user.uid).child(randomLobbyCode).setValue(true)
                Gdx.app.log("Firebase", "Success on creating lobby")
            }
            //If lobby exists or user not logged in try again
            else {
                createLobby(lobbyName)
            }
        }.addOnFailureListener {
            //Failure, could not connect to db
            Gdx.app.log("Firebase", "Error getting data", it)
        }
        return randomLobbyCode
    }

    /**
     * Join lobby with lobby code
     * Screen is for sending error message to the user
     */
    override fun joinLobby(lobbyCode: String, screen: JoinLobbyScreen): String{
        var returnString = lobbyCode
        var myRef: DatabaseReference = database.getReference("lobbies")
        val user = auth.currentUser
        myRef.child(lobbyCode).get().addOnSuccessListener {
            var lobby = it.value
            //If user no logged in try again
            if (user == null) {
                joinLobby(lobbyCode, screen)
            }
            else if (lobby != null && lobby is Map<*, *> ) {
                //If lobby exist and do not contain already a user add user to lobby
                if (!lobby.containsKey("player_2")) {
                    myRef.child(lobbyCode).child("player_2").setValue(user.uid)
                    //Add lobby to user
                    database.getReference("users")
                            .child(user.uid).child(lobbyCode).setValue(true)
                    Gdx.app.log("Firebase", "Success joining lobby")
                    //Sendig success message to user, also enabled to change screen
                    screen.errorMessage("Success")
                } else {
                    //Sending error messge to user
                    screen.errorMessage("Lobby is full")
                }
            } else {
                //Sending error message to user
                screen.errorMessage("Lobby do not exist")
            }
        }.addOnFailureListener {
            //Not connected to db
            //Sending error message to user
            screen.errorMessage("Something went wrong, not connected to server")
            Gdx.app.log("Firebase", "Error getting data", it)
            returnString = "error"
        }
        return returnString
    }

    /**
     * Create listener for highScore, update highScoreScreen when top 10 change og score change
     */
    override fun getHighScoreListner(screen: HighScoreScreen) {
        val myTopScore = database.getReference("score")
            .orderByValue().limitToFirst(10)
        Gdx.app.log("Firebase", "On child change ${myTopScore}")

        myTopScore.addChildEventListener(object : ChildEventListener {
            // [START_EXCLUDE]
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                Gdx.app.log("Firebase", "On child dded  ${dataSnapshot} ${s}")
                val userName = dataSnapshot.getKey().toString()
                val score = Integer.parseInt(dataSnapshot.getValue().toString())

                if (userName != null && userName is String && score != null && score is Int) {
                    screen.childMoved(userName, score)
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
                Gdx.app.log("Firebase", "on child change ${dataSnapshot} ${s}")
                val userName = dataSnapshot.getKey().toString()
                val score = Integer.parseInt(dataSnapshot.getValue().toString())

                if (userName != null && userName is String && score != null && score is Int) {
                    screen.childMoved(userName, score)
                }
            }
            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                Gdx.app.log("Firebase", "On child removed ${dataSnapshot}" )
                if (dataSnapshot.getKey() is String) {
                }
                val userName = dataSnapshot.getKey().toString()

                if (userName != null && userName is String) {
                    screen.userRemoved(userName)
                }
            }
            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {
            //Do not think we need to update when child move since it also changes
            /*Gdx.app.log("Firebase", "On child moved ${dataSnapshot} ${s}")
                val userName = dataSnapshot.getKey().toString()
                val score = Integer.parseInt(dataSnapshot.getValue().toString())

                if (userName != null && userName is String && score != null && score is Int) {
                    screen.childMoved(userName, score)
                }*/
            }
            override fun onCancelled(databaseError: DatabaseError) {}
            // [END_EXCLUDE]
        })

    }

    override fun updateCurrentGameState(lobbyCode: String, state: GameState) {
        var myRef: DatabaseReference = database.getReference("lobbies")


        //var updatingUser = auth.currentUser
        //var host = myRef.child(lobbyCode).child("host").get()
        //TODO: should may verify before changing



        myRef.child(lobbyCode).get().addOnSuccessListener {
            if(it.value!==null) {
                myRef.child(lobbyCode).child("current_gamestate").setValue(state.state)
            }
        }

    }

    override fun setPlayersChoice(lobbyCode: String, position: Int, targetPostion: Int) {
        var myRef: DatabaseReference = database.getReference("lobbies")
        var playerType="null"
        var playerID = auth.currentUser?.uid

        myRef.child(lobbyCode).get().addOnSuccessListener {
                println("1")
            if(it.value != null)
            {
                println("2")
                if(it.child("host").value== playerID) playerType = "host";
                if(it.child("player_2").value== playerID) playerType = "player_2";
                println(playerType)

                if (playerType!= "null") {

                myRef.child(lobbyCode).child(playerType).child("position").setValue(position)
                myRef.child(lobbyCode).child(playerType).child("target_position").setValue(targetPostion)
                }

            }
        }


    }

    override fun playerIsReadyToFire(lobbyCode: String) {
        var myRef: DatabaseReference = database.getReference("lobbies")
        var playerType="null"
        var playerID = auth.currentUser?.uid

        myRef.child(lobbyCode).get().addOnSuccessListener {
            if(it.value != null)
            {
                if(it.child("host").value== playerID) playerType = "host";
                if(it.child("player_2").value== playerID) playerType = "player_2";

                if (playerType!= "null") {

                    myRef.child(lobbyCode).child(playerType).child("ready_to_fire").setValue(true)
                }


            }
        }


    }

    override fun bothPLayersAreReady(lobbyCode: String): Boolean {
        var myRef: DatabaseReference = database.getReference("lobbies")
        var ready = false

        myRef.child(lobbyCode).get().addOnSuccessListener {
            if(it.value != null)
            {
                println(it.child("host").child("ready_to_fire").value)
                println(it.child("player_2").child("ready_to_fire").value)
                if(it.child("host").child("ready_to_fire").value== "true" && it.child("player_2").child("ready_to_fire").value== "true") ready = true;

            }
        }
        return ready

    }



}