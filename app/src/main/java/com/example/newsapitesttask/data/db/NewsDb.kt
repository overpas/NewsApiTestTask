package com.example.newsapitesttask.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.newsapitesttask.data.db.entity.ArticleEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

@Database(
    entities = [
      ArticleEntity::class,
    ],
    version = 1,
)
@TypeConverters(Converters::class)
internal abstract class NewsDb : RoomDatabase() {

    abstract fun articlesDao(): ArticlesDao
}

private object Converters {

    @TypeConverter
    fun toTimestamp(dateTime: LocalDateTime?): Long? {
        return dateTime?.toInstant(TimeZone.UTC)?.toEpochMilliseconds()
    }

    @TypeConverter
    fun toLocalDateTime(timestamp: Long?): LocalDateTime? {
        return timestamp?.let(Instant::fromEpochMilliseconds)?.toLocalDateTime(TimeZone.UTC)
    }
}
