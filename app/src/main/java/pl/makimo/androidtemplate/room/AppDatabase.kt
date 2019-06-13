package pl.makimo.androidtemplate.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import pl.makimo.androidtemplate.BuildConfig
import pl.makimo.androidtemplate.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // Here add more DAOs
    abstract fun users(): UserDao

    companion object {
        fun get(app: Application) = Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            BuildConfig.DBNAME
        )
//        .addMigrations(MIGRATION_1_2, MIGRATION_2_1)
        .build()
}
}

//
// Examples of migrations
//
//val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//    override fun migrate(database: SupportSQLiteDatabase) {
//        database.execSQL("ALTER TABLE user ADD COLUMN name TEXT")
//    }
//}
//
//val MIGRATION_2_1: Migration = object : Migration(2, 1) {
//    override fun migrate(database: SupportSQLiteDatabase) {
//        // SQLite does not provide method to drop single column
//        database.apply {
//            execSQL("BEGIN TRANSACTION;")
//            execSQL("ALTER TABLE user RENAME TO temp_user;")
//            execSQL("CREATE TABLE user (uid INT PRIMARY KEY, username TEXT);")
//            execSQL("INSERT INTO user SELECT uid, username FROM temp_user;")
//            execSQL("DROP TABLE temp_user;")
//            execSQL("COMMIT;")
//        }
//    }
//}
