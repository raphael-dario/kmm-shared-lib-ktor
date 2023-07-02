package com.labstyle.kmmsharedlibktor.repo

import com.labstyle.kmmsharedlibktor.models.SearchResult
import com.labstyle.kmmsharedlibktor.models.SearchResultItem
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import io.ktor.http.path
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

private const val apiUrl = "https://itunes.apple.com/"

class ITunesRepo {
    private val httpClient = HttpClient()
    private val jsonSerializer = Json {
        ignoreUnknownKeys = true
        isLenient = false
    }

    fun searchAlbum(
        term: String,
        onSuccess: (List<SearchResultItem>) -> Unit,
        onError: (Exception) -> Unit
    ) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val apiResult = callSearchApiForAlbum(term)
            onSuccess(apiResult.results)
        } catch (e: Exception) {
            onError(e)
        }
    }

    private suspend fun callSearchApiForAlbum(term: String): SearchResult {
        val response: HttpResponse = httpClient.request(apiUrl) {
            method = HttpMethod.Get
            url {
                path("search")
                parameters.append("media", "music")
                parameters.append("entity", "album")
                parameters.append("term", term)
            }
        }
        val responseBody = response.bodyAsText()
        return jsonSerializer.decodeFromString(responseBody)
    }
}