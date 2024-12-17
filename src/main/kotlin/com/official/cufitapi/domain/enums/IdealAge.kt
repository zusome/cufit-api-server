package com.official.cufitapi.domain.enums

enum class IdealAge(
    val description: String
) {
    OLDER("연상"),
    EQUAL("동갑"),
    LOWER("연하");

    companion object {
        @JvmStatic
        fun convertToIdealAgeList(idealAgeRangeString: String): List<IdealAge> {
            return idealAgeRangeString.split(",")
                .map {
                    IdealAge.valueOf(it)
                }
        }
    }
}