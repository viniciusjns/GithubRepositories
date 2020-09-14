package com.example.githubrepositories.repository

import com.example.githubrepositories.dao.RepoDao
import com.example.githubrepositories.model.Owner
import com.example.githubrepositories.model.Repo
import com.example.githubrepositories.model.ResponseWrap
import com.example.githubrepositories.rules.TestCoroutineRule
import com.example.githubrepositories.service.GithubService
import com.example.githubrepositories.utils.PreferencesHelper
import com.example.githubrepositories.utils.RepoMapper
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainRepositoryImplTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var service: GithubService

    @Mock
    private lateinit var dao: RepoDao

    private lateinit var repository: MainRepository

    private lateinit var preferencesHelper: PreferencesHelper

    private lateinit var repoMapper: RepoMapper

    @Before
    fun setUp() {
        repoMapper = RepoMapper()
        preferencesHelper = PreferencesHelper()
        repository = MainRepositoryImpl(
            service, dao,
            preferencesHelper, repoMapper
        )
    }

    @Test
    fun `should return list from database when service return null`() =
        testCoroutineRule.runBlockingTest {
            // given
            val responseWrap: ResponseWrap<Repo>? = null
            val repoList = mockRepos()

            // when
            whenever(service.searchRepositories("", "", 1))
                .thenReturn(responseWrap)
            whenever(dao.getRepos())
                .thenReturn(repoMapper.transformRepoToEntity(repoList))
            val result = repository.searchRepositories("", "", 1)

            // then
            assertEquals(result, repoList)
        }

    @Test
    fun `should return list from server and concat with database list`() =
        testCoroutineRule.runBlockingTest {
            // given
            val repoList = mockRepos()
            val datas = mockRepos()
            val responseWrap = ResponseWrap(
                totalCount = 1000,
                incompleteResults = false,
                items = repoList
            )

            // when
            whenever(service.searchRepositories("", "", 1))
                .thenReturn(responseWrap)
            whenever(dao.getRepos()).thenReturn(repoMapper.transformRepoToEntity(datas))
            val result = repository.searchRepositories("", "", 1)

            // then
            assertThat(result.size, CoreMatchers.`is`(20))
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