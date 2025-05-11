package com.official.cufitapi.domain.auth.infrastructure.persist

import org.springframework.data.jpa.repository.JpaRepository

interface JpaSmsWhiteListRepository: JpaRepository<JpaSmsWhiteList, Long> {
}
