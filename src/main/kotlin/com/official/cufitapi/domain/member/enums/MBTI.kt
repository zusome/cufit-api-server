package com.official.cufitapi.domain.member.enums

enum class MBTI {
    ISTJ,
    ISFJ,
    INFJ,
    INTJ,
    ISTP,
    ISFP,
    INFP,
    INTP,
    ESTP,
    ESFP,
    ENFP,
    ENTP,
    ESTJ,
    ESFJ,
    ENFJ,
    ENTJ;

    companion object {
        @JvmStatic
        fun split(mbti: MBTI): List<String> {
            return mbti.name.split("")
        }
    }
}

enum class MBTILetter(
    val description: String
) {
    E("외향적"),
    I("내향적"),
    N("창의적"),
    S("현실적"),
    F("감성적"),
    T("이성적"),
    P("즉흥형"),
    J("계획형");

    companion object {
        @JvmStatic
        fun convert(mbtiList: List<String>): List<MBTILetter> {
            return mbtiList.map { value ->
                when (value) {
                    "E" -> MBTILetter.E
                    "I" -> MBTILetter.I
                    "N" -> MBTILetter.N
                    "S" -> MBTILetter.S
                    "F" -> MBTILetter.F
                    "T" -> MBTILetter.T
                    "P" -> MBTILetter.P
                    "K" -> MBTILetter.J
                    else -> throw IllegalArgumentException("Invalid MBTI letter: $value")
                }
            }
        }
    }
}

