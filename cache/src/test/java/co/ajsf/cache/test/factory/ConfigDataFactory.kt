package co.ajsf.cache.test.factory

import co.ajsf.cache.model.Config

internal object ConfigDataFactory {

    fun makeCachedConfig(): Config = Config(
        DataFactory.randomInt(),
        DataFactory.randomLong()
    )
}