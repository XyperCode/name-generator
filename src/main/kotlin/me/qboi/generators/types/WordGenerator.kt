package me.qboi.generators.types

abstract class WordGenerator : TextGenerator {
    constructor()
    constructor(seed: Long) : super(seed) {}
}