package com.pado.domain.entity

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

class CouponStock(
    val couponMetaInfoId: ObjectId,
    val count: Long,
)