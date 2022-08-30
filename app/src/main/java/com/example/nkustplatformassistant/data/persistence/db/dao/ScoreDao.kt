package com.example.nkustplatformassistant.data.persistence.db.dao

import androidx.room.*
import com.example.nkustplatformassistant.data.persistence.db.converter.DataConverter
import com.example.nkustplatformassistant.data.persistence.db.entity.ScoreDropDownList
import com.example.nkustplatformassistant.data.persistence.db.entity.ScoreEntity

@Dao
@TypeConverters(DataConverter::class)
interface ScoreDao {

    @Query("SELECT * FROM score_table ORDER BY id ASC")
    fun getScoreList(): List<ScoreEntity>

    @Query("SELECT * FROM score_table WHERE year = :year AND semester = :semester")
    fun getSpecScoreList(year: Int, semester: Int): List<ScoreEntity>

    @Query("SELECT DISTINCT year,semester,semDescription FROM SCORE_TABLE")
    fun getDropDownList(): List<ScoreDropDownList>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScore(scoreEntity: ScoreEntity)

    @Query("")
    suspend fun insertMultiScore(
        subjectList: List<ScoreEntity>,
    ) {
        for (item in subjectList) {
            insertScore(ScoreEntity(null,
                item.year,
                item.semester,
                item.semDescription,
                item.subjectName,
                item.midScore,
                item.finalScore)
            )
        }
    }

    @Query("DELETE FROM score_table")
    suspend fun emptyScoreTable()
}