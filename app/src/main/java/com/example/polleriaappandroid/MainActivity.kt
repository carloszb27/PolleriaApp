package com.example.polleriaappandroid

import ProfileFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.polleriaappandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    private lateinit var mActiveFragment: Fragment
    private lateinit var mFragmentManager: FragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupBottomNav()
    }

    private fun setupBottomNav() {
        val mFragmentManager = supportFragmentManager

        val homeFragment = HomeFragment()
        val addFragment = AddFragment()
        val profileFragment = ProfileFragment()

        mActiveFragment = homeFragment

        // Poner el menú pero invertido
        // Crear de derecha a izquierda las opciones de menú
        mFragmentManager.beginTransaction()
            .add(R.id.hostFragment, profileFragment, ProfileFragment::class.java.name)
            .hide(profileFragment).commit()
        mFragmentManager.beginTransaction()
            .add(R.id.hostFragment, addFragment, AddFragment::class.java.name)
            .hide(addFragment).commit()
        mFragmentManager.beginTransaction()
            .add(R.id.hostFragment, homeFragment, HomeFragment::class.java.name)
            .commit()

        mBinding.bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.action_home -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(homeFragment).commit()
                    mActiveFragment = homeFragment
                    true
                }
                R.id.action_add -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(addFragment).commit()
                    mActiveFragment = addFragment
                    true
                }
                R.id.action_profile -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(profileFragment).commit()
                    mActiveFragment = profileFragment
                    true
                }
                else -> false
            }
        }

    }


}