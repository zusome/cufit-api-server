package com.official.cufitapi.domain.infrastructure

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
    ENTJ
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
    K("계획형")
}