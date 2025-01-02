package com.official.cufitapi.domain.member.enums

enum class IdealAge(
    val description: String
) {
    OLDER("연상"),
    EQUAL("동갑"),
    LOWER("연하");

    companion object {
        @JvmStatic
        fun convertToIdealAgeList(idealAgeRangeString: String): List<com.official.cufitapi.domain.member.enums.IdealAge> {
            return idealAgeRangeString.split(",")
                .map {
                    com.official.cufitapi.domain.member.enums.IdealAge.valueOf(it)
                }
        }
    }
}