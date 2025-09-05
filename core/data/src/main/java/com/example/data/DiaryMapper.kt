package com.example.data

import com.example.domain.DiaryDto

fun DiaryEntity.toDiaryDto() : DiaryDto {
    return DiaryDto(date = this.date,
        content = this.content)
}

fun DiaryDto.toDiaryEntity() :  DiaryEntity {
    return DiaryEntity(date = this.date,
        content = this.content)
}