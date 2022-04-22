package group16.project.game

import android.app.Activity
import com.badlogic.gdx.Gdx
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.ValueEventListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import group16.project.game.models.FirebaseInterface
import group16.project.game.models.Game
import group16.project.game.models.GameInfo
import group16.project.game.models.GameState
import group16.project.game.views.GameScreen
import group16.project.game.views.JoinLobbyScreen
import group16.project.game.views.HighScoreScreen

class AndroidFirebaseConnection : FirebaseInterface, Activity() {
    // The link should be stored in a separate file in the future, put here through a reference for now.
    private val database = Firebase.database("https://tdt4240-battlestar-default-rtdb.europe-west1.firebasedatabase.app/")
    private var auth: FirebaseAuth = Firebase.auth

    /**
     * Sign in the user anonymously, is it is a new user add user to db
     */
    override fun signInAnonymously() {
        // [START signin_anonymously]
        val myRef: DatabaseReference = database.getReference("users")
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

    private fun generateLobbyCode(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..6)
            .map { allowedChars.random() }
            .joinToString("")
    }

    /**
     * Create new lobby with lobby name.
     */
    override fun createLobby(lobbyName: String, gameController: StarBattle){
        val randomLobbyCode = generateLobbyCode()
        val myRef: DatabaseReference = database.getReference("lobbies").child(randomLobbyCode)
        //Creates a random lobby code with letters and numbers (6 char code)

        val user = auth.currentUser

        myRef.get().addOnSuccessListener {
            //If lobby with the randomly created lobby code do not exists create lobby
            //and user logged in
            if (it.value == null && user != null) {
                //Set name of lobby and host with the user id
                myRef.child("name").setValue(lobbyName)
                myRef.child("host").child("id").setValue(user.uid)
                myRef.child("host").child("lives").setValue(3)
                gameController.updateCurrentGame(randomLobbyCode, "host", "player_2")
                updateCurrentGameState(GameState.START)
                // Adds newly created lobby to user in db
                database.getReference("users").child(user.uid).child(randomLobbyCode).setValue(true)
                println(randomLobbyCode)
                Gdx.app.log("Firebase", "Success on creating lobby")
            }
            //If lobby exists or user not logged in try again
            else {
                createLobby(lobbyName, gameController)
            }
        }.addOnFailureListener {
            //Failure, could not connect to db
            Gdx.app.log("Firebase", "Error getting data", it)
        }
    }

    /**
     * Join lobby with lobby code
     * Screen is for sending error message to the user
     */
    override fun joinLobby(lobbyCode: String, screen: JoinLobbyScreen){
        val myRef: DatabaseReference = database.getReference("lobbies").child(lobbyCode)
        val user = auth.currentUser
        myRef.get().addOnSuccessListener {
            val lobby = it.value
            //If user no logged in try again
            if (user == null) {
                joinLobby(lobbyCode, screen)
            }
            else if (lobby != null && lobby is Map<*, *> ) {
                //If lobby exist and do not contain already a user add user to lobby
                if (!lobby.containsKey("player_2")) {
                    myRef.child("player_2").child("id").setValue(user.uid)
                    myRef.child("player_2").child("lives").setValue(3)
                    screen.gameController.updateCurrentGame(lobbyCode, "player_2", "host")
                    updateCurrentGameState(GameState.SETUP)
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
        }
    }

    /**
     * Create listener for highScore, update highScoreScreen when top 10 change og score change
     */
    override fun getHighScoreListener(screen: HighScoreScreen) {
        val myTopScore = database.getReference("score")
            .orderByValue().limitToLast(10)
        Gdx.app.log("Firebase", "On child change $myTopScore")

        myTopScore.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                Gdx.app.log("Firebase", "On child added  $dataSnapshot $s")
                val userName = dataSnapshot.key.toString()
                val score = Integer.parseInt(dataSnapshot.value.toString())

                if (userName != null && userName is String && score != null && score is Int) {
                    screen.updateHighscore(userName, score)
                }
            }
            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
                Gdx.app.log("Firebase", "on child change $dataSnapshot $s")
                val userName = dataSnapshot.key.toString()
                val score = Integer.parseInt(dataSnapshot.value.toString())

                if (userName != null && userName is String && score != null && score is Int) {
                    screen.updateHighscore(userName, score)
                }
            }
            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                Gdx.app.log("Firebase", "on child remove $dataSnapshot")
                val userName = dataSnapshot.key.toString()
                val score = Integer.parseInt(dataSnapshot.value.toString())

                if (userName != null && userName is String && score != null && score is Int) {
                    screen.deletePlayerFromHighScore(userName)
                }
            }
            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun updateScore(points: Int) {
        val updates: MutableMap<String, Any> = HashMap()
        val myRef: DatabaseReference = database.getReference("score")
        val user = auth.currentUser
        if (user != null) {
            val userName = "user-" + user.uid
            myRef.child(userName).get().addOnSuccessListener {
                val score = it.value
                if (score != null) {
                    if ((score as Long).toInt()+points >= 0) {
                        updates[userName] = ServerValue.increment(points * 1.0)
                        myRef.updateChildren(updates)
                    }else {
                        myRef.child(userName).setValue(0)
                    }
                } else if (points > 0) {
                    myRef.child(userName).setValue(points)
                }
            }
        }
    }


    override fun updateCurrentGameState(state: GameState) {
        val myRef: DatabaseReference = database.getReference("lobbies").child(GameInfo.currentGame).child("current_gamestate")
        println("State: ${state.name}")
        myRef.setValue(state.name)
        println("UPDATE GAME STATE: $state")
    }

