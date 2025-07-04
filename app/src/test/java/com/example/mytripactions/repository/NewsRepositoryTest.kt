package com.example.mytripactions.repository

import com.example.mytripactions.UIState
import com.example.mytripactions.api.NewsApi
import com.example.mytripactions.api.NewsArticleDto
import com.example.mytripactions.api.NewsResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

class NewsRepositoryTest {

    @MockK
    lateinit var newsApi: NewsApi

    private lateinit var repository: NewsRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = NewsRepositoryImpl(newsApi)
    }

    @Test
    fun getNewsArticleReturnExpected() = runTest {
        val newsArticleList: List<NewsArticleDto> = listOf(
            NewsArticleDto("title1", "url1", "urltoimage1", "publishedat1"),
            NewsArticleDto("title2", "url2", "urltoimage2", "publishedat2"),
            NewsArticleDto("title3", "url3", "urltoimage3", "publishedat4")
        )

        val newsResponse = NewsResponse(newsArticleList)

        val successfulresponse = Response.success(newsResponse)
        coEvery {
            newsApi.getNews()
        } returns successfulresponse

        val uistates = repository.getNews().toList()

        assertEquals(2, uistates.size)
        assertTrue(uistates[0] is UIState.Loading)
        assertTrue(uistates[1] is UIState.Success)
        assertEquals(newsArticleList, (uistates[1] as UIState.Success).data)
    }

    @Test
    fun getNewsArticleThrowsException() = runTest{

        val networkException = java.io.IOException("NO INTERNET")
        coEvery {
            newsApi.getNews()
        } throws networkException

        val uistates = repository.getNews().toList()
        assertEquals(2, uistates.size)
        assertTrue(uistates[0] is UIState.Loading)
        assertTrue(uistates[1] is UIState.Error)
        assertEquals("error fetching articles :NO INTERNET", (uistates[1] as UIState.Error).message)


    }

}