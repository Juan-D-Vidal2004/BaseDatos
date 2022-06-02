package com.jose.basedatos.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario (

    @PrimaryKey(autoGenerate = true) val uid: Int?,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name ") val lastName: String?,
   // @NonNull @ColumnInfo(name = "stop_name") val stopName: String,
   //@NonNull @ColumnInfo(name = "arrival_time") val arrivalTime: Int
    )