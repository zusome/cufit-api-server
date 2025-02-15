package com.official.cufitapi.domain.member.infrastructure.persistence.sql

object MemberSqlConstant {

    const val OTHER_CANDIDATE_COUNT_SQL = """
            SELECT
                COUNT(*)
            FROM
                match_candidate
            JOIN 
                member_relations ON match_candidate.member_id = member_relations.invitee_id
            WHERE
                member_relations.inviter_id != :memberId
        """

    const val CANDIDATE_COUNT_SQL = """
            SELECT COUNT(*)
            FROM match_candidate
            JOIN member_relations ON match_candidate.member_id = member_relations.invitee_id
            WHERE member_relations.inviter_id = :memberId
        """
}
