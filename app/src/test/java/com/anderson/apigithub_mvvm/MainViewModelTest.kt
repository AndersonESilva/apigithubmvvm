package com.anderson.apigithub_mvvm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anderson.apigithub_mvvm.data.response.ItemResponse
import com.anderson.apigithub_mvvm.data.response.RepositoryResponse
import com.anderson.apigithub_mvvm.data.response.Resource
import com.anderson.apigithub_mvvm.feature.main.conveter.MainConverter
import com.anderson.apigithub_mvvm.feature.main.viewmodel.MainViewModel
import com.anderson.apigithub_mvvm.service.GitHubRepository
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Created by anderson on 20/11/20.
 */
@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: GitHubRepository

    @Mock
    lateinit var converter: MainConverter

    lateinit var viewModel: MainViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(repository, converter)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun testGetRepositoriesDataError() = runBlocking {

        whenever(repository.getRepositories(1)).thenReturn(Resource.error(anyString()))

        viewModel.getRepositories()
        delay(2000L)

        val value = viewModel.resource.value
        assertEquals(Resource.Status.ERROR, value?.status)
    }

    @Test
    fun testGetRepositoriesDataSuccess() = runBlocking {
        whenever(repository.getRepositories(1)).thenReturn(successMock())

        viewModel.getRepositories()
        delay(2000L)

        val value = viewModel.resource.value
        assertEquals(Resource.Status.SUCCESS, value?.status)
    }

    private fun successMock(): Resource<ItemResponse>{
        val list = listOf<RepositoryResponse>()
        return Resource.success(ItemResponse(list))
    }
}
