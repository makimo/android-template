package pl.makimo.androidtemplate.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


/**
 * This is an example model, which is created in room database.
 */
@Entity
data class User (
    @PrimaryKey(autoGenerate = true) var uid: Int? = null,
    @ColumnInfo(name = "username") var username: String? = null
) : Serializable