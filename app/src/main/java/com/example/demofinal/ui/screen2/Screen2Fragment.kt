package com.example.demofinal.ui.screen2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.example.demofinal.databinding.FragmentScreen2Binding
import com.example.demofinal.ui.ScreenModelFactory
import com.example.demofinal.ui.screen2.adapter.Screen2Adapter
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@ExperimentalPagingApi
class Screen2Fragment : DaggerFragment() {

    private var screen2Adapter: Screen2Adapter? = null

    @Inject
    lateinit var screenModelFactory: ScreenModelFactory
    private val screen2ViewModel: Screen2ViewModel by viewModels {
        screenModelFactory
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentScreen2Binding.inflate(layoutInflater, container, false)
        screen2Adapter = Screen2Adapter()
        binding.rv.adapter = screen2Adapter
        lifecycleScope.launchWhenCreated {
            screen2ViewModel.characterFlow.collectLatest {
                screen2Adapter?.submitData(lifecycle, it)
            }
        }
        return binding.root
    }


}