package com.rba29.testcasecancreative.UI.Fragment.FavoriteFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rba29.testcasecancreative.Data.Db.FavoriteGames.FavoriteGames
import com.rba29.testcasecancreative.Data.Response.ListResultItem
import com.rba29.testcasecancreative.R
import com.rba29.testcasecancreative.UI.Activity.DetailGamesActivity.DetailGamesActivity
import com.rba29.testcasecancreative.UI.Fragment.HomeFragment.HomeFragmentViewModel
import com.rba29.testcasecancreative.ViewModelFactory.ViewModelFactory
import com.rba29.testcasecancreative.databinding.FragmentFavoriteBinding
import com.rba29.testcasecancreative.databinding.FragmentHomeBinding

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){

            adapter = FavoriteAdapter()
            rvChild.layoutManager = LinearLayoutManager(this@FavoriteFragment.requireContext())
            rvChild.setHasFixedSize(true)
            rvChild.adapter = adapter

            viewModel = obtainViewModel(this@FavoriteFragment.requireActivity() as AppCompatActivity)

            viewModel.getAllFavorite().observe(viewLifecycleOwner) { favList ->
                if (favList.size > 0) {
                    adapter.setListFavoriteGames(favList)
                    rvChild.visibility = View.VISIBLE
                    tvEmpty.visibility = View.GONE
                } else {
                    rvChild.visibility = View.GONE
                    tvEmpty.visibility = View.VISIBLE
                }
            }

            adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
                override fun onItemClicked(data: FavoriteGames) {

                    val gameFavClick = ListResultItem(
                        id = data.id,
                        name = data.name,
                        slug = data.slug,
                        released = data.released,
                        rating = data.rating,
                        background_image = data.background_image,
                    )
                    val intent: Intent = Intent(
                        this@FavoriteFragment.requireContext() as AppCompatActivity,
                        DetailGamesActivity::class.java
                    )
                    intent.putExtra("EXTRA_GAMES", gameFavClick)
                    startActivity(intent)
                }
            })

        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(
            this@FavoriteFragment.requireContext()
        )
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}