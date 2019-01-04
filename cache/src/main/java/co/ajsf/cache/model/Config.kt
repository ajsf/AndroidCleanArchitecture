package co.ajsf.cache.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import co.ajsf.cache.db.ConfigConstants

@Entity(tableName = ConfigConstants.TABLE_NAME)
data class Config(
    @PrimaryKey(autoGenerate = true)
    val id: Int = -1,
    val lastCacheTime: Long
)