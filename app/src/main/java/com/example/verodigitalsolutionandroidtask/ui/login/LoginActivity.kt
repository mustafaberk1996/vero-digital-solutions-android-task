package com.example.verodigitalsolutionandroidtask.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.verodigitalsolutionandroidtask.ui.main.MainActivity
import com.example.verodigitalsolutionandroidtask.ui.theme.VeroDigitalSolutionAndroidTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VeroDigitalSolutionAndroidTaskTheme {

                val context = LocalContext.current
                val viewModel: LoginViewModel = hiltViewModel()
                val state: LoginUiState by viewModel.loginUiState.collectAsState(LoginUiState.Idle)

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginContent(
                        modifier = Modifier.padding(innerPadding),
                        state = state,
                        onLoginButtonClick = {
                            viewModel.loginButtonClicked()
                        })
                }

                LaunchedEffect(state) {
                    if (state is LoginUiState.Success) {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    }
                }
            }
        }
    }
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    state: LoginUiState,
    onLoginButtonClick: () -> Unit
) {

    Column(
        modifier = modifier,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        Button(modifier = Modifier.fillMaxWidth(),onClick = { onLoginButtonClick() }) {
            Text("Login")
        }
        when (state) {
            is LoginUiState.Idle -> {

            }

            is LoginUiState.Success -> {
                Text("Login is successfully, redirecting to the home page")
            }

            is LoginUiState.Error -> {
                Text("something went wrong")
            }

            is LoginUiState.Loading -> {
                Text("loading...")
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun LoginContentPreview() {
    VeroDigitalSolutionAndroidTaskTheme {
        LoginContent(modifier = Modifier, LoginUiState.Loading, onLoginButtonClick = {})
    }
}