package com.example.teamapp.viewModel

import app.cash.turbine.test
import com.example.teamapp.di.repository.APIRepository
import com.example.teamapp.model.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import retrofit2.Response

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class InviteMemberViewModelTest {
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    @Mock
    lateinit var apiRepository: APIRepository

    private lateinit var inviteMemberViewModel: InviteMemberViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        inviteMemberViewModel = InviteMemberViewModel(
            apiRepository = apiRepository,
            defaultDispatcher = testDispatcher,
            ioDispatcher = testDispatcher
        )
    }

    @Test
    fun `get Team as success`() {
        runTest {
            val teamResponse = Team("1", Members(100, 20, 20, 20, 20, 20), Plan(100, 20))
            val retrofitTeamResponse = Response.success(200, teamResponse)
            whenever(apiRepository.getTeam(ArgumentMatchers.anyString())).thenReturn(
                retrofitTeamResponse
            )
            inviteMemberViewModel.teamResponse.test {
                inviteMemberViewModel.getTeam(ArgumentMatchers.anyString())
                val firstEmit = awaitItem()
                Assert.assertTrue(firstEmit is com.example.teamapp.model.Response.Success)
                assertEquals(
                    teamResponse,
                    (firstEmit as com.example.teamapp.model.Response.Success).data
                )
            }

        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}