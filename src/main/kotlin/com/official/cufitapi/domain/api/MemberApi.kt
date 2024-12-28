package com.official.cufitapi.domain.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.domain.api.docs.MemberApiDocs
import com.official.cufitapi.domain.api.dto.MemberInfoResponse
import com.official.cufitapi.domain.api.dto.MemberProfileRequest
import com.official.cufitapi.domain.application.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1Controller
class MemberApi(
    private val memberService: MemberService
) : MemberApiDocs {

    // 유저 정보 조회
    @GetMapping("/members")
    fun getMemberInfo(
        @Authorization(AuthorizationType.ALL) memberId: Long
    ) : ResponseEntity<MemberInfoResponse> {
        memberService.getMemberInfo(memberId)
        return ResponseEntity.noContent().build()
    }

    // 프로필 작성
    @PostMapping("/members/profile")
    fun updateProfile(
        @Authorization(AuthorizationType.ALL) memberId: Long,
        @RequestBody request: MemberProfileRequest
    ) : ResponseEntity<Unit> {
        memberService.updateMemberProfile(memberId, request)
        return ResponseEntity.noContent().build()
    }

}