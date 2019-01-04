package co.ajsf.data.test.factory

import java.util.*
import java.util.concurrent.ThreadLocalRandom

internal object DataFactory {

    fun randomString(): String = UUID.randomUUID().toString()

    fun randomInt(): Int = ThreadLocalRandom.current().nextInt(0, 1000 + 1)

    fun randomLong(): Long = randomInt().toLong()

    fun randomBoolean(): Boolean = Math.random() < 0.5
}