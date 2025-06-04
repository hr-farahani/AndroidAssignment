package ir.miare.androidcodechallenge.network

import ir.miare.androidcodechallenge.network.model.FakeData

interface NetworkDataSource {

    suspend fun getData(): List<FakeData>
}