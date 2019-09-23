package my.fetchsample

import android.app.Application
import android.util.Log
import com.tonyodev.fetch2.*
import com.tonyodev.fetch2.Download
import com.tonyodev.fetch2.FetchListener
import com.tonyodev.fetch2core.DownloadBlock
import org.greenrobot.eventbus.EventBus
import org.jetbrains.annotations.NotNull


class MainApplication : Application() {

    lateinit var fetch: Fetch

    companion object {

        lateinit var instance: MainApplication
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        val fetchConfiguration = FetchConfiguration.Builder(this)
            .setDownloadConcurrentLimit(3)
            .build()

        fetch = Fetch.getInstance(fetchConfiguration)

        val fetchListener = object : FetchListener {

            override fun onAdded(download: Download) {
            }

            override fun onDownloadBlockUpdated(download: Download, downloadBlock: DownloadBlock, totalBlocks: Int) {
            }

            override fun onError(download: Download, error: Error, throwable: Throwable?) {
            }

            override fun onStarted(download: Download, downloadBlocks: List<DownloadBlock>, totalBlocks: Int) {
            }

            override fun onWaitingNetwork(download: Download) {
            }

            override fun onQueued(@NotNull download: Download, waitingOnNetwork: Boolean) {
                Log.d("MainApplication", "onQueued $download")
                val listItemId = download.extras.getLong("list_item_id", -1)
                EventBus.getDefault().post(DownloadStartEvent(listItemId))
            }

            override fun onCompleted(@NotNull download: Download) {
                Log.d("MainApplication", "onCompleted $download")
                val listItemId = download.extras.getLong("list_item_id", -1)
                EventBus.getDefault().post(DownloadCompleteEvent(listItemId))
            }

            fun onError(@NotNull download: Download) {
                val error = download.error
            }

            override fun onProgress(
                @NotNull download: Download, etaInMilliSeconds: Long,
                downloadedBytesPerSecond: Long
            ) {
                Log.d("MainApplication", "onProgress $download")
                val listItemId = download.extras.getLong("list_item_id", -1)
                EventBus.getDefault().post(DownloadProgressEvent(listItemId, downloadedBytesPerSecond))
            }

            override fun onPaused(@NotNull download: Download) {

            }

            override fun onResumed(@NotNull download: Download) {

            }

            override fun onCancelled(@NotNull download: Download) {

            }

            override fun onRemoved(@NotNull download: Download) {

            }

            override fun onDeleted(@NotNull download: Download) {

            }
        }

        fetch.addListener(fetchListener)

        // fetch.removeListener(fetchListener)
    }

    override fun onTerminate() {
        super.onTerminate()
//
        fetch.removeAll()
    }

}
data class DownloadStartEvent(var id: Long)
data class DownloadProgressEvent(var id: Long, var downloadedBytesPerSecond: Long)
data class DownloadCompleteEvent(var id: Long)