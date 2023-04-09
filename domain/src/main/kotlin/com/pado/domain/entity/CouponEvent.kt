package com.pado.domain.entity

import com.pado.domain.type.EventPopupType
import org.springframework.data.mongodb.core.mapping.Document

class CouponEvent(
    val name: String,
    val couponCount: Long,
    val type: EventPopupType?,
    val description: String?,
)