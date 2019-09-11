package com.aniketkadam.bloomfilterexperiment

import com.google.common.hash.BloomFilter
import com.google.common.hash.Funnels
import org.junit.Test
import java.nio.charset.Charset

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class BloomFilterTest {

    @Test
    fun `playing with bloom filters`() {
        @Suppress("UnstableApiUsage")
        val animalBloomFilter =
            BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), 4)

        with(animalBloomFilter) {
            put("cat")
            put("dog")
            put("lizard")
            put("carnivores")
        }

        assert(!animalBloomFilter.mightContain("goat"))
    }

}
