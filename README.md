# BloomFilterAndroidTest
Trying out the ease of use of BloomFilters on Android using Google's Guava library. It's pretty straightforward as long as you realise there's a 3% false positive rate with the detault settings used here. This can be lowered at the cost of additional memory.

The only file worth checking out here is the actual [BloomFilterTest](app/src/test/java/com/aniketkadam/bloomfilterexperiment/BloomFilterTest.kt)

Maybe the relevant [imports](app/build.gradle#L34)
