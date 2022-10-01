package com.santimattius.deeplink.dispatcher.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deeplink")
data class DeepLinkEntity(
    @PrimaryKey @ColumnInfo(name = "id") val uid: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "uri") val uri: String,
)
