package com.anderson.apigithub_mvvm.feature.main.viewmodel

import br.com.anderson.apigithub_mvvm.ui.generic.base.viewmodel.BaseViewModel
import com.anderson.apigithub_mvvm.service.MainRepository
import javax.inject.Inject

/**
 * Created by anderson on 21/09/19.
 */
class MainViewModel @Inject constructor(val repository: MainRepository) : BaseViewModel() {
}