package com.example.mytripactions

import com.example.mytripactions.api.NewsApi
import com.example.mytripactions.api.NewsArticleDto
import com.example.mytripactions.api.NewsResponse
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response

class NewsServiceTest {
    private var service: NewsApi = mockk()

    @Test
    fun getnewsSuccessfulResponse()  = runTest{
        val newsArticleList: List<NewsArticleDto> = listOf(
            NewsArticleDto("title1", "url1", "urltoimage1", "publishedat1"),
            NewsArticleDto("title2", "url2", "urltoimage2", "publishedat2"),
            NewsArticleDto("title3", "url3", "urltoimage3", "publishedat4")
        )

        val newsResponse = NewsResponse(newsArticleList)

        val successfulresponse = Response.success(newsResponse)
        coEvery {service.getNews()}returns successfulresponse

        val response = service.getNews()
        assertTrue(response.isSuccessful)
        assertEquals(response.body(), newsResponse)
    }
}