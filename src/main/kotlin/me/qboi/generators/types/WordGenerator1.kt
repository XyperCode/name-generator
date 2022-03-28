@file:Suppress("unused")

package me.qboi.generators.types

import me.qboi.generators.MainOld
import java.util.*

class WordGenerator1 : WordGenerator {
    private val a = listOf(
        "b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "qu", "r", "s", "t", "v", "w", "x", "y", "z", "br",
        "bl", "ck", "cl", "cr", "dr", "fl", "fr", "gl", "gn", "gr", "hc", "hg", "kl", "kn", "kr", "ks", "mr", "ng",
        "nk", "pc", "ph", "pr", "ps", "rh", "rl", "rt", "sch", "sc", "sck", "sh", "sl", "sn", "sr", "st", "sw", "th",
        "tr", "vl", "vr", "vy", "wr", "xyl", "zyl"
    )
    private val b = listOf(
        "a", "e", "i", "o", "u", "ee", "oo", "eo", "ou", "ue"
    )
    private val y = listOf(
        "rld", "ld", "d", "ne", "re", "ng", "nk", "sh", "xyl", "zyl"
    )
    private val z = listOf(
        ". ", ", "
    )

    private var firstLetter: Boolean

    constructor(firstLetter: Boolean) {
        this.firstLetter = firstLetter
    }

    constructor(seed: Long, firstLetter: Boolean) : super(seed) {
        this.firstLetter = firstLetter
    }

    override fun generate(): String {
        val random = random()

        @Suppress("NAME_SHADOWING") var firstLetter = firstLetter
        val len = random.nextInt(MIN_WORD_SIZE, MAX_WORD_SIZE)

        var state: MainOld.State? = MainOld.State.A
        val sb = StringBuilder()
        for (i in 0 until len) {
            when (state) {
                MainOld.State.A -> {
                    state = MainOld.State.B
                    var choose = choose(random, a)
                    if (firstLetter) {
                        firstLetter = false
                        val c = choose[0]
                        val substring = choose.substring(1)
                        choose = ("" + c).uppercase(Locale.getDefault()) + substring
                    }
                    sb.append(choose)
                }
                MainOld.State.B -> {
                    state = MainOld.State.A
                    val choose = choose(random, b)
                    sb.append(choose)
                }
                else -> {

                }
            }
        }
        return sb.toString()
    }

    companion object {
        private const val MIN_WORD_SIZE = 2
        private const val MAX_WORD_SIZE = 5
    }
}