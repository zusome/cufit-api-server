package com.official.cufitapi.domain.member.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.common.api.docs.MemberApiDocs
import com.official.cufitapi.domain.member.api.dto.MemberInfoResponse
import com.official.cufitapi.domain.member.api.dto.MemberProfileRequest
import com.official.cufitapi.domain.member.api.dto.MemberTypeInfoResponse
import com.official.cufitapi.domain.member.application.MemberService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1Controller
class MemberApi(
    private val memberService: MemberService
) : MemberApiDocs {

    // 유저 타입 조회
    @GetMapping("/members/type")
    override fun getMemberTypeInfo(
        @Authorization(AuthorizationType.ALL) memberId: Long
    ): HttpResponse<MemberTypeInfoResponse> {
        val member = memberService.findById(memberId)
        return HttpResponse.of(
            HttpStatus.NO_CONTENT,
            MemberTypeInfoResponse(member.name.isBlank(), member.memberType.name)
        )
    }

    // 유저 정보 조회
    @GetMapping("/members")
    fun getMemberInfo(
        @Authorization(AuthorizationType.ALL) memberId: Long
    ): HttpResponse<MemberInfoResponse> {
        val memberInfo = memberService.getMemberInfo(memberId)
        return HttpResponse.of(HttpStatus.NO_CONTENT, memberInfo)
    }

    // 프로필 작성
    @PostMapping("/members/profile")
    fun updateProfile(
        @Authorization(AuthorizationType.ALL) memberId: Long,
        @RequestBody request: MemberProfileRequest
    ): HttpResponse<Unit> {
        memberService.updateMemberProfile(memberId, request)
        return HttpResponse.of(HttpStatus.OK, Unit)
    }
}
