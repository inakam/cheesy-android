package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

// パース用の構造体
@Serializable
data class ArticleRemoteModel(
    val title: String,
    val url: String,
    val user: UserRemoteModel,
    @SerialName(value = "likes_count")
    val likesCount: Int = 0
)

@Serializable
data class UserRemoteModel(
    val name: String
)

// Retrofitのインターフェース
interface QiitaAPI {
    @GET("/api/v2/items")
    suspend fun getArticles(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<ArticleRemoteModel>
}

// アプリ内で使う構造体
data class ArticleLocalModel (
    val title: String,
    val url: String,
    val user: UserLocalModel,
    val likesCount: Int
)

data class UserLocalModel(
    val name: String
)

// dataSourceのインターフェース
interface QiitaDataSourceRemote {
    suspend fun getArticles(
        page: Int,
        parPage: Int
    ):List<ArticleLocalModel>
}

// dataSource
class QiitaDataSourceRemoteImpl @Inject constructor(): QiitaDataSourceRemote {
    override suspend fun getArticles(
        page: Int,
        parPage: Int
    ): List<ArticleLocalModel> {
        // APIクライアント作成
        val contentType = "application/json".toMediaType()
        val converter = Json { ignoreUnknownKeys = true }
        val client = OkHttpClient.Builder().build()
        val api = Retrofit.Builder()
            .baseUrl("https://qiita.com")
            .addConverterFactory(converter.asConverterFactory(contentType))
            .client(client)
            .build()
            .create(QiitaAPI::class.java)

        return api.getArticles(
            page = 1,
            perPage = 3
        ).map { article ->
            ArticleLocalModel(
                title = article.title,
                url = article.url,
                user = UserLocalModel(
                    name = article.user.name
                ),
                likesCount = article.likesCount
            )
        }
    }
}

// viewState
data class ArticlesViewState (
    var articles: List<ArticleLocalModel>
)

@Module
@InstallIn(SingletonComponent::class)
abstract class QiitaDataSourceRemoteModule {
    @Binds
    abstract fun bindQiitaDataSourceDataSourceRemote(
        dataSource: QiitaDataSourceRemoteImpl
    ) :QiitaDataSourceRemote
}

// viewModel
@HiltViewModel
class ArticlesViewModel @Inject constructor(private val dataSource: QiitaDataSourceRemote) : ViewModel() {
    private val _viewState = MutableStateFlow(
        ArticlesViewState(
            articles = listOf()
        )
    )
    val viewState = _viewState.asStateFlow()

    fun fetchArticles() {
        viewModelScope.launch {
            val newData = dataSource.getArticles(1,3)
            Log.d("hoge1", newData.toString())
            _viewState.update { it ->
                it.copy(articles = newData)
            }
        }
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArticlesContainer()
                }
            }
        }
    }
}

@Composable
fun ArticlesContainer(viewModel: ArticlesViewModel = hiltViewModel()) {
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(Unit){
        viewModel.fetchArticles()
        Log.d("hoge", viewState.articles.toString())
    }

    Log.d("hoge", viewState.articles.toString())

    ArticlesScreen(articles = viewState.articles)
}

@Composable
fun ArticlesScreen(
    articles: List<ArticleLocalModel>
) {
    Column {
        articles.forEach { article ->
            Article(article = article)
        }
    }
}

@Composable
fun Article(
    article: ArticleLocalModel
) {
    Card {
        Text(
            text = article.title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = article.url,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(8.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                painter = rememberVectorPainter(image = Icons.Filled.Favorite),
                contentDescription = null,
                modifier = Modifier.padding(2.dp)
            )
            Text(text = article.likesCount.toString(), style = MaterialTheme.typography.labelMedium)

            Spacer(modifier = Modifier.weight(1f))

            Text(text = article.user.name, style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(end = 0.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticlePreview() {
    MyApplicationTheme {
        Article(
            article = ArticleLocalModel(
                title = "ほげタイトル",
                url = "https://qiita.com",
                likesCount = 3,
                user = UserLocalModel(
                    name = "Ryomm"
                )
            )
        )
    }
}