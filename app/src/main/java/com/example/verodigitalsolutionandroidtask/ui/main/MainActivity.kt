package com.example.verodigitalsolutionandroidtask.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                val filteredTasks: List<Task> by viewModel.filteredTasks.collectAsState(emptyList())
                val query:String by viewModel.query.collectAsState()

                LaunchedEffect(state) {
                    if(state == MainUiState.Logout){
                        finish()
                    }
                }


                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainContent(
                        modifier = Modifier.padding(innerPadding),
                        state = state,
                        filteredTasks = filteredTasks,
                        onLogoutButtonClicked = {
                            viewModel.logout()
                        },
                        onQueryChanged = {
                            viewModel.onQueryChanged(it)
                        },
                        query = query
                    )

                }
            }
        }
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    state: MainUiState,
    filteredTasks:List<Task> = emptyList(),
    onLogoutButtonClicked: () -> Unit,
    onQueryChanged: (String) -> Unit,
    query: String
) {
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
                    TaskListContent(tasks = filteredTasks, onQueryChanged = onQueryChanged, query = query)
                }
                is MainUiState.Error -> {
                    ErrorState(message = state.throwable?.localizedMessage.orEmpty(), {})
                }
                is MainUiState.Idle -> {}
                is MainUiState.Logout -> {}
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}


@Composable
fun TaskListContent(modifier: Modifier = Modifier, tasks: List<Task>, onQueryChanged: (String) -> Unit = {}, query: String) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            SearchBar(
                query = query,
                onQueryChanged = onQueryChanged,
                modifier = Modifier
                    .fillMaxWidth()
            )

            if (!tasks.isEmpty()) {
                LazyColumn {
                    items(tasks) { item ->
                        TaskRow(item)
                    }
                }
            } else {
                EmptyState()
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search..."
) {
    TextField(
        value = query,
        onValueChange = onQueryChanged,
        placeholder = { Text(placeholder) },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChanged("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear"
                    )
                }
            }
        },
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    )
}
