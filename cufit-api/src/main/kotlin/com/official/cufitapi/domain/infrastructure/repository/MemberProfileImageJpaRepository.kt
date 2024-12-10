package com.official.cufitapi.domain.infrastructure.repository

import com.official.cufitapi.domain.infrastructure.entity.MemberProfileImage
import org.springframework.data.jpa.repository.JpaRepository

interface MemberProfileImageJpaRepository : JpaRepository<MemberProfileImage, Int>