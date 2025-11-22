package com.example.verodigitalsolutionandroidtask.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.verodigitalsolutionandroidtask.domain.model.FetchType
import com.example.verodigitalsolutionandroidtask.domain.model.Task
import com.example.verodigitalsolutionandroidtask.ui.worker.RefreshWorker
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
        setContent {

            VeroDigitalSolutionAndroidTaskTheme {
                val viewModel: MainViewModel = hiltViewModel()
                val state: MainUiState by viewModel.uiState.collectAsState()
                val query:String by viewModel.query.collectAsState()
                val lastFetchTime by viewModel.lastFetchTime.collectAsState(null)
                val lastFetchType by viewModel.lastFetchType.collectAsState(null)
                var openQrCodeScannerScreen by rememberSaveable { mutableStateOf(false) }
                var showExitAlertDialog by rememberSaveable { mutableStateOf(false) }

                LaunchedEffect(state) {
                    if(state.logOut){
                        finish()
                    }
                }


                BackHandler {
                    if (openQrCodeScannerScreen) {
                        openQrCodeScannerScreen = false
                    }else{
                       showExitAlertDialog = !showExitAlertDialog
                    }
                }

                if (showExitAlertDialog){
                    AlertDialog(
                        onDismissRequest = {showExitAlertDialog = false},
                        title = {
                            Text(text = "Exit App")
                        },
                        text = {
                            Text(text = "Do you really want to exit?")
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                viewModel.logout()
                            }) {
                                Text("Yes")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                showExitAlertDialog = false
                            }) {
                                Text("Cancel")
                            }
                        }
                    )
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box{
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
                            onRefresh = {fetchType->
                                viewModel.onRefresh(fetchType)
                            },
                            onQrCodeScannerClicked = {
                                openQrCodeScannerScreen = true
                            }
                        )
                        if (openQrCodeScannerScreen) {
                            QrCodeScannerScreen(modifier = Modifier.padding(innerPadding),
                                onCloseClicked = {
                                openQrCodeScannerScreen = false
                            }, onQrScanned = {code->
                                openQrCodeScannerScreen = false
                                viewModel.onQueryChanged(code)}
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    state: MainUiState,
    onLogoutButtonClicked: () -> Unit,
    onQueryChanged: (String) -> Unit,
    onRefresh: (FetchType) -> Unit,
    query: String,
    lastFetchTime: String? = null,
    lastFetchType:String? = null,
    onQrCodeScannerClicked: () -> Unit
) {
    Box(
        modifier = modifier.padding(4.dp)
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


            if (state.error != null && state.error.code == 401) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "You session has been expired. Please try to re-login to the app.",
                        textAlign = TextAlign.Center
                    )
                }
                return
            }



            if (!state.data.isEmpty() || query.isNotEmpty()) {
                SearchBar(
                    query = query,
                    onQueryChanged = onQueryChanged,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            if(state.error != null && state.error.code != 401){
                ErrorState(message = state.error.message, onRetry = {onRefresh(FetchType.RETRY)})
            }

            when {
                state.isLoading -> LoadingState()
                state.data.isEmpty() && state.query.isBlank()  -> EmptyState()
                else ->  TaskListContent(
                    tasks = state.data,
                    isRefreshing = state.isLoading,
                    onRefresh = onRefresh,
                    onQrCodeScannerClicked = onQrCodeScannerClicked
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
    onRefresh: (FetchType) -> Unit,
    onQrCodeScannerClicked:()->Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = {onRefresh(FetchType.SWIPE)},
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

        FloatingActionButton(
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.BottomEnd),
            onClick = { onQrCodeScannerClicked() },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(Icons.Default.QrCode, contentDescription = "Scan Qr Code")
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
