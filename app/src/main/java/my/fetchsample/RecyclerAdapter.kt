package my.fetchsample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val activity: MainActivity,
                      private val itemList:List<ListItem>,
                      private val itemClickListener: RecyclerViewHolder.ItemClickListener
) : RecyclerView.Adapter<RecyclerViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.let { recyclerViewHolder ->
            recyclerViewHolder.itemLayout.setOnClickListener {
                itemClickListener.onItemClick(it, position)
            }
            recyclerViewHolder.itemTextView.text = itemList[position].statusText
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun changeStatus(id: Long, statusText: String) {
        itemList.forEach {
            if (id == it.id) {
                it.statusText = statusText
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        val layoutInflater = LayoutInflater.from(activity)
        val mView = layoutInflater.inflate(R.layout.list_item1, parent, false)
        return RecyclerViewHolder(mView)
    }

}
class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    // 独自に作成したListener
    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    val itemLayout: LinearLayout = view.findViewById(R.id.list_item_layout)
    val itemTextView: TextView = view.findViewById(R.id.list_item_text)
    val itemImageView: ImageView = view.findViewById(R.id.list_item_image)

    init {
        // layoutの初期設定するときはココ
    }

}
data class ListItem(var id: Long, var statusText: String, var status: Int, var downloadUrl: String)