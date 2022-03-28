@file:Suppress("unused")

package me.qboi.generators.types

import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.max

class WordGenerator5 : WordGenerator {
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
        A, B, C, D, E1, E2, Y, Z
    }

    override fun generate(): String {
        val random = random()
        val len = random.nextInt(max(minSize, 2), maxSize + 1)

        val named = AtomicBoolean(isNamed)
        var state: State = if (random.nextBoolean()) State.C else State.D
        val sb = StringBuilder()
        for (i in 0 until len) {
            when (state) {
                State.A -> {
                    state = State.B
                    if (i == len - 1) {
                        appendRandom(sb, named, random, y, ya, yb, yc, yd)
                        continue
                    }
                    appendRandom(sb, named, random, a)
                }
                State.B -> {
                    state = if (i < len - 3) {
                        if (random.nextBoolean()) {
                            State.A
                        } else {
                            State.E1
                        }
                    } else {
                        State.A
                    }
                    if (i == len - 1) {
                        appendRandom(sb, named, random, za, zb, zc, zd, ze)
                        continue
                    }
                    appendRandom(sb, named, random, b)
                }
                State.C -> {
                    state = State.B
                    appendRandom(sb, named, random, c, ca, cb)
                }
                State.D -> {
                    state = if (i < len - 3) {
                        if (random.nextBoolean()) State.A else State.E1
                    } else {
                        State.A
                    }
                    appendRandom(sb, named, random, d, da, db)
                }
                State.E1 -> {
                    state = State.E2
                    appendRandom(sb, named, random, e)
                }
                State.E2 -> {
                    state = State.B
                    appendRandom(sb, named, random, e)
                }
                else -> throw IllegalStateException("Unexpected state: $state")
            }
        }
        return sb.toString()
    }

    private fun appendRandom(sb: StringBuilder, named: AtomicBoolean, random: Random, vararg lists: List<String>) {
        var list: List<String>? = null
        for (l in lists) {
            if (random.nextBoolean()) {
                list = l
                break
            }
        }
        if (list == null) {
            list = lists[0]
        }
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
            "ng", "nk", "pc", "ph", "pr", "ps", "rh", "rl", "rt", "sch", "sc", "sh", "sl", "sn", "sr", "st", "sw", "th",
            "tr", "vl", "vr", "vy", "wr"
        )
        private val b = listOf(
            "a", "e", "i", "o", "u", "ee", "oo", "eo", "ou", "ue"
        )
        private val c = listOf(
            "b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "r", "s", "t", "v", "w", "x", "y", "z"
        )
        private val ca = listOf(
            "br", "bl", "cr", "dr", "fr", "gr", "gl", "gn", "kr", "kl", "kn", "pr", "pl", "sr", "sl", "sn", "s"
        )
        private val cb = listOf(
            "abs", "ont", "sch", "scr", "her"
        )
        private val d = listOf(
            "a", "e", "i", "o", "u", "qu"
        )
        private val da = listOf(
            "de", "re", "be", "ge", "quo"
        )
        private val db = listOf(
            "qui", "que", "quo"
        )
        private val e = listOf(
            "b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "r", "s", "t", "v", "w", "x", "z"
        )
        private val y = listOf(
            "rld", "ld", "d", "ne", "re", "sh", "xyl", "zyl", "x", "th", "ph"
        )
        private val ya = listOf(
            "d", "r", "s", "m", "th", "ph"
        )
        private val yb = listOf(
            "ld", "nd", "rd", "rs"
        )
        private val yc = listOf(
            "rld", "mt", "rt", "st", "pt"
        )
        private val yd = listOf(
            "xyl", "zyl", "byl"
        )
        private val za = listOf(
            "e", "ed", "er", "en", "em", "im", "in", "an", "ian", "iam", "ing"
        )
        private val zb = listOf(
            "ink", "ick", "ock", "ong", "eng", "us", "urt", "erd", "ert", "erl", "orl", "url"
        )
        private val zc = listOf(
            "urd", "urt", "ird", "ord", "irt"
        )
        private val zd = listOf(
            "yl", "ix", "ux", "ox", "ex", "yx", "um", "il"
        )
        private val ze = listOf(
            "ium"
        )
    }
}