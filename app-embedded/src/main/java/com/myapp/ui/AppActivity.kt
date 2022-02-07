package com.myapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.myapp.toothpick.DI
import com.myapp.R
import com.rsastack.system.navigation.BackButtonListener

class AppActivity: AppCompatActivity(), OnFragmentExit
{
    val layoutRes: Int = R.layout.activity_main2
    val navigationContainerId: Int = R.id.app_container1

    private val currentFragment: BackButtonListener?
        get() = supportFragmentManager.findFragmentById(navigationContainerId) as? BackButtonListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutRes)

        if (supportFragmentManager.fragments.isEmpty()) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.app_container1, FeatureAppFragment.newInstance(DI.APP_SCOPE))
                commit()
            }
        }
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

    override fun onFragmentExit(fragment: Fragment) {
        super.onBackPressed()
    }

}

