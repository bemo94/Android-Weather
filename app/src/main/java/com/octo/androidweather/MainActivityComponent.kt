package com.octo.androidweather

import dagger.Component

@Component(
    modules = [MainActivityModule::class]
)
interface MainActivityComponent {
    fun inject(activity: MainActivity)
}