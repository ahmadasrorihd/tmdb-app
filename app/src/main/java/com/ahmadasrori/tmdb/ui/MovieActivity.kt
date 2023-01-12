package com.ahmadasrori.tmdb.ui

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.ahmadasrori.tmdb.R
import com.ahmadasrori.tmdb.adapter.MovieAdapter
import com.ahmadasrori.tmdb.data.model.ResultItem
import com.ahmadasrori.tmdb.databinding.ActivityMovieBinding
import com.ahmadasrori.tmdb.helper.Constant
import com.ahmadasrori.tmdb.helper.isTrue
import com.ahmadasrori.tmdb.helper.showToast
import com.ahmadasrori.tmdb.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalPagingApi
@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private var doubleBackToExitPressedOnce = false

    private val binding: ActivityMovieBinding by lazy {
        ActivityMovieBinding.inflate(layoutInflater)
    }
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initAdapter()
    }

    private fun initView() {
        binding.rvMovie.layoutManager = GridLayoutManager(this, 2)
        binding.rvMovie.layoutManager?.onRestoreInstanceState(binding.rvMovie.layoutManager?.onSaveInstanceState())

        mainViewModel.getMovie().observe(this) { stories ->
            initRecyclerViewUpdate(stories)
        }
    }

    private fun initAdapter() {
        adapter = MovieAdapter {
            if (isOnline()) {
                val intent = Intent(this, MovieDetailActivity::class.java)
                intent.putExtra(Constant.MOVIEID, it.id)
                startActivity(intent)
            } else {
                showToast(R.string.state_no_network)
            }
        }
        binding.rvMovie.layoutManager?.onRestoreInstanceState(binding.rvMovie.layoutManager?.onSaveInstanceState())
        binding.rvMovie.adapter = adapter
        lifecycleScope.launch {
            adapter.loadStateFlow.distinctUntilChanged { old, new ->
                old.mediator?.prepend?.endOfPaginationReached.isTrue() == new.mediator?.prepend?.endOfPaginationReached.isTrue()
            }
                .filter { it.refresh is LoadState.NotLoading && it.prepend.endOfPaginationReached && !it.append.endOfPaginationReached }
                .collect {
                    binding.rvMovie.scrollToPosition(0)
                }
        }
        adapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {
                    binding.apply {
                        bgDim.visibility = View.VISIBLE
                        pgBar.visibility = View.VISIBLE
                    }
                }
                is LoadState.Error -> {
                    binding.apply {
                        bgDim.visibility = View.GONE
                        pgBar.visibility = View.GONE
                    }
                    showToast(R.string.state_error)
                }
                else -> {
                    binding.apply {
                        bgDim.visibility = View.GONE
                        pgBar.visibility = View.GONE
                    }
                    Timber.e(getString(R.string.unknown_state))
                }
            }
        }
    }

    private fun initRecyclerViewUpdate(storiesData: PagingData<ResultItem>) {
        val recyclerViewState = binding.rvMovie.layoutManager?.onSaveInstanceState()
        adapter.submitData(lifecycle, storiesData)
        binding.rvMovie.layoutManager?.onRestoreInstanceState(recyclerViewState)
    }


    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            val a = Intent(Intent.ACTION_MAIN)
            a.addCategory(Intent.CATEGORY_HOME)
            a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(a)
        }
        this.doubleBackToExitPressedOnce = true
        showToast(R.string.double_click_to_exit)
        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    private fun isOnline(): Boolean {
        val activeNetworkInfo =
            (getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return activeNetworkInfo != null &&
                activeNetworkInfo.isConnectedOrConnecting
    }

    override fun onResume() {
        super.onResume()
        if (!isOnline()) {
            showToast(R.string.state_no_network)
        }
    }

}