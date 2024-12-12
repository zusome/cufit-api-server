package com.official.cufitapi

import com.official.cufitapi.domain.enums.MBTI
import org.junit.jupiter.api.Test

class MbtiTest {

    @Test
    fun mbti() {
        MBTI.split(MBTI.ENFJ)
            .map {
                println(it)
            }
    }
}