package com.example.firstkmmapp.android.di

import com.example.firstkmmapp.android.AndroidCharacterListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val androidModule = module {
    viewModelOf(::AndroidCharacterListViewModel)
}