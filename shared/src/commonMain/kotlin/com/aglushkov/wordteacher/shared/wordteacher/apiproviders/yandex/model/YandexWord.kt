package com.aglushkov.wordteacher.apiproviders.yandex.model

import com.aglushkov.wordteacher.shared.wordteacher.model.WordTeacherDefinition
import com.aglushkov.wordteacher.shared.wordteacher.model.WordTeacherWord
import com.aglushkov.wordteacher.shared.wordteacher.model.fromString
import com.aglushkov.wordteacher.shared.wordteacher.repository.Config
import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize
import kotlinx.serialization.SerialName


@Parcelize
data class YandexWord(
    @SerialName("tr") val definitions: List<YandexDefinition>,
    @SerialName("ts") val transcription: String?,

    // Universal attributes
    @SerialName("text") val text: String,
    @SerialName("num") val num: String?,
    @SerialName("pos") val pos: String?,
    @SerialName("gen") val gender: String?,
    @SerialName("asp") val asp: String?
) : Parcelable

fun YandexWord.asWordTeacherWord(): WordTeacherWord? {
    val map: MutableMap<WordTeacherWord.PartOfSpeech, List<WordTeacherDefinition>> = mutableMapOf()
    for (definition in definitions) {
        val partOfSpeech = WordTeacherWord.PartOfSpeech.fromString(definition.pos)
        definition.asWordTeacherDefinition()?.let {
            var list = map[partOfSpeech] as? MutableList<WordTeacherDefinition>
            if (list == null) {
                list = mutableListOf()
                map[partOfSpeech] = list
            }

            list.add(it)
        }
    }

    return WordTeacherWord(text,
            transcription,
            map,
            listOf(Config.Type.Yandex))
}