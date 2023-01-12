package com.ahmadasrori.tmdb.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.ahmadasrori.tmdb.adapter.MovieAdapter
import com.ahmadasrori.tmdb.data.model.*
import com.ahmadasrori.tmdb.data.remote.ApiResponse
import com.ahmadasrori.tmdb.data.repository.MainRepository
import com.ahmadasrori.tmdb.helper.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mainViewModel: MainViewModel

    @Mock
    private lateinit var mainRepository: MainRepository

    @Before
    fun setup() {
        mainViewModel = MainViewModel(mainRepository)
    }

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when Get Movie Should Not Null and Return Success`() = mainDispatcherRule.runBlockingTest {
        val dummyStory = DataDummy.generateDummyMovieResponse()
        val data = MoviePagingSource.snapshot(dummyStory)
        val expectedMovie = MutableLiveData<PagingData<ResultItem>>()

        expectedMovie.value = data

        Mockito.`when`(mainRepository.getMovie()).thenReturn(expectedMovie.asFlow())

        val actualMovie = mainViewModel.getMovie().getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = MovieAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )

        differ.submitData(actualMovie)
        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyStory, differ.snapshot())
        Assert.assertEquals(dummyStory.size, differ.snapshot().size)
        Assert.assertEquals(dummyStory[0].id, differ.snapshot()[0]?.id)
    }

    @Test
    fun `when Get Trailers Should Not Null and Return Success`() = mainDispatcherRule.runBlockingTest {
        val dummyStory = DataDummy.generateDummyTrailersResponse()
        val data = MutableLiveData<ApiResponse<TrailerVideoResponse>>()
        data.value = ApiResponse.Success(dummyStory)

        Mockito.`when`(mainRepository.getTrailers(DataDummy.generatedDummyMovieId())).thenReturn(data.asFlow())

        val actualTrailers = mainViewModel.getTrailers(DataDummy.generatedDummyMovieId()).getOrAwaitValue()

        Assert.assertNotNull(actualTrailers)
        Assert.assertTrue(actualTrailers is ApiResponse.Success)
    }

    @Test
    fun `when Get Reviews Should Not Null and Return Success`() = mainDispatcherRule.runBlockingTest {
        val dummyStory = DataDummy.generateDummyReviewsResponse()
        val data = MutableLiveData<ApiResponse<ReviewResponse>>()
        data.value = ApiResponse.Success(dummyStory)

        Mockito.`when`(mainRepository.getReviews(DataDummy.generatedDummyMovieId())).thenReturn(data.asFlow())

        val actualReviews = mainViewModel.getReviews(DataDummy.generatedDummyMovieId()).getOrAwaitValue()

        Assert.assertNotNull(actualReviews)
        Assert.assertTrue(actualReviews is ApiResponse.Success)
    }

    @Test
    fun `when Get MovieDetail Should Not Null and Return Success`() = mainDispatcherRule.runBlockingTest {
        val dummyStory = DataDummy.generateDummyMovieDetailResponse()
        val data = MutableLiveData<ApiResponse<MovieDetailResponse>>()
        data.value = ApiResponse.Success(dummyStory)

        Mockito.`when`(mainRepository.getMovieDetail(DataDummy.generatedDummyMovieId())).thenReturn(data.asFlow())

        val actualMovieDetail = mainViewModel.getMovieDetail(DataDummy.generatedDummyMovieId()).getOrAwaitValue()

        Assert.assertNotNull(actualMovieDetail)
        Assert.assertTrue(actualMovieDetail is ApiResponse.Success)
    }

    private val noopListUpdateCallback  = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }

}