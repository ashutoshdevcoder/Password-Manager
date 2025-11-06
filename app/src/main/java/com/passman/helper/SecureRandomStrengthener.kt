package com.passman.helper

import java.nio.ByteBuffer
import java.security.DigestException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.LinkedList

class SecureRandomStrengthener private constructor(algorithm: String?) {
    private val algorithm: String
    private val entropySources: MutableList<EntropySource> = LinkedList()
    private lateinit var digest: MessageDigest
    private val seedBuffer: ByteBuffer

    init {
        require(!(algorithm == null || algorithm.length == 0)) { "Please provide a PRNG algorithm string such as SHA1PRNG" }
        this.algorithm = algorithm
        try {
            digest = MessageDigest.getInstance("SHA1")
        } catch (e: NoSuchAlgorithmException) {
            throw IllegalStateException(
                    "MessageDigest to create seed not available", e
            )
        }
        seedBuffer = ByteBuffer.allocate(digest.digestLength)
    }

    /**
     * Add an entropy source, which will be called for each generation and
     * re-seeding of the given random number generator.
     *
     * @param source
     * the source of entropy
     */
    fun addEntropySource(source: EntropySource?) {
        requireNotNull(source) { "EntropySource should not be null" }
        entropySources.add(source)
    }

    /**
     * Generates and seeds a random number generator of the configured
     * algorithm. Calls the [EntropySource.provideEntropy] method of all
     * added sources of entropy.
     *
     * @return the random number generator
     */
    fun generateAndSeedRandomNumberGenerator(): SecureRandom {
        val secureRandom: SecureRandom
        secureRandom = try {
            SecureRandom.getInstance(algorithm)
        } catch (e: NoSuchAlgorithmException) {
            throw IllegalStateException("PRNG is not available", e)
        }
        reseed(secureRandom)
        return secureRandom
    }

    /**
     * Re-seeds the random number generator. Calls the
     * [EntropySource.provideEntropy] method of all added sources of
     * entropy.
     *
     * @param secureRandom
     * the random number generator to re-seed
     */
    fun reseed(secureRandom: SecureRandom) {
        seedBuffer.clear()
        secureRandom.nextBytes(seedBuffer.array())
        for (source in entropySources) {
            val entropy = source.provideEntropy() ?: continue
            val wipeBuffer = entropy.duplicate()
            digest.update(entropy)
            wipe(wipeBuffer)
        }
        digest.update(TIME_ENTROPY_SOURCE.provideEntropy())
        digest.update(seedBuffer)
        seedBuffer.clear()
        // remove data from seedBuffer so it won't be retrievable

        // reuse
        try {
            digest.digest(
                    seedBuffer.array(), 0,
                    seedBuffer.capacity()
            )
        } catch (e: DigestException) {
            throw IllegalStateException(
                    "DigestException should not be thrown", e
            )
        }
        secureRandom.setSeed(seedBuffer.array())
        wipe(seedBuffer)
    }

    private fun wipe(buf: ByteBuffer) {
        while (buf.hasRemaining()) {
            buf.put(0.toByte())
        }
    }

    companion object {
        private const val DEFAULT_PSEUDO_RANDOM_NUMBER_GENERATOR = "SHA1PRNG"
        private val TIME_ENTROPY_SOURCE: EntropySource = object : EntropySource {
            val timeBuffer = ByteBuffer.allocate(
                    java.lang.Long.SIZE / java.lang.Byte.SIZE
                            * 2
            )

            override fun provideEntropy(): ByteBuffer? {
                timeBuffer.clear()
                timeBuffer.putLong(System.currentTimeMillis())
                timeBuffer.putLong(System.nanoTime())
                timeBuffer.flip()
                return timeBuffer
            }
        }

        /**
         * Generates an instance of a [SecureRandomStrengthener] that
         * generates and re-seeds instances of `"SHA1PRNG"`.
         *
         * @return the strengthener, never null
         */
        val instance: SecureRandomStrengthener
            get() = SecureRandomStrengthener(
                    DEFAULT_PSEUDO_RANDOM_NUMBER_GENERATOR
            )

        /**
         * Generates an instance of a [SecureRandomStrengthener] that
         * generates instances of the given argument. Note that the availability of
         * the given algorithm arguments in not tested until generation.
         *
         * @param algorithm
         * the algorithm indicating the [SecureRandom] instance to
         * use
         * @return the strengthener, never null
         */
        fun getInstance(algorithm: String?): SecureRandomStrengthener {
            return SecureRandomStrengthener(algorithm)
        }
    }
}