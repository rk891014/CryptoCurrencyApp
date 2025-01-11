package com.example.cheqapp.presentation.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cheqapp.common.Resources
import com.example.cheqapp.domain.model.Coin
import com.example.cheqapp.domain.use_case.get_coins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
): ViewModel() {

//    What do our viewmodels now contains -> because we removed most of business logic from viewmodels to usecases
//    main purpose of viewmodels is to maintain our UI states
//    for single screen we create one state object, to show progress bar, is there an error, show coins list

    private val _state = mutableStateOf(CoinListState())
    val state: State<CoinListState> = _state
    private var coinList: List<Coin>? = emptyList()

    init {
        getCoins()
    }

    private fun getCoins() {
        getCoinsUseCase().onEach { result ->
            when (result) {
                is Resources.Loading -> {
                    _state.value = CoinListState(isLoading = true)
                }
                is Resources.Success -> {
                    coinList = result.data?.toList()
                    _state.value = CoinListState(coins = result.data ?: emptyList())
                }
                is Resources.Error -> {
                    _state.value = CoinListState(error = result.message ?: "An unexpected error occured")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onSearchTextChange(searchedStr: String?) {
        val searchList = coinList?.filter {
            it.name.contains(searchedStr.orEmpty(), ignoreCase = true)
        }

        if(searchList?.isEmpty() == true){
            _state.value = CoinListState(isEmpty = true)
        } else {
            _state.value = CoinListState(coins = searchList ?: emptyList())
        }

    }

}