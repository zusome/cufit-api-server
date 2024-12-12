package com.official.cufitapi.domain.api.docs

import com.official.cufitapi.domain.api.dto.invitation.InvitationCodeGenerateRequest
import com.official.cufitapi.domain.api.dto.invitation.InvitationCodeRequest
import com.official.cufitapi.domain.api.dto.invitation.InvitationCodeResponse

import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.RequestBody



interface InvitationApiDocs {


    fun validateInvitation(
        memberId: Long,
        @RequestBody request: InvitationCodeRequest
    ) : ResponseEntity<Unit>


    fun generate(
        memberId: Long,
        @RequestBody request: InvitationCodeGenerateRequest
    ) : ResponseEntity<InvitationCodeResponse>
}