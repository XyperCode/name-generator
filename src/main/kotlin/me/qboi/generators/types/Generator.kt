package me.qboi.generators.types

abstract class Generator<T> {
    abstract fun generate(): T
}