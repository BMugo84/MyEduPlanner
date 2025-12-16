package com.example.myeduplanner.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionPlanDao {

    @Insert
    suspend fun insert(sessionPlan: SessionPlanEntity): Long

    @Update
    suspend fun update(sessionPlan: SessionPlanEntity)

    @Delete
    suspend fun delete(sessionPlan: SessionPlanEntity)

    @Query("SELECT * FROM session_plans ORDER BY createdAt DESC")
    fun getAllSessionPlans(): Flow<List<SessionPlanEntity>>

    @Query("SELECT * FROM session_plans WHERE id = :id")
    suspend fun getSessionPlanById(id: Long): SessionPlanEntity?

    @Query("SELECT * FROM session_plans WHERE unitCode = :unitCode ORDER BY createdAt DESC")
    fun getSessionPlansByUnitCode(unitCode: String): Flow<List<SessionPlanEntity>>

    @Query("SELECT * FROM session_plans WHERE sessionTitle LIKE '%' || :query || '%' OR unitCode LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    fun searchSessionPlans(query: String): Flow<List<SessionPlanEntity>>

    @Query("DELETE FROM session_plans")
    suspend fun deleteAll()
}
