package com.official.cufitapi.domain.member.api

import com.google.rpc.context.AttributeContext.Auth
import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.member.api.docs.MemberApiDocs
import com.official.cufitapi.domain.member.api.dto.MemberTypeInfo
import com.official.cufitapi.domain.member.api.dto.UpdateMemberProfileRequest
import com.official.cufitapi.domain.member.application.MemberService
import com.official.cufitapi.domain.member.infrastructure.persistence.dao.MemberDao
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MemberInfoResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1Controller
class MemberApi(
    private val memberDao: MemberDao,
    private val memberService: MemberService,
) : MemberApiDocs {

    @GetMapping("/members")
    override fun relationInfo(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.CANDIDATE,
            AuthorizationType.MAKER
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<MemberInfoResponse> {
        val memberInfo = memberDao.relationInfo(authorizationUser.userId)
        return HttpResponse.of(HttpStatus.NO_CONTENT, memberInfo)
    }

    @GetMapping("/members/type")
    override fun memberType(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.CANDIDATE,
            AuthorizationType.MAKER
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<MemberTypeInfo> {
        return HttpResponse.of(
            HttpStatus.NO_CONTENT,
            memberDao.memberType(authorizationUser.userId)
        )
    }

    @PostMapping("/members/name")
    override fun realName(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.CANDIDATE,
            AuthorizationType.MAKER
        ) authorizationUser: AuthorizationUser,
        @RequestBody request: UpdateMemberProfileRequest,
    ): HttpResponse<Unit> {
        memberService.updateRealName(request.toCommand(authorizationUser.userId))
        return HttpResponse.of(HttpStatus.OK, Unit)
    }
}
