package com.aglushkov.wordteacher.shared.wordteacher.service

import com.aglushkov.wordteacher.shared.wordteacher.model.WordTeacherWord
import com.aglushkov.wordteacher.shared.wordteacher.repository.Config
import com.aglushkov.wordteacher.shared.wordteacher.repository.ServiceMethodParams

interface WordTeacherWordService {
    val id: Int // TODO: parse id from a config
        get() {
            return 31 * baseUrl.hashCode() + 31 * key.hashCode()
        }

    var type: Config.Type
    var baseUrl: String
    var key: String
    var methodParams: ServiceMethodParams

    suspend fun define(word: String): List<WordTeacherWord>
}