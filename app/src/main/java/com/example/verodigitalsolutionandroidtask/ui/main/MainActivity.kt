package com.example.verodigitalsolutionandroidtask.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.verodigitalsolutionandroidtask.domain.Task
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainContent(
                        modifier = Modifier.padding(innerPadding),
                        state = state
                    )

                }
            }
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier, state: MainUiState) {
    Box(
        modifier = modifier
    ){

        when(state){
            is MainUiState.Loading -> {
                Text("loading")
            }
            is MainUiState.TaskList -> {
                TaskList(tasks = state.taskList)
            }
            is MainUiState.Error -> {
                Text("error")
            }
            is MainUiState.Empty -> {
                Text("empty")
            }
            is MainUiState.Idle -> {

            }
        }


    }
}

@Composable
fun TaskList(modifier: Modifier = Modifier, tasks:List<Task>) {
    LazyColumn{
        items(tasks){item->
            Row {
                Text(item.title)
                Text(item.colorCode)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VeroDigitalSolutionAndroidTaskTheme {
        Greeting("Android")
    }
}