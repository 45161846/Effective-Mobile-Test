package com.effectivemobiletask

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.effectivemobiletask.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        bottomNav = findViewById(R.id.bottom_navigation)

        // Настройка Bottom Navigation
        bottomNav.setupWithNavController(navController)

        // Скрывать Bottom Navigation на экранах логина и деталей
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                com.effectivemobiletask.feature.auth.impl.R.id.loginFragment,
                com.effectivemobiletask.details.impl.R.id.courseDetailsFragment -> {
                    bottomNav.visibility = View.GONE
                }
                else -> {
                    bottomNav.visibility = View.VISIBLE
                }
            }
        }

        bottomNav.setOnNavigationItemSelectedListener { item ->
            navController.navigate(item.itemId, null, NavOptions.Builder()
                .setPopUpTo(R.id.main_nav_graph, false)
                .build())
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}