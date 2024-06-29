package com.rba29.testcasecancreative.UI.Home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.rba29.testcasecancreative.Data.Repository.GamesRepository
import com.rba29.testcasecancreative.Data.Response.ListResultItem
import com.rba29.testcasecancreative.DataDummy
import com.rba29.testcasecancreative.MainDispatcherRule
import com.rba29.testcasecancreative.UI.Fragment.HomeFragment.GamesAdapter
import com.rba29.testcasecancreative.UI.Fragment.HomeFragment.HomeFragmentViewModel
import com.rba29.testcasecancreative.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var repo: GamesRepository

    @Test
    fun `when Get Stories Should Not Null and Return Data`() = runTest {
        val dummyQuote = DataDummy.generateDummyQuoteResponse()
        val data: PagingData<ListResultItem> = QuotePagingSource.snapshot(dummyQuote)
        val expectedQuote = MutableLiveData<PagingData<ListResultItem>>()
        expectedQuote.value = data

        Mockito.`when`(repo.getAllGames()).thenReturn(expectedQuote)
        val homeViewModel = HomeFragmentViewModel(repo)
        val actualQuote: PagingData<ListResultItem> = homeViewModel.listGames.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = GamesAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyQuote.size, differ.snapshot().size)
        Assert.assertEquals(dummyQuote[0], differ.snapshot()[0])
    }

    @Test
    fun `when Get Stories Empty Should Return No Data`() = runTest {
        val data: PagingData<ListResultItem> = PagingData.from(emptyList())
        val expectedQuote = MutableLiveData<PagingData<ListResultItem>>()
        expectedQuote.value = data

        Mockito.`when`(repo.getAllGames()).thenReturn(expectedQuote)
        val homeViewModel = HomeFragmentViewModel(repo)
        val actualQuote: PagingData<ListResultItem> = homeViewModel.listGames.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = GamesAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)
        Assert.assertEquals(0, differ.snapshot().size)
    }
}

class QuotePagingSource : PagingSource<Int, LiveData<List<ListResultItem>>>() {
    companion object {
        fun snapshot(items: List<ListResultItem>): PagingData<ListResultItem> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<ListResultItem>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<ListResultItem>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) { }
    override fun onRemoved(position: Int, count: Int) { }
    override fun onMoved(fromPosition: Int, toPosition: Int) { }
    override fun onChanged(position: Int, count: Int, payload: Any?) { }
}