package com.official.cufitapi.enums

import com.official.cufitapi.domain.enums.IdealAge
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class IdealAgeTest {

    @Test
    @DisplayName("문자열로된 이상형 나이 범위를 이상형 나이 목록으로 반환할 수 있다.")
    fun convertToIdealAgeList() {
        val idealAgeRangeString = "OLDER,EQUAL"
        val idealAges = IdealAge.convertToIdealAgeList(idealAgeRangeString)
        Assertions.assertThat(idealAges)
            .isEqualTo(listOf(IdealAge.OLDER, IdealAge.EQUAL))
    }

    @Test
    @DisplayName("문자열로된 이상형 나이 범위에 오타가 있을 경우에 예외를 반환한다.")
    fun convertToIdealAgeListWithMissingTypo() {
        val idealAgeRangeString = "OLDER,EQUEL"

        Assertions.assertThatThrownBy {
            IdealAge.convertToIdealAgeList(idealAgeRangeString)
        }
        .isExactlyInstanceOf(IllegalArgumentException::class.java)
    }
}