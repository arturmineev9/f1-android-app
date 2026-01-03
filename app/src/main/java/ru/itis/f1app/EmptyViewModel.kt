package ru.itis.f1app

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmptyViewModel @Inject constructor() : ViewModel()
