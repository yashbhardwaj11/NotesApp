package com.devinfusion.internshalaassignment

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.devinfusion.internshalaassignment.databinding.ActivityMainBinding
import com.devinfusion.internshalaassignment.fragments.LoginFragment
import com.devinfusion.internshalaassignment.fragments.NoteListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref.contains("user_id")

        if (savedInstanceState == null) {
            if (isLoggedIn) {
                supportFragmentManager.commit {
                    replace(R.id.fragment_container, NoteListFragment())
                }
            } else {
                supportFragmentManager.commit {
                    replace(R.id.fragment_container, LoginFragment())
                }
            }
        }
    }
}
