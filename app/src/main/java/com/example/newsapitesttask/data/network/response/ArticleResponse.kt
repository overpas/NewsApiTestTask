package com.example.newsapitesttask.data.network.response

import com.example.newsapitesttask.domain.Article
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.serializers.LocalDateTimeIso8601Serializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
internal data class ArticleResponse(
    @SerialName("author")
    val author: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("url")
    val url: String? = null,
    @SerialName("urlToImage")
    val urlToImage: String? = null,
    @SerialName("publishedAt")
    @Serializable(with = LocalDateTimeSerializer::class)
    val publishedAt: LocalDateTime? = null,
    @SerialName("content")
    val content: String? = null,
)

private object LocalDateTimeSerializer : KSerializer<LocalDateTime> {

    private val defaultSerializer = LocalDateTimeIso8601Serializer

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return decoder.decodeString()
            .replace("Z", "")
            .let(LocalDateTime::parse)
    }

    override val descriptor: SerialDescriptor =
        defaultSerializer.descriptor

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        defaultSerializer.serialize(encoder, value)
    }
}

internal fun ArticleResponse.toArticle(): Article =
    Article(
        id = 0L,
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
    )
