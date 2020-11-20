package com.anderson.apigithub_mvvm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anderson.apigithub_mvvm.data.response.PullRequestResponse
import com.anderson.apigithub_mvvm.data.response.Resource
import com.anderson.apigithub_mvvm.feature.pullRequest.converter.PullRequestConverter
import com.anderson.apigithub_mvvm.feature.pullRequest.viewmodel.PullRequestViewModel
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
class PullRequestViewModelTest{

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: GitHubRepository

    @Mock
    lateinit var converter: PullRequestConverter

    lateinit var viewModel: PullRequestViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        Dispatchers.setMain(testDispatcher)
        viewModel = PullRequestViewModel(repository, converter)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun testGetPullRequestesDataError() = runBlocking {
        whenever(repository.getPullRequestes(anyString(), anyString())).thenReturn(Resource.error(anyString()))

        viewModel.getPullRequestes(anyString(), anyString())
        delay(2000L)

        val value = viewModel.resource.value
        assertEquals(Resource.Status.ERROR, value?.status)
    }

    @Test
    fun testGetPullRequestesDataSuccess() = runBlocking {
        whenever(repository.getPullRequestes(anyString(), anyString())).thenReturn(successMock())

        viewModel.getPullRequestes(anyString(), anyString())
        delay(2000L)

        val value = viewModel.resource.value
        assertEquals(Resource.Status.SUCCESS, value?.status)
    }

    private fun successMock(): Resource<List<PullRequestResponse>>{
        val list = listOf<PullRequestResponse>()
        return Resource.success(list)
    }
}
