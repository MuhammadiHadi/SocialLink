package com.example.sociallink

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat


import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.sociallink.databinding.ActivityMainBinding
import com.example.sociallink.databinding.HeaderLayoutBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration
    var isOpen = false
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initClicks()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        //bottom nav
        binding.navigationView.setupWithNavController(navController)

        //drawer
////        appBarConfiguration = AppBarConfiguration(
////            setOf(
////                R.id.home,
////                R.id.linkedin,
////                R.id.facebook,
////                R.id.github,
////
////            ),
//            binding.drawerLayout
//        )
        //menu item click handle
        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener(
            fun(
            _ : NavController ,
            destination : NavDestination ,
            arguments : Bundle?
        ) {
            when (destination.id) {
                R.id.home ,
                R.id.linkedin,
                R.id.facebook,
                R.id.github,
                R.id.location->{
                         binding.ivMenu.visibility = View.VISIBLE
                        binding.navigationView.visibility = View.VISIBLE
//                    binding.ivLogo.visibility = View.VISIBLE
            }
                else -> {
                    binding.ivMenu.visibility = View.GONE
                    binding.navigationView.visibility = View.GONE
//                    binding.ivLogo.visibility = View.GONE
                }
            }
        })


        //
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    //bottom nav
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) ||
                super.onOptionsItemSelected(item)
    }

    //open drawer when drawer icon clicked and back btn presse

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    private fun initClicks() {
        binding.ivMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
            isOpen = true
        }
        val view = binding.navView.getHeaderView(0)

        val hBinding: HeaderLayoutBinding = HeaderLayoutBinding.bind(view)

        hBinding.ivBackDrawer.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }


}
