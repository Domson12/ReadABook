package eu.tuto.readabook.screens.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import eu.tuto.readabook.model.MUser
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginScreenViewModel : ViewModel() {
    //val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        context: Context,
        home: () -> Unit
    ) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Timber.tag("FB").d("signInWithEmailAndPassword: %s", task.result)
                            home()
                        } else {
                            Toast.makeText(
                                context,
                                "Login failed! Check your email and password.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            } catch (e: Exception) {
                Timber.tag("FB").d("signInWithEmailAndPassword: %s", e.message)
            }
        }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        context: Context,
        home: () -> Unit
    ) {

        if (_loading.value == false) {
            _loading.value = true


            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val displayName = task.result?.user?.email?.split('@')?.get(0)
                        createUser(displayName)
                        home()
                        Timber.tag("FB").d("createUserWithEmailAndPassword: %s", task.result)
                    } else {
                        Toast.makeText(
                            context,
                            "Oops...Something went wrong! Check your credentials",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                    _loading.value = false
                }

        }
    }


    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user = MUser(
            id = null,
            userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = "",
            quote = "Life is great",
            profession = "Android Developer"
        ).toMap()


        FirebaseFirestore.getInstance().collection("users")
            .add(user)
    }
}