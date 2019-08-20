package wtf.qase.appskeleton.example.main.todos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.listitem_todo.view.*
import wtf.qase.appskeleton.example.R
import wtf.qase.appskeleton.example.repository.todo.dto.db.Todo

class TodoAdapter(
    private var items: List<Todo>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.listitem_todo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(items[position])
        }
        holder.itemView.popupMenuButton.setOnClickListener {
            onItemClickListener.onItemMenuClick(items[position], it)
        }
    }

    override fun getItemCount(): Int = items.size

    fun setData(newData: List<Todo>) {
        items = newData
        notifyDataSetChanged()
    }

    // ------------------------------------------------------------------------

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Todo) {
            itemView.title.text = item.title
            itemView.desc.text = item.desc
        }
    }

    interface OnItemClickListener {
        fun onItemClick(todo: Todo)
        fun onItemMenuClick(todo: Todo, view: View)
    }
}
