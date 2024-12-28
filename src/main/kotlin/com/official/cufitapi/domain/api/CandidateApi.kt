package com.official.cufitapi.domain.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.domain.api.docs.CandidateApiDocs
import org.springframework.web.bind.annotation.PostMapping

@ApiV1Controller
class CandidateApi() : CandidateApiDocs {

    // 추천순 (우리가 정한 score) , 최신순

    // 후보자 프로필 업데이트 API
    @PostMapping("/candidates")
    fun updateCandidateProfile(
        @Authorization(AuthorizationType.ALL) memberId: Long
    ) {

    }


}