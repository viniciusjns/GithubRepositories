package com.example.githubrepositories.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.githubrepositories.model.Owner
import com.example.githubrepositories.model.Repo
import com.example.githubrepositories.repository.MainRepository
import com.example.githubrepositories.rules.TestCoroutineRule
import com.example.githubrepositories.utils.PreferencesHelper
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WithoutPagingLibraryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: WithoutPagingLibraryViewModel

    @Mock
    private lateinit var repository: MainRepository

    private lateinit var preferencesHelper: PreferencesHelper

    @Before
    fun setUp() {
        preferencesHelper = PreferencesHelper()
        viewModel = WithoutPagingLibraryViewModel(repository, preferencesHelper)
    }

    @Test
    fun `should update livedata if result is diferent`() =
        testCoroutineRule.runBlockingTest {
            // given
            val repoList = mockRepos()
            val currentPage = 1
            val liveDataValue = viewModel.reposLiveData.value

            // when
            whenever(repository.searchRepositories("language:kotlin", "stars", currentPage))
                .thenReturn(repoList)
            viewModel.searchRepos()
            val result = viewModel.reposLiveData.value

            // then
            assertNotSame(result, liveDataValue)
        }

    @Test
    fun `should not update livedata if result is equal`() =
        testCoroutineRule.runBlockingTest {
            // given
            val repoList = mockRepos()
            val currentPage = 1

            // when
            whenever(repository.searchRepositories("language:kotlin", "stars", currentPage))
                .thenReturn(repoList)
            viewModel.reposLiveData.value = repoList
            viewModel.searchRepos()
            val result = viewModel.reposLiveData.value

            // then
            assertEquals(result, repoList)
        }

    private fun mockRepos() = (1..10).map {
        Repo(
            id = it,
            name = "Repo $it",
            fullName = "Github repo $it",
            stars = it * 2,
            forks = it,
            owner = mockOwner()
        )
    }

    private fun mockOwner(): Owner =
        Owner(
            ownerId = 0,
            login = "Owner login",
            avatarUrl = "Owner url"
        )


}