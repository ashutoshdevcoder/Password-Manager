package com.passman.helper

import java.nio.ByteBuffer




interface EntropySource {
    fun provideEntropy(): ByteBuffer?

}
