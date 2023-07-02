package com.labstyle.kmmsharedlibktor.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResultItem(
    @SerialName("collectionType")
    val collectionType: String,
    @SerialName("collectionId")
    val collectionId: Long,
    @SerialName("artistName")
    val artistName: String,
    @SerialName("collectionName")
    val collectionName: String
)
