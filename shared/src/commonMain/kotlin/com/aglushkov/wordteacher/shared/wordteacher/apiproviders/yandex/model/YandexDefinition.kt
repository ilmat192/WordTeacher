package com.aglushkov.wordteacher.apiproviders.yandex.model

import com.aglushkov.wordteacher.shared.wordteacher.model.WordTeacherDefinition
import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize
import kotlinx.serialization.SerialName


@Parcelize
data class YandexDefinition(
    @SerialName("ex") val examples: List<YandexExample>?,
    @SerialName("mean") val meanings: List<YandexMeaning>?,
    @SerialName("syn") val synonyms: List<YandexSynonym>?,

    // Universal attributes
    @SerialName("text") val text: String,
    @SerialName("num") val num: String?,
    @SerialName("pos") val pos: String?,
    @SerialName("gen") val gender: String?,
    @SerialName("asp") val asp: String?
) : Parcelable

fun YandexDefinition.asWordTeacherDefinition(): WordTeacherDefinition? {
    val resultExamples = examples.orEmpty().map { it.text }
    val resultSynonyms = synonyms.orEmpty().map { it.text }
    // TODO: support meanings for non english definitions

    return WordTeacherDefinition(listOf(text),
            resultExamples,
            resultSynonyms,
            null)
}