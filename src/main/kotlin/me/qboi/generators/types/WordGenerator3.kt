@file:Suppress("unused")

package me.qboi.generators.types

import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.max

class WordGenerator3 : WordGenerator {
    var minSize: Int
    var maxSize: Int
    var isNamed: Boolean
    var seed: Long = 0

    constructor(config: Config) {
        minSize = config.minSize
        maxSize = config.maxSize
        isNamed = config.named
    }

    constructor(seed: Long, config: Config) : super(seed) {
        minSize = config.minSize
        maxSize = config.maxSize
        isNamed = config.named
    }

    enum class State {
        A, B, C, D, Y
    }

    override fun generate(): String {
        val random = random()
        val len = random.nextInt(max(minSize, 3), maxSize + 1)

        val named = AtomicBoolean(isNamed)
        var state: State = if (random.nextBoolean()) State.C else State.D
        val sb = StringBuilder()
        for (i in 0 until len) {
            when (state) {
                State.A -> {
                    state = State.B
                    if (i == len - 1) {
                        appendRandom(sb, named, random, y)
                        continue
                    }
                    appendRandom(sb, named, random, a)
                }
                State.B -> {
                    state = State.A
                    if (i == len - 1) {
                        appendRandom(sb, named, random, z)
                        continue
                    }
                    appendRandom(sb, named, random, b)
                }
                State.C -> {
                    state = State.B
                    appendRandom(sb, named, random, c)
                }
                State.D -> {
                    state = State.A
                    appendRandom(sb, named, random, d)
                }
                else -> throw IllegalStateException("Unexpected state: $state")
            }
        }
        return sb.toString()
    }

    private fun appendRandom(sb: StringBuilder, named: AtomicBoolean, random: Random, list: List<String>) {
        var choose = choose(random, list)
        if (named.get()) {
            named.set(false)
            val c = choose[0]
            val substring = choose.substring(1)
            choose = ("" + c).uppercase(Locale.getDefault()) + substring
        }
        sb.append(choose)
    }

    fun setConfig(config: Config) {
        minSize = config.minSize
        maxSize = config.maxSize
        isNamed = config.named
    }

    class Config {
        var minSize = 2
        var maxSize = 5
        var named = false
        fun minSize(size: Int): Config {
            minSize = size
            return this
        }

        fun maxSize(size: Int): Config {
            maxSize = size
            return this
        }

        fun named(): Config {
            named = true
            return this
        }

        fun nonNamed(): Config {
            named = false
            return this
        }
    }

    companion object {
        private val a = listOf(
            "b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "qu", "r", "s", "t", "v", "w", "x", "y", "z",
            "br", "bl", "ck", "cl", "cr", "dr", "fl", "fr", "gl", "gn", "gr", "hc", "hg", "kl", "kn", "kr", "ks", "mr",
            "ng", "nk", "pc", "ph", "pr", "ps", "rh", "rl", "rt", "sch", "sc", "sck", "sh", "sl", "sn", "sr", "st",
            "sw", "th", "tr", "vl", "vr", "vy", "wr"
        )
        private val b = listOf(
            "a", "e", "i", "o", "u", "ee", "oo", "eo", "ou", "ue"
        )
        private val c = listOf(
            "b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "r", "s", "t", "v", "w", "x", "y", "z"
        )
        private val d = listOf(
            "a", "e", "i", "o", "u", "qui", "que", "quo", "qua"
        )
        private val y = listOf(
            "rld", "ld", "d", "ne", "re", "sh", "xyl", "zyl", "x", "th", "ph"
        )
        private val z = listOf(
            "ed", "yl", "er", "em", "in", "ix", "ux", "ox", "ex", "yx", "ium", "um", "ick", "il", "ing", "ink"
        )
    }
}