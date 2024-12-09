package com.official.cufitapi.domain.enums

class IdealCharacteristics {
    var ageRange: List<AgeIdealCharacteristics> = listOf()
    var heightFrom: HeightIdealCharacteristicsUnit? = null
    var heightTo: HeightIdealCharacteristicsUnit? = null
    // lenght 4
    var idealMbti: List<MBTILetter> = listOf()
}