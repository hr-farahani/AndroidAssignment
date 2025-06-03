package ir.miare.androidcodechallenge.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import ir.miare.androidcodechallenge.database.DB
import ir.miare.androidcodechallenge.network.model.FakeData
import timber.log.Timber

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    companion object {
        private const val TAG = "SeedDatabaseWorker"
        const val KEY_FILENAME = "FAKE_DATA_FILENAME"
    }

    override suspend fun doWork(): Result {
        return try {
            val filename = inputData.getString(KEY_FILENAME)
            if (filename != null) {
                applicationContext.assets.open(filename).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val fakeDataType = object : TypeToken<List<FakeData>>() {}.type
                        val data: List<FakeData> = Gson().fromJson(jsonReader, fakeDataType)

                        Timber.tag(TAG).d(data[0].league.name)

                        val database = DB.getInstance(applicationContext)

                        Result.success()
                    }
                }
            } else {
                Result.failure()
            }
        } catch (e: Exception) {
            Timber.tag(TAG).e(e)
            Result.failure()
        }
    }
}