package com.example.dictionaryapp.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.domain.repository.WordInfoRepository
import com.example.dictionaryapp.util.CustomExceptions
import com.example.dictionaryapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordInfoViewModel @Inject constructor(
    private val repository: WordInfoRepository
): ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _state = mutableStateOf(WordInfoState())
    val state: State<WordInfoState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    fun onSearch(query: String){
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            repository.getWordInfo(query)
                .onEach { result ->
                    when(result){
                        is Resource.Success ->{
                            _state.value = state.value.copy(
                                wordInfoItems = result.data ?: emptyList(),
                                loading = false
                            )
                        }
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                wordInfoItems = result.data ?: emptyList(),
                                loading = false
                            )
                            _eventFlow.emit(UIEvent.ShowSnackbar(
                                result.exception ?: CustomExceptions.UnknownException()
                            ))
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                wordInfoItems = result.data ?: emptyList(),
                                loading = true
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    sealed class UIEvent{
        data class ShowSnackbar(val exception: Exception): UIEvent()
    }
}