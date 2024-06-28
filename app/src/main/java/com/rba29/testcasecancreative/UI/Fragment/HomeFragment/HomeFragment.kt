package com.rba29.testcasecancreative.UI.Fragment.HomeFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.rba29.testcasecancreative.Data.Paging.LoadingStateAdapter
import com.rba29.testcasecancreative.UI.Activity.SearchActivity.SearchActivity
import com.rba29.testcasecancreative.ViewModelFactory.ViewModelFactory
import com.rba29.testcasecancreative.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {


//    private lateinit var factory: ViewModelFactory
//    private val mainViewModel: HomeFragmentViewModel by viewModels { factory }

    private lateinit var viewModel: HomeFragmentViewModel

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GamesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            viewModel = obtainViewModel(this@HomeFragment.requireActivity() as AppCompatActivity)

            viewModel.isLoading.observe(this@HomeFragment.requireActivity() as AppCompatActivity) {
//                showLoading(it)
            }

            viewModel.message.observe(this@HomeFragment.requireActivity()) {
//                showToast(it)
            }

            val layoutManager = LinearLayoutManager(this@HomeFragment.requireActivity())
            rvGames.layoutManager = layoutManager

            getPagingStory()


            var appVersion = requireContext().packageManager.getPackageInfo(requireContext().packageName, 0).versionName
            tvAppVersion.text = "Apps Ver. : $appVersion"

            etSearch.setOnClickListener {
                val intent = Intent(requireContext(), SearchActivity::class.java)
                startActivity(intent)
            }
        }
    }

    companion object {
        const val SUCCESS_ADD_STORY = "success_add_story"
    }

//    private fun updateStory() {
//        binding.apply {
//            if (intent != null) {
//                val isNewStory = intent.extras?.getBoolean(SUCCESS_ADD_STORY)
//                if (isNewStory != null && isNewStory) {
//                    getPagingStory()
//                    adapter.notifyDataSetChanged()
//                    rvGames.post {
//                        rvGames.scrollToPosition(0)
//                    }
//                    rvGames.smoothScrollToPosition(0)
//
//                }
//            }
//        }
//    }

    private fun getPagingStory(){
        adapter = GamesAdapter()
        binding.rvGames.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoadingStateAdapter { adapter.retry() },
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )



        viewModel.listGames.observe(this@HomeFragment.requireActivity()) {
            adapter.submitData(lifecycle, it)
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) {
        binding.divLoading.visibility = if (state) View.VISIBLE else View.GONE
        binding.rvGames.visibility = if (state) View.GONE else View.VISIBLE

    }

    private fun obtainViewModel(activity: AppCompatActivity): HomeFragmentViewModel {
        val factory = ViewModelFactory.getInstance(
            this@HomeFragment.requireContext()
        )
        return ViewModelProvider(activity, factory).get(HomeFragmentViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Membersihkan binding
    }

}