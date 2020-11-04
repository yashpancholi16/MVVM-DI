package com.yash.myproject

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.yash.myproject.homescreen.repository.ISuperHeroRepository
import com.yash.myproject.homescreen.repository.SuperHeroRepository
import com.yash.myproject.homescreen.viewmodel.SuperHeroViewModel
import com.yash.myproject.networking.ApiProvider
import com.yash.myproject.networking.IApiProvider
import com.yash.myproject.networking.INetworkService
import com.yash.myproject.networking.NetworkService
import com.yash.myproject.utils.ViewModelFactory
import com.yash.myproject.utils.bindViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ProjectApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {

        bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(this) }
        bind<IApiProvider>() with singleton {
            ApiProvider()
        }

        bind<ISuperHeroRepository>() with provider {
            SuperHeroRepository(instance())
        }
        bindViewModel<SuperHeroViewModel>() with provider {
            SuperHeroViewModel(instance())
        }

        bind<INetworkService>() with singleton {
            NetworkService(instance())
        }
    }

}