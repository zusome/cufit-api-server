package com.official.cufitapi.domain.member.infrastructure.persistence.sql

object MemberSqlConstant {

    const val CANDIDATE_COUNT_SQL = """
            SELECT c.*
            FROM candidates c
            JOIN member_relations ON c.member_id = member_relations.invitee_id
            WHERE member_relations.inviter_id = :memberId 
        """

    const val OTHER_CANDIDATE_COUNT_SQL = """
            SELECT c.*
            FROM candidates c 
            JOIN member_relations ON c.member_id = member_relations.invitee_id
            WHERE member_relations.inviter_id != :memberId
        """
}
