package wtf.qase.appskeleton.template.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_dummy.add
import kotlinx.android.synthetic.main.fragment_dummy.message
import kotlinx.android.synthetic.main.fragment_dummy.reset
import org.koin.androidx.viewmodel.ext.android.viewModel
import wtf.qase.appskeleton.template.R

class DummyFragment : Fragment() {
    private val vm: DummyViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_dummy, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        message.text = vm.count.toString()

        add.setOnClickListener {
            vm.increment().toString().let {
                message.text = it
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }

        reset.setOnClickListener {
            message.text = vm.reset().toString()
        }
    }
}
