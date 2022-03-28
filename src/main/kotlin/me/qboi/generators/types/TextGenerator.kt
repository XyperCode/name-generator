package me.qboi.generators.types

import java.util.*

abstract class TextGenerator @JvmOverloads constructor(seed: Long = System.nanoTime()) : Generator<String>() {
    private var random: Random

    init {
        random = Random(seed)
    }

    protected fun reset() {
        random = Random()
    }

    protected fun reset(seed: Long) {
        random = Random(seed)
    }

    protected fun random(): Random {
        return random
    }

    protected fun <T> choose(rand: Random, list: List<T>): T {
        return list[rand.nextInt(list.size)]
    }
}