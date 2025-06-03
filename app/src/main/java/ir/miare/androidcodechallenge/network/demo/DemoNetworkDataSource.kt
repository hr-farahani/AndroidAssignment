package ir.miare.androidcodechallenge.network.demo

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import ir.miare.androidcodechallenge.network.JvmUnitTestDemoAssetManager
import ir.miare.androidcodechallenge.network.NetworkDataSource
import ir.miare.androidcodechallenge.network.di.Dis
import ir.miare.androidcodechallenge.network.di.Dispatcher
import ir.miare.androidcodechallenge.network.model.FakeData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.BufferedReader
import javax.inject.Inject

class DemoNetworkDataSource @Inject constructor(
    @Dispatcher(Dis.IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
    private val assets: DemoAssetManager = JvmUnitTestDemoAssetManager,
) : NetworkDataSource {

    override suspend fun getData(): List<FakeData> {
        return getDataFromJsonFile(FAKE_DATA_ASSET)
    }

    @OptIn(ExperimentalSerializationApi::class)
    private suspend inline fun <reified T> getDataFromJsonFile(fileName: String): List<T> =
        withContext(ioDispatcher) {
            assets.open(fileName).use { inputStream ->
                if (SDK_INT <= M) {
                    /**
                     * On API 23 (M) and below we must use a workaround to avoid an exception being
                     * thrown during deserialization. See:
                     * https://github.com/Kotlin/kotlinx.serialization/issues/2457#issuecomment-1786923342
                     */
                    inputStream.bufferedReader().use(BufferedReader::readText)
                        .let(networkJson::decodeFromString)
                } else {
                    networkJson.decodeFromStream(inputStream)
                }
            }
        }

    companion object {
        private const val FAKE_DATA_ASSET = "data.json"
    }
}