    override fun setPlayersChoice(position: Int, targetPosition: Int, shieldPosition: Int, gameScreen: GameScreen) {
        val myRef: DatabaseReference = database.getReference("lobbies").child(GameInfo.currentGame)

        myRef.get().addOnSuccessListener {
            if(it.value != null) {
                myRef.child(GameInfo.player).child("position").setValue(position)
                myRef.child(GameInfo.player).child("target_position").setValue(targetPosition)
                myRef.child(GameInfo.player).child("shield_position").setValue(shieldPosition)
                myRef.child(GameInfo.player).child("ready_to_fire").setValue(true)
                println("YOU FIRE")
                fire(gameScreen)
            }
        }.addOnFailureListener {
            //Failure, could not connect to db
            Gdx.app.log("Firebase", "Error getting data", it)
        }
    }

    override fun getCurrentState(game: Game){
        val gameState: DatabaseReference = database.getReference("lobbies").child(GameInfo.currentGame).child("current_gamestate")

        gameState.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val currentState = dataSnapshot.value
                if (currentState != null) {
                    game.changeState(GameState.valueOf(currentState.toString()))
                    println("GET GAME STATE: ${GameState.valueOf(currentState.toString())}")
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun heartListener(player: String, screen: GameScreen){
        val health = database.getReference("lobbies").child(GameInfo.currentGame).child(player).child("lives")

        health.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val playerHealth = dataSnapshot.value
                if(playerHealth != null) {
                    println("HEART LISTENER: $player $playerHealth")
                    screen.updateHealth(player, Integer.parseInt(playerHealth.toString()))
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun reduceHeartsAmount() {
        val updates: MutableMap<String, Any> = HashMap()
        updates["${GameInfo.currentGame}/${GameInfo.player}/lives"] = ServerValue.increment(-1)
        database.getReference("lobbies").updateChildren(updates)
    }

    override fun removeShield() {
        val updates: MutableMap<String, Any> = HashMap()
        updates["${GameInfo.currentGame}/${GameInfo.player}/shield_position"] = -2
        updates["${GameInfo.currentGame}/${GameInfo.player}/shield_destroyed"] = true
        database.getReference("lobbies").updateChildren(updates)

    }

    override fun updatePlayerHealth(){
        val myRef: DatabaseReference = database.getReference("lobbies").child(GameInfo.currentGame)
        myRef.get().addOnSuccessListener {
            val opponentTargetPosition = (it.child(GameInfo.opponent).child("target_position").value as Long).toInt()
            val shieldPosition = (it.child(GameInfo.player).child("shield_position").value as Long).toInt()
            var shieldDestroyed = it.child(GameInfo.player).child("shield_destroyed").value
            if (shieldDestroyed != null) shieldDestroyed = shieldDestroyed as Boolean
            else shieldDestroyed = false
            val yourPosition = (it.child(GameInfo.player).child("position").value as Long).toInt()
            if (!shieldDestroyed && opponentTargetPosition == shieldPosition) {
                println("REMOVE SHIELD")
                removeShield()
            } else {
                if (opponentTargetPosition == yourPosition) {
                    println("UPDATE PLAYER HEALTH")
                    println(opponentTargetPosition)
                    println(yourPosition)
                    reduceHeartsAmount()
                }
            }

        }
    }

    override fun checkIfOpponentReady(screen: GameScreen) {
        val myRef: DatabaseReference = database.getReference("lobbies").child(GameInfo.currentGame).child(
            GameInfo.opponent).child("ready_to_fire")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val isReady = dataSnapshot.value
                if (isReady == true) {
                    println("ENEMY FIRE")
                    fire(screen)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun fire(screen : GameScreen) {
        val myRef: DatabaseReference = database.getReference("lobbies").child(GameInfo.currentGame)
        var bothHit = false

        myRef.get().addOnSuccessListener {
            if(it.value != null) {
                val host = it.child("host")
                val player2 = it.child("player_2")
                val hostReady = host.child("ready_to_fire").value == true
                val player2Ready = player2.child("ready_to_fire").value == true

                if(hostReady and player2Ready) {
                    val player1MovingTo = host.child("position").value.toString().toInt()
                    val player1Shooting = host.child("target_position").value.toString().toInt()
                    var player1Shields = host.child("shield_position").value
                    if (player1Shields !=  null) player1Shields = player1Shields.toString().toInt()
                    else player1Shields = -1

                    val player2MovingTo = player2.child("position").value.toString().toInt()
                    val player2Shooting = player2.child("target_position").value.toString().toInt()
                    var player2Shields = player2.child("shield_position").value
                    if (player2Shields !=  null) player2Shields = player2Shields.toString().toInt()
                    else player2Shields = -1

                    if (player1MovingTo == player2Shooting && player2MovingTo == player1Shooting) bothHit = true

                    if (GameInfo.player == "host") screen.bothReady(
                        player2MovingTo,
                        player2Shooting,
                        bothHit,
                        player2Shields
                    )
                    else screen.bothReady(
                        player1MovingTo,
                        player1Shooting,
                        bothHit,
                        player1Shields
                    )
                }
            }
        }.addOnFailureListener {
            //Failure, could not connect to db
            Gdx.app.log("Firebase", "Error getting data", it)
        }
    }

    override fun resetReady() {
        val myRef: DatabaseReference = database.getReference("lobbies").child(GameInfo.currentGame)

        myRef.get().addOnSuccessListener {
            if (it.value != null) {
                myRef.child("host").child("ready_to_fire").setValue(false)
                myRef.child("player_2").child("ready_to_fire").setValue(false)
            }
        }
    }

    override fun deleteLobby() {
        database.getReference("users").child(auth.currentUser!!.uid).child(GameInfo.currentGame).removeValue()
        database.getReference("lobbies").child(GameInfo.currentGame).removeValue()
    }
}