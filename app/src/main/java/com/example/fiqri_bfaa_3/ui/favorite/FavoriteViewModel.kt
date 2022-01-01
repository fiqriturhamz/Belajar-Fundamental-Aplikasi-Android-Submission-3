package com.example.fiqri_bfaa_3.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.fiqri_bfaa_3.data.local.FavoriteUser
import com.example.fiqri_bfaa_3.data.local.FavoriteUserDao
import com.example.fiqri_bfaa_3.data.local.UserDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private var userDao: FavoriteUserDao?

    companion object {
        private lateinit var userDb: UserDatabase
    }

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>? {
        return userDao?.getFavoriteUser()
    }
}