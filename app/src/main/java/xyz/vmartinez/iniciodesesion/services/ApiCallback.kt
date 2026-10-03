package xyz.vmartinez.iniciodesesion.services

import xyz.vmartinez.iniciodesesion.models.TokenModel

interface ApiCallback<T> {
    fun onSuccess(token: TokenModel)
    fun onError(error: String)
}