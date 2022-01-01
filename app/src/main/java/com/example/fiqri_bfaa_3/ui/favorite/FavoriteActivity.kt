package com.example.fiqri_bfaa_3.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fiqri_bfaa_3.data.local.FavoriteUser
import com.example.fiqri_bfaa_3.data.model.User
import com.example.fiqri_bfaa_3.databinding.ActivityFavoriteBinding
import com.example.fiqri_bfaa_3.ui.adapter.UserAdapter
import com.example.fiqri_bfaa_3.ui.detail.DetailUserActivity


class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = UserAdapter(ArrayList())
        adapter.notifyDataSetChanged()
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_URL, data.avatarUrl)
                    startActivity(it)
                }
            }


        })
        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.adapter = adapter
        }
        viewModel.getFavoriteUser()?.observe(this, {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)

            }
        })
        supportActionBar?.apply {

            title = "Favorite"
            elevation = 0f
            setDisplayHomeAsUpEnabled(true)


        }


    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<User> {

        val listUsers = ArrayList<User>()
        for (user in users) {
            val userMapped = User(
                null, null,
                user.login, null, user.avatar_url, user.id
            )

            listUsers.add(userMapped)
        }
        return listUsers

    }

}