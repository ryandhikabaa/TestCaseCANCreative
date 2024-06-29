package com.rba29.testcasecancreative.UI.Activity.SearchActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rba29.testcasecancreative.Data.Response.ListResultItem
import com.rba29.testcasecancreative.R
import com.rba29.testcasecancreative.UI.Activity.DetailGamesActivity.DetailGamesActivity
import com.rba29.testcasecancreative.UI.Fragment.HomeFragment.GamesAdapter
import com.rba29.testcasecancreative.ViewModelFactory.ViewModelFactory
import com.rba29.testcasecancreative.databinding.ActivityMainBinding
import com.rba29.testcasecancreative.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    private lateinit var adapter: GamesAdapter
    private lateinit var viewModel: SearchActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = obtainViewModel(this@SearchActivity)

        viewModel.listItem.observe(this) { gamesList ->
            setGamesData(gamesList)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.snackbarText.observe(this) {

            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }


        with(binding) {
            ivBack.setOnClickListener { finish() }

            val layoutManager = LinearLayoutManager(this@SearchActivity)
            binding.rvGames.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(this@SearchActivity, layoutManager.orientation)
            binding.rvGames.addItemDecoration(itemDecoration)

            svUsers.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    hideKeyboard()
                    svUsers.clearFocus()
                    query?.let {
                        viewModel.searchGame(it)
                        rvGames.visibility = View.VISIBLE
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    if (newText.isNullOrEmpty()) {
//                        svUsers.clearFocus()
//                        svUsers.isFocusable = false
//                        svUsers.isFocusableInTouchMode = false
                        rvGames.visibility = View.GONE

                    }
                    return true
                }
            })
        }
    }

    private fun setGamesData(usersItem: List<ListResultItem>) {
        val adapter = SearchGamesAdapter()
        adapter.submitList(usersItem)
        binding.rvGames.adapter = adapter
        adapter.setOnItemClickCallback(object : SearchGamesAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListResultItem) {
                val intent = Intent(this@SearchActivity, DetailGamesActivity::class.java)
                intent.putExtra("EXTRA_GAMES", data)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(state: Boolean) { binding.divLoading.visibility = if (state) View.VISIBLE else View.GONE }


    private fun obtainViewModel(activity: AppCompatActivity): SearchActivityViewModel {
        val factory = ViewModelFactory.getInstance(application)
        return ViewModelProvider(this, factory).get(SearchActivityViewModel::class.java)
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}