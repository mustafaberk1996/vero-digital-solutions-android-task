package com.example.verodigitalsolutionandroidtask.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.verodigitalsolutionandroidtask.domain.model.Task
import com.example.verodigitalsolutionandroidtask.ui.RefreshWorker
import com.example.verodigitalsolutionandroidtask.ui.component.EmptyState
import com.example.verodigitalsolutionandroidtask.ui.component.ErrorState
import com.example.verodigitalsolutionandroidtask.ui.component.LoadingState
import com.example.verodigitalsolutionandroidtask.ui.theme.VeroDigitalSolutionAndroidTaskTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        startRefreshWorker()
        setContent {

            VeroDigitalSolutionAndroidTaskTheme {
                val viewModel: MainViewModel = hiltViewModel()
                val state: MainUiState by viewModel.uiState.collectAsState()
                val query:String by viewModel.query.collectAsState()
                val lastFetchTime by viewModel.lastFetchTime.collectAsState(null)
                val lastFetchType by viewModel.lastFetchType.collectAsState(null)

                LaunchedEffect(state) {
                    if(state.logOut){
                        finish()
                    }
                }


                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainContent(
                        modifier = Modifier.padding(innerPadding),
                        state = state,
                        lastFetchTime = lastFetchTime,
                        lastFetchType = lastFetchType,
                        onLogoutButtonClicked = {
                            viewModel.logout()
                        },
                        onQueryChanged = {
                            viewModel.onQueryChanged(it)
                        },
                        query = query,
                        onRefresh = {
                            viewModel.onRefresh()
                        }
                    )
                }
            }
        }
    }

    private fun startRefreshWorker() {
        val refreshRequest = PeriodicWorkRequestBuilder<RefreshWorker>(15, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "refresh_worker",
            ExistingPeriodicWorkPolicy.REPLACE,
            refreshRequest
        )
        Timber.d("Refresh worker started")
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    state: MainUiState,
    onLogoutButtonClicked: () -> Unit,
    onQueryChanged: (String) -> Unit,
    onRefresh: () -> Unit,
    query: String,
    lastFetchTime: String? = null,
    lastFetchType:String? = null
) {
    Box(
        modifier = modifier
    ){
        Column {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Column{
                    if (!lastFetchTime.isNullOrBlank()) {
                        Text("Last update: $lastFetchTime")
                    }
                    if (!lastFetchType.isNullOrBlank()) {
                        Text("Fetch Type: $lastFetchType")
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = { onLogoutButtonClicked() }) {
                    Text("Logout")
                }
            }

            if (!state.data.isEmpty() || query.isNotEmpty()) {
                SearchBar(
                    query = query,
                    onQueryChanged = onQueryChanged,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            when {
                state.isLoading -> LoadingState()
                state.error != null -> ErrorState(message = state.error, onRefresh)
                state.data.isEmpty() && state.query.isBlank()  -> EmptyState()
                else ->  TaskListContent(
                    tasks = state.data,
                    isRefreshing = state.isLoading,
                    onRefresh = onRefresh
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListContent(
    modifier: Modifier = Modifier,
    tasks: List<Task>,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
                modifier = modifier
            ) {
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
