@file:Suppress("unused")

package me.qboi.generators.types

import java.util.*

class WordStyle(config: Config) {
    val startA: List<String>
    val startB: List<String>
    val middleA: List<String>
    val middleB: List<String>
    val endA: List<String>
    val endB: List<String>

    init {
        startA = Collections.unmodifiableList(config.startA)
        startB = Collections.unmodifiableList(config.startB)
        middleA = Collections.unmodifiableList(config.middleA)
        middleB = Collections.unmodifiableList(config.middleB)
        endA = Collections.unmodifiableList(config.endA)
        endB = Collections.unmodifiableList(config.endB)
    }

    class Config {
        var startA = listOf<String>()
        var startB = listOf<String>()
        var middleA = listOf<String>()
        var middleB = listOf<String>()
        var endA = listOf<String>()
        var endB = listOf<String>()
        fun setStartA(startA: List<String>): Config {
            this.startA = startA
            return this
        }

        fun setStartB(startB: List<String>): Config {
            this.startB = startB
            return this
        }

        fun setMiddleA(middleA: List<String>): Config {
            this.middleA = middleA
            return this
        }

        fun setMiddleB(middleB: List<String>): Config {
            this.middleB = middleB
            return this
        }

        fun setEndA(endA: List<String>): Config {
            this.endA = endA
            return this
        }

        fun setEndB(endB: List<String>): Config {
            this.endB = endB
            return this
        }
    }
}