package me.qboi.generators.words

import me.qboi.generators.types.*

enum class Version(val versionName: String, private val builder: (Long) -> WordGenerator) {
    GEN_1("v1.0", { WordGenerator1(it, true) }),
    GEN_2("v2.0", { WordGenerator2(it, WordGenerator2.Config().minSize(3).maxSize(7).named()) }),
    GEN_3("v3.0", { WordGenerator3(it, WordGenerator3.Config().minSize(3).maxSize(7).named()) }),
    GEN_4("v4.0", { WordGenerator4(it, WordGenerator4.Config().minSize(3).maxSize(7).named()) }),
    GEN_5("v5.0", { WordGenerator5(it, WordGenerator5.Config().minSize(3).maxSize(7).named()) }),
    ;

    fun build(seed: Long): WordGenerator {
        return builder(seed)
    }
}