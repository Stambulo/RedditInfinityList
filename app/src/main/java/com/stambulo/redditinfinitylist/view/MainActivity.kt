package com.stambulo.redditinfinitylist.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.stambulo.redditinfinitylist.databinding.ActivityMainBinding
import com.stambulo.redditinfinitylist.model.entity.RedditJSON
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: RedditViewModel by viewModels()
    private var _binding: ActivityMainBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {RedditAdapter()}

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
                    is RedditState.NewsSuccess -> {
                        renderSuccess(it.success)
                    }
                    is RedditState.Loading -> {
                        renderLoading()
                    }
                    is RedditState.Error -> {
                        renderError(it)
                    }
                }
            }
        }
    }

    private fun renderLoading() {
        binding.recyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderSuccess(success: Response<RedditJSON>) {
        success.let {
            adapter.setData(success.body()!!.data.children)
            adapter.notifyDataSetChanged()
        }
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun renderError(e: RedditState.Error) {
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
    }
}
