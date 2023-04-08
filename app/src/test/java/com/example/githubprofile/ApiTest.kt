package com.example.githubprofile

import com.example.githubprofile.config.ApiConfig
import com.example.githubprofile.interfaces.ApiServices
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class ApiTest {
    lateinit var service: ApiServices
    @Before
    fun setup() {
        service = ApiConfig.getApiService()
    }

    @Test
    fun checkIfUserExist() = runBlocking {
        val actualResult = service.getUser("SeptAlfauzan")
        assertNotNull(actualResult.twitterUsername)
    }

    @Test
    fun checkIfUsersMoreThanOne() = runBlocking {
        val actualResult = service.getListUsers("septa")
        assertEquals(true, actualResult.items?.size != 1)
    }
}