package com.official.cufitapi.domain.api

import com.official.cufitapi.domain.api.docs.MatchMakerApiDocs
import com.official.cufitapi.domain.application.MatchMakerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@ApiV1Controller
class MatchMakerApi(
    private val matchMakerService: MatchMakerService
) : MatchMakerApiDocs {

    // 본인이 등록한 후보자 목록 조회
    @GetMapping("/matchmakers/{matchMakerId}/candidates")
    fun getRegisteredCandidates(
        @PathVariable matchMakerId: Long,
        memberId: Long
    ) : ResponseEntity<Unit>{
        // matchMakerService.getCandidates(matchMakerId)
        return ResponseEntity.noContent().build()
    }
}