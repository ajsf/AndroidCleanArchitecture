package co.ajsf.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import co.ajsf.cache.db.ConfigConstants
import co.ajsf.cache.model.Config
import io.reactivex.Maybe

@Dao
abstract class ConfigDao {

    @Query(ConfigConstants.QUERY_CONFIG)
    abstract fun getConfig(): Maybe<Config>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertConfig(config: Config)
}