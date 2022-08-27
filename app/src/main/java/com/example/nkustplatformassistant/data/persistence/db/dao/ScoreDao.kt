package com.example.nkustplatformassistant.data.persistence.db.dao

import androidx.room.*
import com.example.nkustplatformassistant.data.persistence.db.converter.DataConverter
import com.example.nkustplatformassistant.data.remote.Subject
import kotlinx.coroutines.flow.Flow

@Dao
@TypeConverters(DataConverter::class)
interface ScoreDao {

    @Query("SELECT * FROM score_table ORDER BY id ASC")
    fun getScoreList(): Flow<List<Subject>>

//        @Query("SELECT * FROM score_table ORDER BY mid_term_exam_score DESC")
//        fun get

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScore(subject: List<Subject>){
        DataConverter().fromSubjectToDB(subject)
    }

    @Query("DELETE FROM score_table")
    suspend fun emptyScoreTable()
}