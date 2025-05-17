package com.official.cufitapi.domain.member.infrastructure.persistence.sql

object MemberSqlConstant {

    const val CANDIDATE_BY_MEMBER_ID_SQL = """
            SELECT * FROM CANDIDATES WHERE CANDIDATES.MEMBER_ID = :memberId
        """

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

    const val CANDIDATE_IMAGE_BY_MEMBER_IDS_SQL = """
            SELECT ci.*, c.member_id FROM candidate_images ci 
            LEFT JOIN candidates c 
            ON ci.candidate_id = c.id
            WHERE c.member_id IN (:ids)
        """

    const val MEMBER_SQL = """
            SELECT * FROM MEMBERS WHERE MEMBERS.ID = :id
        """

    const val MEMBER_RELATIONS_BY_INVITEE_ID = """
            SELECT * FROM MEMBER_RELATIONS
            WHERE MEMBER_RELATIONS.INVITEE_ID = :inviteeId
        """

    const val MEMBER_RELATIONS_BY_INVITER_ID = """
            SELECT * FROM MEMBER_RELATIONS
            WHERE MEMBER_RELATIONS.INVITER_ID = :inviterId
        """

    const val MEMBER_RELATIONS_BY_NOT_INVITER_ID = """
            SELECT * FROM MEMBER_RELATIONS
            WHERE MEMBER_RELATIONS.INVITER_ID = :inviterId
        """

    const val MATCHES_BY_LEFT_CANDIDATE_MEMBER_ID_SQL = """
            SELECT MATCHES.* FROM MATCHES
            WHERE MATCHES.LEFT_CANDIDATE_MEMBER_ID = :leftMemberId
        """

    const val MATCHES_BY_RIGHT_CANDIDATE_MEMBER_ID_SQL = """
            SELECT MATCHES.* FROM MATCHES
            WHERE MATCHES.RIGHT_CANDIDATE_MEMBER_ID = :rightMemberId
        """
}
