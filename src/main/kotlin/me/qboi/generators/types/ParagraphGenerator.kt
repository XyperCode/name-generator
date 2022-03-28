@file:Suppress("unused")

package me.qboi.generators.types

class ParagraphGenerator : TextGenerator {
    private val sentenceCfg: SentenceGenerator.Config
    private val sentenceGen: SentenceGenerator
    private val minCount: Int
    private val maxCount: Int

    constructor(config: Config) {
        sentenceCfg = config.sentence
        sentenceGen = SentenceGenerator(config.sentence)
        minCount = config.minCount
        maxCount = config.maxCount
    }

    constructor(seed: Long, config: Config) : super(seed) {
        sentenceCfg = config.sentence
        sentenceGen = SentenceGenerator(seed, config.sentence)
        minCount = config.minCount
        maxCount = config.maxCount
    }

    override fun generate(): String {
        val random = random()
        sentenceGen.isRandomPunctuation = true
        val count: Int = if (minCount >= maxCount) {
            minCount
        } else {
            random.nextInt(minCount, maxCount + 1)
        }
        val sb = StringBuilder()
        for (i in 0 until count) {
            sb.append(generateSentence())
        }
        val out = sb.toString().trim { it <= ' ' }
        return out.substring(0, out.length - 1) + "."
    }

    private fun generateSentence(): String {
        return sentenceGen.generate()
    }

    class Config {
        var minCount = 2
        var maxCount = 5
        var sentence = SentenceGenerator.Config()
        fun sentence(config: SentenceGenerator.Config): Config {
            sentence = config
            return this
        }

        fun minCount(count: Int): Config {
            minCount = count
            return this
        }

        fun maxCount(count: Int): Config {
            maxCount = count
            return this
        }

        fun count(count: Int): Config {
            minCount = count
            maxCount = count
            return this
        }
    }
}