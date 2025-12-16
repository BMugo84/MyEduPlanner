package com.example.myeduplanner.database

import kotlinx.coroutines.flow.Flow

class PlanRepository(private val database: AppDatabase) {

    private val learningPlanDao = database.learningPlanDao()
    private val sessionPlanDao = database.sessionPlanDao()

    // Learning Plans
    fun getAllLearningPlans(): Flow<List<LearningPlanEntity>> = learningPlanDao.getAllLearningPlans()

    suspend fun insertLearningPlan(plan: LearningPlanEntity): Long = learningPlanDao.insert(plan)

    suspend fun updateLearningPlan(plan: LearningPlanEntity) = learningPlanDao.update(plan)

    suspend fun deleteLearningPlan(plan: LearningPlanEntity) = learningPlanDao.delete(plan)

    suspend fun getLearningPlanById(id: Long): LearningPlanEntity? = learningPlanDao.getLearningPlanById(id)

    fun searchLearningPlans(query: String): Flow<List<LearningPlanEntity>> = learningPlanDao.searchLearningPlans(query)

    // Session Plans
    fun getAllSessionPlans(): Flow<List<SessionPlanEntity>> = sessionPlanDao.getAllSessionPlans()

    suspend fun insertSessionPlan(plan: SessionPlanEntity): Long = sessionPlanDao.insert(plan)

    suspend fun updateSessionPlan(plan: SessionPlanEntity) = sessionPlanDao.update(plan)

    suspend fun deleteSessionPlan(plan: SessionPlanEntity) = sessionPlanDao.delete(plan)

    suspend fun getSessionPlanById(id: Long): SessionPlanEntity? = sessionPlanDao.getSessionPlanById(id)

    fun searchSessionPlans(query: String): Flow<List<SessionPlanEntity>> = sessionPlanDao.searchSessionPlans(query)
}