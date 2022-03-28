@file:Suppress("unused")

package me.qboi.generators.types

import java.util.*

class WordGenerator2 : WordGenerator {
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
        A, B
    }

    override fun generate(): String {
        val random = random()
        val len = random.nextInt(minSize, maxSize + 1)

        var named = isNamed
        var state: State? = State.A
        val sb = StringBuilder()
        for (i in 0 until len) {
            when (state) {
                State.A -> {
                    state = State.B
                    var choose = choose(random, a)
                    if (named) {
                        named = false
                        val c = choose[0]
                        val substring = choose.substring(1)
                        choose = ("" + c).uppercase(Locale.getDefault()) + substring
                    }
                    sb.append(choose)
                }
                State.B -> {
                    state = State.A
                    val choose = choose(random, b)
                    sb.append(choose)
                }
                else -> {
                    
                }
            }
        }
        return sb.toString()
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
            "sw", "th", "tr", "vl", "vr", "vy", "wr", "xyl", "zyl"
        )
        private val b = listOf(
            "a", "e", "i", "o", "u", "ee", "oo", "eo", "ou", "ue"
        )
        private val y = listOf(
            "rld", "ld", "d", "ne", "re", "ng", "nk", "sh", "xyl", "zyl"
        )
    }
}