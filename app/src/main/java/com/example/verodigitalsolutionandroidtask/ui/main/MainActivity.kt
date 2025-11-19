package com.example.verodigitalsolutionandroidtask.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.verodigitalsolutionandroidtask.domain.Task
import com.example.verodigitalsolutionandroidtask.ui.component.EmptyState
import com.example.verodigitalsolutionandroidtask.ui.component.ErrorState
import com.example.verodigitalsolutionandroidtask.ui.component.LoadingState
import com.example.verodigitalsolutionandroidtask.ui.theme.VeroDigitalSolutionAndroidTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            VeroDigitalSolutionAndroidTaskTheme {
                val viewModel: MainViewModel = hiltViewModel()
                val state: MainUiState by viewModel.uiState.collectAsState(MainUiState.Idle)


                LaunchedEffect(state) {
                    if(state == MainUiState.Logout){
                        finish()
                    }
                }


                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainContent(
                        modifier = Modifier.padding(innerPadding),
                        state = state,
                        onLogoutButtonClicked = {
                            viewModel.logout()
                        }
                    )

                }
            }
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier, state: MainUiState, onLogoutButtonClicked: () -> Unit) {
    Box(
        modifier = modifier
    ){
        Column {
            Button(onClick = { onLogoutButtonClicked() }) {
                Text("Logout")
            }

            when(state){
                is MainUiState.Loading -> {
                    LoadingState()
                }
                is MainUiState.TaskList -> {
                    TaskList(tasks = state.taskList)
                }
                is MainUiState.Error -> {
                    ErrorState(message = state.throwable?.localizedMessage.orEmpty(), {})
                }
                is MainUiState.Empty -> {
                    EmptyState()
                }
                is MainUiState.Idle -> {}
                is MainUiState.Logout -> {}
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}


@Composable
fun TaskList(modifier: Modifier = Modifier, tasks:List<Task>) {
    LazyColumn{
        items(tasks){item->
            TaskRow(item)
        }
    }
}