package com.stambulo.redditinfinitylist.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.stambulo.redditinfinitylist.model.entity.DataX
import com.stambulo.redditinfinitylist.repository.RedditRepoImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RedditViewModel @Inject constructor(private val repository: RedditRepoImp): ViewModel(){

    val redditIntent = Channel<RedditIntent>(Channel.UNLIMITED)
    private val _redditState = MutableStateFlow<RedditState>(RedditState.Idle)
    val redditState: StateFlow<RedditState> get() = _redditState

    init { handleIntent() }

    @OptIn(ExperimentalPagingApi::class)
    fun getPosts(): Flow<PagingData<DataX>>{
        return repository.getPosts().cachedIn(viewModelScope)
    }

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
            try {
                _redditState.value = RedditState.NewsSuccess(repository.getPosts())
            } catch (e: Exception){_redditState.value = RedditState.Error(e.localizedMessage)}
        }
    }
}
