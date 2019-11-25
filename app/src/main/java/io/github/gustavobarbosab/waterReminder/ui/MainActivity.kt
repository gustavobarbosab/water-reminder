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
import androidx.work.ExistingPeriodicWorkPolicy.KEEP
import io.github.gustavobarbosab.waterReminder.R
import io.github.gustavobarbosab.waterReminder.data.repository.WaterReminderRepositoryImpl
import io.github.gustavobarbosab.waterReminder.data.storage.local.WaterAppPreferenceImpl
import io.github.gustavobarbosab.waterReminder.data.sync.worker.WaterReminderWorkerManager
import io.github.gustavobarbosab.waterReminder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var workManager: WaterReminderWorkerManager

    private val dynamicBroadcastWaterReminder: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            this@MainActivity.viewModel.updateCounter()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startDependencies()
        startDataBinding()
        setupBroadcast()
        setupWorker()
    }

    private fun setupWorker() {
        workManager.startWorker(this, KEEP)
    }

    private fun startDependencies() {
        val repository = WaterReminderRepositoryImpl(WaterAppPreferenceImpl)
        val factory = MainViewModelFactory(repository)
        viewModel = ViewModelProviders.of(this, factory)[MainViewModel::class.java]
        workManager = WaterReminderWorkerManager()
    }

    private fun startDataBinding() {
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        setSupportActionBar(binding.toolbar)
    }

    private fun setupBroadcast() {
        val intentFilter = IntentFilter(UPDATE_TOTAL)
        registerReceiver(dynamicBroadcastWaterReminder, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(dynamicBroadcastWaterReminder)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
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
        const val UPDATE_TOTAL =
            "io.github.gustavobarbosab.waterReminder.ui.MainActivity.UPDATE_TOTAL"
    }
}
