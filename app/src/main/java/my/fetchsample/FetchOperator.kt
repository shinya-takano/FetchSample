package my.fetchsample

import android.util.Log
import com.tonyodev.fetch2.Priority
import com.tonyodev.fetch2.Request
import com.tonyodev.fetch2core.Extras
import com.tonyodev.fetch2core.Func
import com.tonyodev.fetch2.Download
import kotlinx.coroutines.runBlocking


object FetchOperator {

    fun getDownloads(): List<Download> {
        val result = arrayListOf<Download>() // TODO blockしてない blockしてればOKっぽい
        runBlocking {
            MainApplication.instance.fetch.getDownloads(Func {
                result.addAll(it)
            })
        }
        return result
    }

    fun enqueue(urls: ArrayList<ListItem>) {

        val fetch = MainApplication.instance.fetch

        urls.forEach {

            val url = it.downloadUrl
            val file = "${MainApplication.instance.filesDir.absolutePath}/downloads/${it.id}.jpg"

            val request = Request(url, file)
            request.priority = Priority.HIGH
            request.extras = Extras(hashMapOf("list_item_id" to it.id.toString()))
            // request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG")
            fetch.enqueue(request,
                Func { updatedRequest ->
                    Log.d("MainApplication", updatedRequest.fileUri.toString())
                },
                Func { error ->
                    Log.d("MainApplication", error.httpResponse?.code?.toString())
                }
            )
        }
    }


}