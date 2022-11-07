package com.example.demofinal.ui.screen1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.example.demofinal.databinding.FragmentScreen1Binding
import com.example.demofinal.ui.ScreenModelFactory
import com.example.demofinal.ui.screen1.adapter.Screen1Adapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

@ExperimentalPagingApi
class Screen1Fragment : DaggerFragment() {
    private var screen1Adapter: Screen1Adapter? = null

    @Inject
    lateinit var screenModelFatory: ScreenModelFactory
    private val screen1ViewModel: Screen1ViewModel by viewModels {
        screenModelFatory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentScreen1Binding.inflate(layoutInflater, container, false)
        screen1Adapter = Screen1Adapter()
        binding.rv.adapter = screen1Adapter
        lifecycleScope.launchWhenCreated {
            screen1ViewModel.characterLiveData.observe(viewLifecycleOwner) {
                screen1Adapter?.submitData(lifecycle, it)
            }
        }
        return binding.root
    }
}