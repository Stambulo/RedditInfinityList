package com.stambulo.redditinfinitylist.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stambulo.redditinfinitylist.repository.RedditRepoImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Delay
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RedditViewModel @Inject constructor(private val repository: RedditRepoImp): ViewModel(){

    val redditIntent = Channel<RedditIntent>(Channel.UNLIMITED)
    private val _redditState = MutableStateFlow<RedditState>(RedditState.Idle)
    val redditState: StateFlow<RedditState> get() = _redditState

    init { handleIntent() }

    private fun handleIntent() {
        viewModelScope.launch {
            redditIntent.consumeAsFlow().collect {
                when (it) {is RedditIntent.FetchNews -> fetchNews()}
            }
        }
    }

    private fun fetchNews() {
        _redditState.value = RedditState.Loading
        viewModelScope.launch {
            delay(800)   // ProgressBar demonstration
            try {
                _redditState.value = RedditState.NewsSuccess(repository.getPosts())
            } catch (e: Exception){_redditState.value = RedditState.Error(e.localizedMessage)}
        }
    }
}
