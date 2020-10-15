package com.aglushkov.wordteacher.di

import com.aglushkov.wordteacher.androidApp.di.FragmentComp
import com.aglushkov.wordteacher.androidApp.features.definitions.views.DefinitionsFragment
import com.aglushkov.wordteacher.androidApp.general.ItemViewBinder
import com.aglushkov.wordteacher.shared.features.definitions.repository.WordRepository
import com.aglushkov.wordteacher.shared.general.connectivity.ConnectivityManager
import com.aglushkov.wordteacher.shared.repository.ConfigConnectParamsStatRepository
import com.aglushkov.wordteacher.shared.repository.ConfigRepository
import com.aglushkov.wordteacher.shared.repository.ServiceRepository
import com.aglushkov.wordteacher.shared.repository.WordTeacherWordServiceFactory
import com.aglushkov.wordteacher.shared.service.ConfigService
import dagger.Component


@FragmentComp
@Component(dependencies = [DefinitionsDependencies::class], modules = [DefinitionsModule::class])
public interface DefinitionsComponent {
    fun getItemViewBinder(): ItemViewBinder
    fun injectDefinitionsFragment(fragment: DefinitionsFragment)
}


interface DefinitionsDependencies {
    fun getConfigService(): ConfigService
    fun getConfigRepository(): ConfigRepository
    fun getConfigConnectParamsStatRepository(): ConfigConnectParamsStatRepository
    fun getServiceRepository(): ServiceRepository
    fun getWordTeacherWordServiceFactory(): WordTeacherWordServiceFactory
    fun getWordRepository(): WordRepository

    fun getConnectivityManager(): ConnectivityManager
}