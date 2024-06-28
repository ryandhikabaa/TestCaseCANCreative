package com.rba29.testcasecancreative.UI.Activity.DetailGamesActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rba29.testcasecancreative.Data.Response.ListResultItem
import com.rba29.testcasecancreative.R
import com.rba29.testcasecancreative.UI.Fragment.HomeFragment.HomeFragmentViewModel
import com.rba29.testcasecancreative.ViewModelFactory.ViewModelFactory
import com.rba29.testcasecancreative.databinding.ActivityDetailGamesBinding
import com.rba29.testcasecancreative.databinding.ActivityMainBinding

class DetailGamesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailGamesBinding

    private lateinit var viewModel: DetailGameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailGamesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            viewModel = obtainViewModel(this@DetailGamesActivity)

            val gamesData: ListResultItem? = intent.getParcelableExtra("EXTRA_GAMES")

            viewModel.isLoading.observe(this@DetailGamesActivity) {
                showLoading(it)
            }

            viewModel.message.observe(this@DetailGamesActivity) {
                showToast(it)
            }

            if (gamesData != null) {
                viewModel.getDetailGame(gamesData.id)

                viewModel.gameDataDetail.observe(this@DetailGamesActivity) { data ->
                    viewModel.isStateDataDetail.observe(this@DetailGamesActivity) {
                        if (it) {
                            tvSlug.text = data.slug
                            tvName.text = data.name
                            var sRilisDate = data.released
                            tvPublished.text = "Realese Date : $sRilisDate"
                            tvRate.text = data.rating
                            tvDesc.text = data.description
                            Glide.with(this@DetailGamesActivity)
                                .load(data.image)
                                .into(ivStory)

                            ivShare.setOnClickListener {
                                var sWeb = data.website
                                val text = "Official Website : $sWeb"
                                val shareIntent = Intent(Intent.ACTION_SEND)
                                shareIntent.type = "text/plain"
                                shareIntent.putExtra(Intent.EXTRA_TEXT, text)
                                startActivity(Intent.createChooser(shareIntent, "Dicoding Stories"))
                            }
                        } else {
                            finish()
                        }
                    }


                    ivBack.setOnClickListener(View.OnClickListener { finish() })

                }
            }else{
                finish()
            }

        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailGameViewModel {
        val factory = ViewModelFactory.getInstance(
            this@DetailGamesActivity
        )
        return ViewModelProvider(activity, factory).get(DetailGameViewModel::class.java)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) {
        binding.divLoading.visibility = if (state) View.VISIBLE else View.GONE

    }
}