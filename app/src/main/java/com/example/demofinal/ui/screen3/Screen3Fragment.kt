package com.example.demofinal.ui.screen3

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.work.WorkInfo
import com.example.demofinal.data.model.Response
import com.example.demofinal.data.worker.WorkerCharacter
import com.example.demofinal.databinding.FragmentScreen3Binding
import com.example.demofinal.ui.ScreenModelFactory
import com.example.demofinal.ui.screen3.adapter.Screen3Adapter
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
class Screen3Fragment : DaggerFragment() {

    private var screen3Adapter: Screen3Adapter? = null
    private lateinit var binding: FragmentScreen3Binding

    @Inject
    lateinit var screenModelFactory: ScreenModelFactory

    private val screen3ViewModel: Screen3ViewModel by viewModels {
        screenModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentScreen3Binding.inflate(layoutInflater, container, false)
        binding.btnRequest.setOnClickListener {
            val current = screen3ViewModel.page.value ?: 0
            val next = current + 1
            onLoadDataOfPage(next)
            onStartWithWorker(next)
        }
        onHandleCharacter()
        return binding.root
    }

    private fun onStartWithWorker(next: Int) {

        screen3ViewModel.currentResponse?.run { }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            WorkerCharacter.create(requireContext(), page = next)
                .observe(viewLifecycleOwner, Observer {
                    when (it.first.state) {
                        WorkInfo.State.ENQUEUED,

                        WorkInfo.State.RUNNING -> {
                            it.first.progress
                        }

                        WorkInfo.State.SUCCEEDED -> {
                            it.second?.run { }
                        }

                        else -> {}

                    }
                })
        }

    }

    private fun onHandleCharacter() {
//        flow
        lifecycleScope.launch {
            screen3ViewModel.characterFlowResponse.collectLatest {
                screen3ViewModel.currentResponse = it
                val currentPage = screen3ViewModel.page.value ?: 0
                val state = it.state
                binding.txtFlow.text = "FLOW LOAD DATA FROM: $currentPage" + kotlin.run {
                    when (state) {
                        Response.State.LOADING -> "Loading"
                        Response.State.SUCCESS -> "Success"
                        else -> "Error"
                    }
                }

                when (state) {
                    Response.State.LOADING -> {}
                    Response.State.SUCCESS -> {
                        screen3Adapter = it.datas?.let { i ->
                            Screen3Adapter(i) { item ->
                                val intent = Intent(requireContext(), DetailActivity::class.java)
                                intent.putExtra("key", item)
                                startActivity(intent)
                            }
                        }
                        binding.rvFlow.adapter = screen3Adapter
                    }
                    Response.State.ERROR -> {}
                }

            }
        }

//        liveData
        lifecycleScope.launch {
            screen3ViewModel.characterLiveDataResponse.observe(viewLifecycleOwner, Observer {
                val currentPage = screen3ViewModel.page.value ?: 0
                val state = it.state
                binding.txtLiveData.text = "LIVEDATA LOAD DATA FROM: $currentPage " + kotlin.run {
                    when (state) {
                        Response.State.LOADING -> "Loading"
                        Response.State.SUCCESS -> "Success"
                        else -> "Error"
                    }
                }

                when (state) {
                    Response.State.LOADING -> {

                    }
                    Response.State.SUCCESS -> {
                        screen3Adapter = it.datas?.let { i ->
                            Screen3Adapter(i) { item->
                                val intent =
                                    Intent(requireContext(), DetailActivity::class.java)
                                intent.putExtra("key", item)
                                startActivity(intent)
                            }
                        }
                        binding.rvLiveData.adapter = screen3Adapter
                    }
                    Response.State.ERROR -> {
                        // error của api , hoặc của lỗi cấu hình , hoặc ko có internet
                    }
                }
            })
        }
    }

    private fun onLoadDataOfPage(index: Int) {
        screen3ViewModel.page.value = index
    }


}