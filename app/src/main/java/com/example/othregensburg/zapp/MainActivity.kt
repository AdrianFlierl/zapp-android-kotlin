package com.example.othregensburg.zapp

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.othregensburg.zapp.music.view.MyMusicFragment
import com.example.othregensburg.zapp.travelStream.view.TravelStreamFragment
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var drawerToggle: ActionBarDrawerToggle
    lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUiComponents()
        setupToolbar()
        setupDrawerToogle()
        setupNavigationDrawer()

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = TravelStreamFragment.newInstance(getString(R.string.nav_item_title_travel_stream))
        fragmentTransaction.add(R.id.main_container, fragment)
        fragmentTransaction.commit()
//        switchFragment(R.id.menu_nav_item_travel_stream);

    }

    private fun switchFragment(menuItemId: Int) {
        // Get FragmentManager
        val fragmentManager = supportFragmentManager
        // Start transaction
        val fragmentTransaction = fragmentManager.beginTransaction()
        // Set custom animation
        fragmentTransaction.setCustomAnimations(R.anim.alpha_transition_in,
            R.anim.alpha_transition_out)

        // Get the current fragment which is in the container
        val containerFragment: Fragment? =
            fragmentManager.findFragmentById(R.id.main_container)
        // Init the new fragment
        val newFragment = containerFragment?.let { getFragmentById(it, menuItemId) }
        // If returned fragment != null -> replace it
        if (newFragment != null) {
            fragmentTransaction.replace(R.id.main_container, newFragment)
            fragmentTransaction.commit()
        }

    }

    private fun setupNavigationDrawer() {
        navigationView.menu.getItem(0).isChecked = true;

        // Once a menu item is selected replace fragment
        navigationView.setNavigationItemSelectedListener(
            object : NavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                    menuItem.isChecked = true
                    switchFragment(menuItem.itemId)
                    drawerLayout.closeDrawers()
                    return true
                }
            })

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
    }

    private fun setupDrawerToogle() {
        drawerToggle = ActionBarDrawerToggle(this,
            drawerLayout, toolbar, R.string.open_drawer_accessibility_desc,
            R.string.close_drawer_accessibility_desc)
        drawerLayout.addDrawerListener(drawerToggle)

    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun setupUiComponents() {
        toolbar = findViewById(R.id.main_toolbar)
        drawerLayout = findViewById(R.id.drawerlayout)
        navigationView = findViewById(R.id.navigation);
    }

    private fun getFragmentById(containerFragment: Fragment, menuItemId: Int): Fragment? {
        when (menuItemId) {
            R.id.menu_nav_item_travel_stream ->
                // Check if fragment is already there - prevent unnecessary replacement
                if (containerFragment !is TravelStreamFragment) return TravelStreamFragment.newInstance(
                    getString(R.string.nav_item_title_travel_stream))
            R.id.menu_nav_items_music -> if (containerFragment !is MyMusicFragment) return MyMusicFragment.newInstance(
                getString(R.string.nav_item_title_my_music))
            else -> return null
        }
        // Return null if no change should occur
        return null
    }

}
