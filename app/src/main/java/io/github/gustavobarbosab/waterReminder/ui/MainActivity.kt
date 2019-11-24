package io.github.gustavobarbosab.waterReminder.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import io.github.gustavobarbosab.waterReminder.R
import io.github.gustavobarbosab.waterReminder.data.storage.local.WaterAppPreference
import io.github.gustavobarbosab.waterReminder.data.storage.local.WaterAppPreferenceImpl
import io.github.gustavobarbosab.waterReminder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var preferences: WaterAppPreference

    private val dynamicBroadcastWaterReminder: BroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            this@MainActivity.updateViewModelTotal()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        preferences = WaterAppPreferenceImpl()
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        setSupportActionBar(binding.toolbar)
        setupBroadcast()
    }

    private fun setupBroadcast() {
        val intentFilter = IntentFilter(UPDATE_TOTAL)
        registerReceiver(dynamicBroadcastWaterReminder,intentFilter)
    }

    override fun onResume() {
        super.onResume()
        updateViewModelTotal()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(dynamicBroadcastWaterReminder)
    }

    private fun updateViewModelTotal() {
        viewModel.totalWaterCups = preferences.getTotalWaterCups(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val UPDATE_TOTAL = "io.github.gustavobarbosab.waterReminder.ui.MainActivity.UPDATE_TOTAL"
    }
}
