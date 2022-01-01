package com.example.fiqri_bfaa_3.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fiqri_bfaa_3.R
import com.example.fiqri_bfaa_3.data.model.User
import com.example.fiqri_bfaa_3.databinding.ActivityMainBinding
import com.example.fiqri_bfaa_3.ui.adapter.UserAdapter
import com.example.fiqri_bfaa_3.ui.detail.DetailUserActivity
import com.example.fiqri_bfaa_3.ui.favorite.FavoriteActivity
import com.example.fiqri_bfaa_3.ui.setting.SettingPreferences
import com.example.fiqri_bfaa_3.ui.setting.ThemeActivity
import com.example.fiqri_bfaa_3.ui.setting.ThemeViewModel
import com.example.fiqri_bfaa_3.ui.setting.ViewModelFactory


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            btnSearch.setOnClickListener {
                searchUser()
            }
            etQuery.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false

            }
        }
        viewModel.getSearchUsers().observe(this, {
            if (it != null) {

                adapter = UserAdapter(it)
                binding.rvUser.adapter = adapter
                adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: User) {
                        Intent(this@MainActivity, DetailUserActivity::class.java).also { tk ->
                            tk.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                            tk.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                            tk.putExtra(DetailUserActivity.EXTRA_URL, data.avatarUrl)
                            startActivity(tk)
                        }
                    }


                })
                showLoading(false)
            }

        })
        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            ThemeViewModel::class.java
        )
        mainViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                }

            })

    }

    private fun searchUser() {
        binding.apply {
            val query = etQuery.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
            viewModel.setSearchUsers(query)

        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_menu -> {
                Intent(this@MainActivity, FavoriteActivity::class.java).also {
                    startActivity(it)

                }
            }
            R.id.setting -> {
                Intent(this@MainActivity, ThemeActivity::class.java).also {
                    startActivity(it)

                }

            }
        }
        return super.onOptionsItemSelected(item)
    }


}