package com.pado.domain.entity

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "member")
data class Member(
    val id: ObjectId,
    val name: String,
)