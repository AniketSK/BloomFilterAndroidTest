package com.aniketkadam.bloomfilterexperiment

import com.google.common.hash.BloomFilter
import com.google.common.hash.Funnels
import org.junit.Test
import java.nio.charset.StandardCharsets

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
            BloomFilter.create(
                Funnels.stringFunnel(StandardCharsets.UTF_8),
                4,
                0.0001
            ) // 1 in 10,000 false positive probability.

        with(animalBloomFilter) {
            put("cat")
            put("dog")
            put("lizard")
            put("carnivores")
        }

        assert(!animalBloomFilter.mightContain("goat"))
        assert(animalBloomFilter.mightContain("cat"))
    }

    /**
     * To calculate the time and space the BloomFilter will take with a given data size and false
     * positive probability:
     *
     * Change the following:
     * 1. The total expected insertions, which is the number of rules you'd want to check against.
     * 2. The false positive probability.
     * It calculates:
     * 1. The number of hash functions that will be used O(n)
     * 2. The number of bits of memory that will be used O(n)
     *
     * Values are printed to the test results.
     *
     * Equations are taken from the BloomFilter's functions optimalNumOfBits/optimalNumOfHashFunctions.
     * optimal bits: https://github.com/google/guava/blob/bdaa4683401dbe9c05e16e6cd2b1ba3caba7c961/guava/src/com/google/common/hash/BloomFilter.java#L520
     * optimal hash functions: https://github.com/google/guava/blob/bdaa4683401dbe9c05e16e6cd2b1ba3caba7c961/guava/src/com/google/common/hash/BloomFilter.java#L504
     *
     */
    @Test
    fun `calculate time and space requirements for using a bloom filter with EasyList which is a large text file of adblocking rules, one per line`() {

        val totalExpectedInsertions = 74_000 // cat easylist.txt | wc -l
        val falsePositiveProbability: Double = 1.toDouble() / 10_000 // 1 in 10,000

        val optimalBits =
            (-totalExpectedInsertions * Math.log(falsePositiveProbability) / (Math.log(2.0) * Math.log(
                2.0
            ))).toLong()

        val optimalHashFunctions = Math.max(
            1,
            Math.round(optimalBits.toDouble() / totalExpectedInsertions * Math.log(2.0)).toInt()
        )

        println("Optimal Hash functions: $optimalHashFunctions")
        println("Optimal bits: ${optimalBits}, in kilobytes: ${optimalBits / (8 * 1024).toDouble()}")
    }

}
