package wtf.qase.appskeleton.example.main.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_settings.login
import kotlinx.android.synthetic.main.fragment_settings.logout
import kotlinx.android.synthetic.main.fragment_settings.preferences
import kotlinx.android.synthetic.main.fragment_settings.service_start
import kotlinx.android.synthetic.main.fragment_settings.service_stop
import org.koin.androidx.viewmodel.ext.android.viewModel
import quanti.com.kotlinlog.Log
import wtf.qase.appskeleton.example.service.MultiService
import wtf.qase.appskeleton.example.R

class SettingsFragment : Fragment() {
    private val vm: SettingsViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_settings, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        preferences.text = vm.prefs

        login.setOnClickListener {
            vm.login("username", "password")
                .subscribe({
                    Log.d(it.toString())
                    if (it.isSuccessful) {
                        // Save
                    } else {
                        // Show error msg
                    }
                }, { error ->
                    error.printStackTrace()
                    // Show error msg
                })
        }

        logout.setOnClickListener {
            vm.logout()
                .subscribe({
                    Log.d(it.toString())
                    if (it.isSuccessful) {
                        // Save
                    } else {
                        // Show error msg
                    }
                }, { error ->
                    error.printStackTrace()
                    // Show error msg
                })
        }

        service_start.setOnClickListener {
            MultiService.start(requireContext())
        }

        service_stop.setOnClickListener {
            requireActivity().stopService(Intent(context, MultiService::class.java))
        }
    }
}
