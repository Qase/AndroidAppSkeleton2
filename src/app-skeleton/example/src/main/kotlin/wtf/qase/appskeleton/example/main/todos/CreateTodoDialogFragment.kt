package wtf.qase.appskeleton.example.main.todos

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_todo_create.view.cancel
import kotlinx.android.synthetic.main.dialog_todo_create.view.create
import kotlinx.android.synthetic.main.dialog_todo_create.view.desc
import kotlinx.android.synthetic.main.dialog_todo_create.view.title
import org.koin.androidx.viewmodel.ext.android.viewModel
import wtf.qase.appskeleton.example.R

class CreateTodoDialogFragment: DialogFragment() {
    private val vm: CreateTodoViewModel by viewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogView = LayoutInflater
            .from(context)
            .inflate(R.layout.dialog_todo_create, null)

        dialogView.create.setOnClickListener {
            vm.createTodo(
                dialogView.title.text.toString(),
                dialogView.desc.text.toString()
            ).subscribe({
                dismiss()
            }, { error ->
                Toast.makeText(
                    context,
                    error.localizedMessage,
                    Toast.LENGTH_SHORT
                ).show()
            })
        }

        dialogView.cancel.setOnClickListener {
            dismiss()
        }

        return AlertDialog.Builder(requireActivity())
            .setTitle(R.string.todo_dialog_title)
            .setView(dialogView)
            .create()
    }
}
