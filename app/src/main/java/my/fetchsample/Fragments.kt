package my.fetchsample

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_1.*
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.EventBus






class Fragment1: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val downloads = FetchOperator.getDownloads()
        val orgDataList = createData()
        for (listItem in orgDataList) {
            for (download in downloads) {
                val listItemId = download.extras.getLong("list_item_id", -1)
                if (listItem.id == listItemId) {
                    listItem.statusText = download.status.name
                    break
                }
            }
        }

        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapter = RecyclerAdapter(activity as MainActivity, orgDataList, object : RecyclerViewHolder.ItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    (activity as MainActivity).replaceFragment(Fragment2())
                }
            })
        }

        download_button.setOnClickListener {
            FetchOperator.enqueue(createData())
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: DownloadStartEvent) {
        Log.d("Fragment1", "DownloadStartEvent: " + event.id.toString())
        val adapter = recycler_view.adapter as RecyclerAdapter
        adapter.changeStatus(event.id, "ダウンロード中")
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: DownloadProgressEvent) {
        Log.d("Fragment1", "DownloadProgressEvent: " + event.id.toString())
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: DownloadCompleteEvent) {
        Log.d("Fragment1", "DownloadCompleteEvent: " + event.id.toString())
        val adapter = recycler_view.adapter as RecyclerAdapter
        adapter.changeStatus(event.id, "ダウンロード終了")
    }

    private fun createData(): ArrayList<ListItem> {
        // ダウンロード未 -> ダウンロード中 -> ダウンロード終了
        return arrayListOf(
            ListItem(0, "ダウンロード未", 0, "https://3.bp.blogspot.com/--MeNYybnZlA/W3abFrCdEwI/AAAAAAABN-Y/9kGQn4yLNaEjKCqFmMJA654qtHoJMe-qgCLcBGAs/s800/cat3_1_question.png")
            ,ListItem(1, "ダウンロード未",0, "https://2.bp.blogspot.com/-ZArsHhK7b9c/W3abF17xoFI/AAAAAAABN-c/CTwvqbJdeWY3AFI0EMKFYXbCK5gAtUuDACLcBGAs/s800/cat3_2_heart.png")
            ,ListItem(2, "ダウンロード未",0, "https://4.bp.blogspot.com/-dDXlC-Li85o/W3abGvoG7kI/AAAAAAABN-g/LkHmv8_ntaMmxMZmLNOm1mFmKb8wAEDRQCLcBGAs/s800/cat3_3_sleep.png")
            ,ListItem(3, "ダウンロード未",0, "https://2.bp.blogspot.com/-Wq39D5Sl7p4/W3abG77UVSI/AAAAAAABN-k/PH27NjOmYvMdxWPkIHMrU-j0-MtXbOCfgCLcBGAs/s800/cat3_4_tehe.png")
            ,ListItem(4, "ダウンロード未", 0, "https://2.bp.blogspot.com/-Wq39D5Sl7p4/W3abG77UVSI/AAAAAAABN-k/PH27NjOmYvMdxWPkIHMrU-j0-MtXbOCfgCLcBGAs/s800/cat3_4_tehe.png")
            ,ListItem(5, "ダウンロード未",0, "https://2.bp.blogspot.com/-Wq39D5Sl7p4/W3abG77UVSI/AAAAAAABN-k/PH27NjOmYvMdxWPkIHMrU-j0-MtXbOCfgCLcBGAs/s800/cat3_4_tehe.png")
            ,ListItem(6, "ダウンロード未",0, "https://2.bp.blogspot.com/-Wq39D5Sl7p4/W3abG77UVSI/AAAAAAABN-k/PH27NjOmYvMdxWPkIHMrU-j0-MtXbOCfgCLcBGAs/s800/cat3_4_tehe.png")
            ,ListItem(7, "ダウンロード未",0, "https://2.bp.blogspot.com/-Wq39D5Sl7p4/W3abG77UVSI/AAAAAAABN-k/PH27NjOmYvMdxWPkIHMrU-j0-MtXbOCfgCLcBGAs/s800/cat3_4_tehe.png")
        )
    }

}
class Fragment2: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_2, container, false)
    }
}