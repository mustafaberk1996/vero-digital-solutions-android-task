package com.example.verodigitalsolutionandroidtask.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.verodigitalsolutionandroidtask.data.datastore.AppDataStore
import com.example.verodigitalsolutionandroidtask.domain.model.FetchType
import com.example.verodigitalsolutionandroidtask.domain.model.filter
import com.example.verodigitalsolutionandroidtask.domain.usecase.FetchTasks
import com.example.verodigitalsolutionandroidtask.domain.usecase.LogoutUseCase
import com.example.verodigitalsolutionandroidtask.domain.usecase.ObserveTasks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchTasks: FetchTasks,
    private val logoutUseCase: LogoutUseCase,
    private val observeTasksUseCase: ObserveTasks,
    private val appDataStore: AppDataStore
): ViewModel() {


    private val _query: MutableStateFlow<String> = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _uiState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState())
    val uiState: MutableStateFlow<MainUiState> = _uiState

    val lastFetchTime: Flow<String?> = appDataStore.lastFetchTimeFlow.map {
        it?.let {
            Date(it)
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(it))
        }.toString()
    }

    val lastFetchType: Flow<String?> = appDataStore.lastFetchTypeFlow

    init {
        observeTasks()
        fetchTaskList(FetchType.INITIAL_FETCH)
    }

    private fun observeTasks() {
        viewModelScope.launch {
            combine(observeTasksUseCase.invoke(), _query) { data, query ->
                if (query.isBlank()) data else data.filter { it.filter(query) }
            }.collect { filteredData ->
                _uiState.update { current ->
                    current.copy(
                        data = filteredData,
                        isLoading = false,
                        error = null
                    )
                }
            }
        }
    }

    private fun fetchTaskList(fetchType:FetchType) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                fetchTasks(fetchType)
                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "Unknown error") }
            }
        }
    }

    fun onQueryChanged(query: String){
        _query.value = query
        Timber.d("query changed: $query")
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            _uiState.update { current ->
                current.copy(
                    logOut = true
                )
            }

        }
    }

    fun onRefresh() {
        fetchTaskList(fetchType = FetchType.SWIPE)
    }

}