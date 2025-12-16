package com.example.myeduplanner.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LearningPlanDao {

    @Insert
    suspend fun insert(learningPlan: LearningPlanEntity): Long

    @Update
    suspend fun update(learningPlan: LearningPlanEntity)

    @Delete
    suspend fun delete(learningPlan: LearningPlanEntity)

    @Query("SELECT * FROM learning_plans ORDER BY createdAt DESC")
    fun getAllLearningPlans(): Flow<List<LearningPlanEntity>>

    @Query("SELECT * FROM learning_plans WHERE id = :id")
    suspend fun getLearningPlanById(id: Long): LearningPlanEntity?

    @Query("SELECT * FROM learning_plans WHERE unitCode = :unitCode ORDER BY createdAt DESC")
    fun getLearningPlansByUnitCode(unitCode: String): Flow<List<LearningPlanEntity>>

    @Query("SELECT * FROM learning_plans WHERE sessionTitle LIKE '%' || :query || '%' OR unitCode LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    fun searchLearningPlans(query: String): Flow<List<LearningPlanEntity>>

    @Query("DELETE FROM learning_plans")
    suspend fun deleteAll()
}