@file:Suppress("unused")

package me.qboi.generators.types

class SentenceGenerator : TextGenerator {
    var minCount: Int
    var maxCount: Int
    var isRandomPunctuation: Boolean
    private val wordGenConfig: WordGenerator5.Config
    private val wordGen: WordGenerator5

    constructor(config: Config) {
        wordGenConfig = config.wordConfig
        wordGen = WordGenerator5(config.wordConfig)
        minCount = config.minCount
        maxCount = config.maxCount
        isRandomPunctuation = config.randomPunctuation
    }

    constructor(seed: Long, config: Config) : super(seed) {
        wordGenConfig = config.wordConfig
        wordGen = WordGenerator5(seed, config.wordConfig)
        minCount = config.minCount
        maxCount = config.maxCount
        isRandomPunctuation = config.randomPunctuation
    }

    override fun generate(): String {
        val random = random()
        val list: MutableList<String?> = ArrayList()
        val count: Int = if (minCount >= maxCount) {
            minCount
        } else {
            random.nextInt(minCount, maxCount + 1)
        }
        wordGen.isNamed = true
        for (i in 0 until count) {
            val s = generateWord()
            list.add(s)
            wordGen.isNamed = false
        }
        wordGen.setConfig(wordGenConfig)
        return if (isRandomPunctuation) {
            java.lang.String.join(" ", list) + choose(random,
                punctuations)
        } else {
            java.lang.String.join(" ", list)
        }
    }

    private fun generateWord(): String {
        return wordGen.generate()
    }

    class Config {
        var minCount = 2
        var maxCount = 5
        var wordConfig = WordGenerator5.Config()
        var randomPunctuation = false
        fun word(config: WordGenerator5.Config): Config {
            wordConfig = config
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

        fun randomPunctuation(): Config {
            randomPunctuation = true
            return this
        }
    }

    companion object {
        private val punctuations: List<String> = listOf(
            ". ", ", "
        )
    }
}