package com.example.cheqapp.domain.use_case.get_coin

import com.example.cheqapp.common.Resources
import com.example.cheqapp.data.remote.dto.toCoinDetail
import com.example.cheqapp.domain.model.Coin
import com.example.cheqapp.domain.model.CoinDetail
import com.example.cheqapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(coinId: String): Flow<Resources<CoinDetail>> = flow {
        try {
            emit(Resources.Loading<CoinDetail>())
            val coin = repository.getCoinById(coinId).toCoinDetail()
            emit(Resources.Success<CoinDetail>(coin))
        } catch (e: HttpException) {
            emit(Resources.Error<CoinDetail>(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resources.Error<CoinDetail>("Couldn't reach server. Check Your Internet Connection."))
        }
    }
}