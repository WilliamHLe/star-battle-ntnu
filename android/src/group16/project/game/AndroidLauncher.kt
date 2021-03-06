package group16.project.game

import com.badlogic.gdx.backends.android.AndroidApplication
import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration

class AndroidLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize(StarBattle(AndroidFirebaseConnection()), AndroidApplicationConfiguration().apply {
            useWakelock = true
        })
    }
}