package com.pado.domain.service

import com.pado.domain.dto.CouponMetaInfoDto
import com.pado.domain.entity.Coupon
import com.pado.domain.factory.CouponFactory
import com.pado.domain.repository.mongo.MongoCouponRepository
import com.pado.domain.repository.redis.RedisCouponRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service


/**
DTO를 ENTITY로 변환하는 레이어, DB 저장 끝단이다.
 */

@Service
class CouponCommandService(
    private val mongoCouponRepository: MongoCouponRepository,
    private val redisCouponRepository: RedisCouponRepository,
    private val couponFactory: CouponFactory,
) {
    /**
     *    mongo 저장시 Redis를 동시 저장하도록 한다 -> repository 가 redis / mongo에 두 벌 존재하는 격
     *    pojo entity 사용할 필요 없이, 같은 entity에 redis, mongo관련 어노테이션을 모두 달아둔다.
     */

    // 쿠폰을 생성 후 저장
    fun createAndSave(couponMetaInfoDto: CouponMetaInfoDto, count: Int) {
        for (i in 1..count) {
            saveCoupon(couponMetaInfoDto)
        }
    }

    // 생성한 랜덤 넘버 쿠폰을 저장

    private fun saveCoupon(couponMetaInfoDto: CouponMetaInfoDto) {
        val createdCoupon = createCoupon(metaInfo = couponMetaInfoDto)
        // mongoCouponRepository.save(createdCoupon)
        redisCouponRepository.save(createdCoupon)
    }

    // 쿠폰 랜덤 넘버 생성
    private fun createCoupon(metaInfo: CouponMetaInfoDto): Coupon {
        val createdCoupon = couponFactory.createCouponCode(metaInfo = metaInfo.toEntity())
        val foundCoupon = redisCouponRepository.findByCode(code = createdCoupon.code)

        return if (foundCoupon == null) createdCoupon
        else createCoupon(metaInfo = metaInfo)
    }
}


