package wtf.qase.appskeleton.example.main.todos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_todo_detail.desc
import kotlinx.android.synthetic.main.fragment_todo_detail.title
import wtf.qase.appskeleton.example.R
import wtf.qase.appskeleton.example.repo.model.Todo

class TodoDetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_todo_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val todo = (arguments?.getSerializable("todo") as Todo)
        title.text = todo.title
        desc.text = todo.desc
    }
}