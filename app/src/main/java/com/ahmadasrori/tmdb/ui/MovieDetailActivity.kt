package com.ahmadasrori.tmdb.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmadasrori.tmdb.R
import com.ahmadasrori.tmdb.adapter.ReviewAdapter
import com.ahmadasrori.tmdb.adapter.TrailerAdapter
import com.ahmadasrori.tmdb.data.model.*
import com.ahmadasrori.tmdb.data.remote.ApiResponse
import com.ahmadasrori.tmdb.databinding.ActivityMovieDetailBinding
import com.ahmadasrori.tmdb.helper.Constant
import com.ahmadasrori.tmdb.helper.Constant.MOVIEID
import com.ahmadasrori.tmdb.helper.Constant.URL_IMAGE
import com.ahmadasrori.tmdb.helper.showToast
import com.ahmadasrori.tmdb.viewmodel.MainViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private val binding: ActivityMovieDetailBinding by lazy {
        ActivityMovieDetailBinding.inflate(layoutInflater)
    }
    private val listReview = ArrayList<ResultsItemReview>()
    private val listTrailer = ArrayList<ResultsItemTrailer>()
    private var movieId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        val args = intent.extras
        movieId = args?.getString(MOVIEID)
        getMovieDetail(movieId!!.toInt())
        getReviews(movieId!!.toInt())
        getTrailers(movieId!!.toInt())
    }

    @SuppressLint("SetTextI18n")
    private fun getMovieDetail(movieId: Int) {
        mainViewModel.getMovieDetail(movieId).observe(this) { response ->
            when(response) {
                is ApiResponse.Loading -> {
                    showToast(R.string.state_loading)
                }
                is ApiResponse.Success -> {
                    binding.tvTitle.text = response.data.title
                    binding.tvReleaseDate.text = "release date : "+response.data.releaseDate
                    binding.tvDuration.text = "vote average : "+response.data.voteAverage
                    binding.tvDescription.text = response.data.overview
                    Glide.with(this)
                        .load("$URL_IMAGE${response.data.posterPath}")
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.ivMovie)
                }
                is ApiResponse.Error -> {
                    showToast(R.string.state_error)
                }
            }
        }
    }

    private fun getReviews(movieId: Int) {
        mainViewModel.getReviews(movieId).observe(this) { response ->
            when(response) {
                is ApiResponse.Loading -> {
                    showToast(R.string.state_loading)
                }
                is ApiResponse.Success -> {
                    response.let {
                        if (response.data.results!!.isEmpty()) {
                            binding.tvTitleReview.text = getString(R.string.no_reviews)
                        } else {
                            listReview.addAll(response.data.results)
                            initListReview()
                        }
                    }
                }
                is ApiResponse.Error -> {
                    showToast(R.string.state_error)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initListReview() {
        val adapter = ReviewAdapter(listReview)
        binding.rvItemReview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvItemReview.adapter = adapter
        binding.rvItemReview.isNestedScrollingEnabled = false
        binding.rvItemReview.adapter?.notifyDataSetChanged()
    }

    private fun getTrailers(movieId: Int) {
        mainViewModel.getTrailers(movieId).observe(this) { response ->
            when(response) {
                is ApiResponse.Loading -> {
                    showToast(R.string.state_loading)
                }
                is ApiResponse.Success -> {
                    response.let {
                        if (response.data.results!!.isEmpty()) {
                            binding.tvTitleReview.text = getString(R.string.no_trailers)
                        } else {
                            listTrailer.addAll(response.data.results)
                            initListTrailer()
                        }

                    }
                }
                is ApiResponse.Error -> {
                    showToast(R.string.state_error)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initListTrailer() {
        val adapter = TrailerAdapter(this, listTrailer) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constant.URL_YOUTUBE+listTrailer[it].key)))
        }
        binding.rvItemTrailer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvItemTrailer.adapter = adapter
        binding.rvItemTrailer.isNestedScrollingEnabled = false
        binding.rvItemTrailer.adapter?.notifyDataSetChanged()
    }
}