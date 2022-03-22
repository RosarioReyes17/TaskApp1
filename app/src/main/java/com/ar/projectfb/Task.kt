package com.ar.projectfb


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "taskTable")

class Task (@ColumnInfo(name = "title") val taskTitle:String,
            @ColumnInfo(name = "description")val taskDescription:String,
            @ColumnInfo(name = "timestamp") val timeStamp:String,
            @ColumnInfo(name = "user") val user:String
            ){

    @PrimaryKey(autoGenerate = true) var id = 0
}