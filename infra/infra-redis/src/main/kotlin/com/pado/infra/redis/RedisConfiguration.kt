package com.mongodb.annotations

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories


@Configuration
@EnableRedisRepositories(basePackages = arrayOf("com.pado.domain"))
class RedisConfiguration {
    @Value("\${spring.redis.host}")
    private val redisHost: String? = null

    @Value("\${spring.redis.port}")
    private val redisPort = 0
    @Bean
    fun redissonClient(): RedissonClient? {
        var redisson: RedissonClient? = null
        val config = Config()
        config.useSingleServer().setAddress(REDISSON_HOST_PREFIX + redisHost + ":" + redisPort)
        redisson = Redisson.create(config)
        return redisson
    }

    companion object {
        private const val REDISSON_HOST_PREFIX = "redis://"
    }
}
