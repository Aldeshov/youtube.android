package com.aldeshov.youtube.service.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class LocalUser @Ignore constructor(
    @PrimaryKey var id: Int? = null,
    @ColumnInfo(name = "email") var email: String? = null,
    @ColumnInfo(name = "full_name") var full_name: String? = null,
    @ColumnInfo(name = "avatar") var avatar: String? = null,
    @ColumnInfo(name = "token") var token: String? = null) {

    constructor() : this(0, "", "", "", "")
}