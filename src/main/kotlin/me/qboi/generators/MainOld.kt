@file:Suppress("unused")

package me.qboi.generators

import me.qboi.generators.types.*
import java.util.*

class MainOld(args: Array<String>) {
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

    init {
        val random = Random()
        if (args.size == 1 && args[0] == "--sentence") {
            println(generateSentence(random, 5))
        } else {
            val sb = StringBuilder()
            for (i in 0 until SENTENCES) {
                sb.append(generateSentence(random, random.nextInt(5, 10)))
            }
            println(sb)
            val generator = ParagraphGenerator(ParagraphGenerator.Config()
                .minCount(5)
                .maxCount(24)
                .sentence(SentenceGenerator.Config()
                    .minCount(3)
                    .maxCount(8)
                    .word(WordGenerator5.Config()
                        .minSize(2)
                        .maxSize(5)
                    )
                )
            )
            println(generator.generate())
            println(generator.generate())
            println(generator.generate())
            val sentence = SentenceGenerator(SentenceGenerator.Config()
                .minCount(5)
                .maxCount(8)
                .word(WordGenerator5.Config()
                    .minSize(2)
                    .maxSize(7)
                )
            )
            println(sentence.generate())
            println(sentence.generate())
            println(sentence.generate())
            val word = WordGenerator3(WordGenerator3.Config()
                .maxSize(7)
                .minSize(2)
            )
            println(word.generate())
            println(word.generate())
            println(word.generate())
            println(word.generate())
            println(word.generate())
            println(word.generate())
            println(word.generate())
            println(word.generate())
            println(word.generate())
            println(word.generate())
            println(word.generate())
            println(word.generate())
            println(word.generate())
            println(word.generate())
            println(word.generate())
            println(word.generate())
            println(word.generate())
            println(word.generate())
            println(word.generate())
        }
    }

    private fun generateSentence(random: Random, count: Int): String {
        val list: MutableList<String> = ArrayList()
        var firstLetter = true
        for (i in 0 until count) {
            val s = generateWord(random, firstLetter)
            list.add(s)
            firstLetter = false
        }
        return java.lang.String.join(" ", list) + choose(random, z)
    }

    enum class State {
        A, B
    }

    private fun generateWord(random: Random, firstLetter: Boolean): String {
        @Suppress("NAME_SHADOWING") var firstLetter = firstLetter
        val len = random.nextInt(MIN_WORD_SIZE, MAX_WORD_SIZE)

        var state: State? = State.A
        val sb = StringBuilder()
        for (i in 0 until len) {
            when (state) {
                State.A -> {
                    state = State.B
                    var choose = choose(random, a)
                    if (firstLetter) {
                        firstLetter = false
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

    private fun <T> choose(rand: Random, list: List<T>): T {
        return list[rand.nextInt(list.size)]
    }

    companion object {
        private const val MIN_WORD_SIZE = 2
        private const val MAX_WORD_SIZE = 5
        private const val SENTENCES = 30
        @JvmStatic
        fun main(args: Array<String>) {
            MainOld(args)
        }
    }
}