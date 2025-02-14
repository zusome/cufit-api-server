package com.official.cufitapi.domain.arrangement.domain

interface ArrangementRepository {
    fun findById(id: Long): Arrangement
}