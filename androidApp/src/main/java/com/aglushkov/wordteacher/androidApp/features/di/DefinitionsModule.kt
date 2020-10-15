package com.aglushkov.wordteacher.di

import com.aglushkov.wordteacher.androidApp.di.AppComp
import com.aglushkov.wordteacher.androidApp.di.FragmentComp
import com.aglushkov.wordteacher.androidApp.features.definitions.blueprints.DefinitionsDisplayModeBlueprint
import com.aglushkov.wordteacher.androidApp.features.definitions.blueprints.WordDefinitionBlueprint
import com.aglushkov.wordteacher.androidApp.features.definitions.blueprints.WordDividerBlueprint
import com.aglushkov.wordteacher.androidApp.features.definitions.blueprints.WordExampleBlueprint
import com.aglushkov.wordteacher.androidApp.features.definitions.blueprints.WordPartOfSpeechBlueprint
import com.aglushkov.wordteacher.androidApp.features.definitions.blueprints.WordSubHeaderBlueprint
import com.aglushkov.wordteacher.androidApp.features.definitions.blueprints.WordSynonymBlueprint
import com.aglushkov.wordteacher.androidApp.features.definitions.blueprints.WordTitleBlueprint
import com.aglushkov.wordteacher.androidApp.features.definitions.blueprints.WordTranscriptionBlueprint
import com.aglushkov.wordteacher.androidApp.general.ItemViewBinder
import dagger.Module
import dagger.Provides

@Module
class DefinitionsModule {
    @FragmentComp
    @Provides
    fun createItemViewBinder(): ItemViewBinder {
        return ItemViewBinder()
            .addBlueprint(DefinitionsDisplayModeBlueprint())
            .addBlueprint(WordDefinitionBlueprint())
            .addBlueprint(WordDividerBlueprint())
            .addBlueprint(WordExampleBlueprint())
            .addBlueprint(WordPartOfSpeechBlueprint())
            .addBlueprint(WordSubHeaderBlueprint())
            .addBlueprint(WordSynonymBlueprint())
            .addBlueprint(WordTitleBlueprint())
            .addBlueprint(WordTranscriptionBlueprint())
    }
}