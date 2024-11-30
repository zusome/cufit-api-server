package com.official.cufitapi.domain.api

import com.official.cufitapi.domain.api.dto.MemberProfileRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1Controller
class MemberApiController {

    // 유저 정보 조회
    @GetMapping("/members")
    fun getMemberInfo() {

    }

    // 프로필 작성
    @PostMapping("/members/profile")
    fun updateProfile(@RequestBody request: MemberProfileRequest) {

    }


}