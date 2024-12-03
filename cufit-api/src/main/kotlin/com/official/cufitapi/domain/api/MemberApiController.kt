package com.official.cufitapi.domain.api

import com.official.cufitapi.domain.api.dto.MemberInfoResponse
import com.official.cufitapi.domain.api.dto.MemberProfileRequest
import com.official.cufitapi.domain.application.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1Controller
class MemberApiController(
    private val memberService: MemberService
) {

    // 유저 정보 조회
    @GetMapping("/members")
    fun getMemberInfo() : ResponseEntity<MemberInfoResponse> {
        memberService.getMemberInfo()
        return ResponseEntity.noContent().build()
    }

    // 프로필 작성
    @PostMapping("/members/profile")
    fun updateProfile(
        memberId: Long,
        @RequestBody request: MemberProfileRequest
    ) {
        memberService.updateMemberProfile(memberId, request)
    }


}