package pl.makimo.androidtemplate.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import pl.makimo.androidtemplate.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun loadAll(): LiveData<List<User>>
}