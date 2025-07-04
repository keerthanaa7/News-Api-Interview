package com.example.mytripactions

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mytripactions.compose.TATheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val newsViewModel: NewsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TATheme {
                getNewsApi(newsViewModel)
            }
        }
    }
}

@Composable
fun getNewsApi(newsViewModel: NewsViewModel) {
    LaunchedEffect(Unit) { newsViewModel.getArticlesList() }
    val articleState by newsViewModel.articles.collectAsState()

    when (articleState) {
        is UIState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }

        is UIState.Error -> {
            val error = (articleState as UIState.Error).message
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = error, color = Color.Red)
            }
        }

        is UIState.Success -> {
            val articles = (articleState as UIState.Success).data
            if (articles.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text("No articles available")
                }
            } else {
                LazyColumn {
                    items(articles.size) { index ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                           /* articles[index].title?.let {
                                Text(text = it, modifier = Modifier.weight(1f, fill = false))
                            }*/
                            AsyncImage(
                                model = articles[index].urlToImage,
                                contentDescription = null
                            )

                            //   Text(text = articles[index].url)

                            //   Text(text = articles[index].publishedAt)

                        }
                        HorizontalDivider(thickness = 2.dp)
                    }
                }
            }

        }
    }
}