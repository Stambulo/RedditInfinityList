package com.stambulo.redditinfinitylist.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.stambulo.redditinfinitylist.databinding.ActivityMainBinding
import com.stambulo.redditinfinitylist.model.entity.DataX
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: RedditViewModel by viewModels()
    private var _binding: ActivityMainBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {PagingAdapter()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupViewModel()
        observeViewModel()
    }

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            viewModel.redditIntent.send(RedditIntent.FetchNews)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.redditState.collect {
                when (it) {
                    is RedditState.NewsSuccess -> { renderSuccess(it.success)}
                    is RedditState.Loading -> { renderLoading() }
                    is RedditState.Error -> { renderError(it) }
                    else -> {}
                }
            }
        }
    }

    private fun renderLoading() {
        binding.recyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderSuccess(success: Flow<PagingData<DataX>>) {
        success.let {
            lifecycleScope.launchWhenCreated {
                it.collectLatest { adapter.submitData(it) }
            }
        }
        binding.recyclerView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun renderError(e: RedditState.Error) {
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
    }
}
