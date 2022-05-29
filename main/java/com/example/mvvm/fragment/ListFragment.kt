package com.example.mvvm.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.*
import com.example.mvvm.adapter.UserAdapter
import com.example.mvvm.database.AppDatabase
import com.example.mvvm.databinding.FragmentListBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val userRepository by inject<UserRepository>()
    private val appDatabase by inject<AppDatabase>()

    private val viewModel by viewModels<ListViewModel> {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ListViewModel(
                    userRepository,
                    appDatabase.userDao()
                ) as T
            }
        }
    }

    private val adapter by lazy {
        UserAdapter {
            findNavController().navigate(
                ListFragmentDirections.details(it.id.toString())
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.onLoadMore()
        return FragmentListBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val layoutManager = LinearLayoutManager(requireContext())
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
            recyclerView.addSpaceDecoration(SPACE_SIZE)
            recyclerView.addPaginationScrollListener(layoutManager, 20) {
                viewModel.onLoadMore()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel
                .dataFlow
                .onEach {
                    adapter.submitList(it)
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val SPACE_SIZE = 50
    }
}