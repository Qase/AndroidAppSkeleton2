package wtf.qase.appskeleton.example.main.todos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_todo_list.add
import kotlinx.android.synthetic.main.fragment_todo_list.list
import org.koin.androidx.viewmodel.ext.android.viewModel
import wtf.qase.appskeleton.example.R
import wtf.qase.appskeleton.example.repository.todo.dto.db.Todo

class TodoListFragment : Fragment() {
    private val vm: TodoListViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_todo_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)

        val adapter = TodoAdapter(listOf(), object : TodoAdapter.OnItemClickListener {
            override fun onItemClick(todo: Todo) {
                Navigation.findNavController(view).navigate(
                    R.id.todo_detail_fragment,
                    bundleOf("todo" to todo)
                )
            }

            override fun onItemMenuClick(todo: Todo, view: View) {
                val popup = PopupMenu(requireContext(), view)
                popup.menuInflater.inflate(R.menu.todo_actions, popup.menu)
                popup.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menu_todo_delete -> {
                            vm.deleteTodo(todo)
                                .subscribe({
                                    Toast.makeText(
                                        context,
                                        R.string.deleted,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }, { error ->
                                    Toast.makeText(
                                        context,
                                        error.localizedMessage,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                })
                            return@setOnMenuItemClickListener true
                        }

                        else -> return@setOnMenuItemClickListener false
                    }

                }
                popup.show()
            }
        })

        vm.getAllTodos().observe(this, Observer {
            adapter.setData(it)
        })

        list.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        list.setHasFixedSize(true)
        list.layoutManager = LinearLayoutManager(activity)
        list.adapter = adapter

        add.setOnClickListener {
            Navigation.findNavController(view).navigate(
                R.id.create_todo_dialog_fragment
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.todo, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_settings -> {
                Navigation.findNavController(view!!).navigate(R.id.settings_fragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
