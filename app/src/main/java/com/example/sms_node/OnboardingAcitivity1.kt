package com.example.sms_node

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast
import androidx.core.content.ContextCompat

class OnboardingAcitivity1 : AppCompatActivity() {
    var smsAllowed = false;
    var systemNotificationAllowed = false
    var foregroundPersistentExecutionAllowed = false;
    var batteryExemptionOptimizationAllowed = false;

    lateinit var btnSMS: Button
    lateinit var btnNotification: Button
    lateinit var btnForeground: Button
    lateinit var btnBattery: Button

    private val requestSmsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted ->
        if(isGranted){
            showToast("SMS Permission Granted!")
            smsAllowed = true
        } else showToast("SMS Permission Denied.")
    }

    private val requestNotificationLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted ->
        if(isGranted){
            showToast("Notifications Allowed!")
            systemNotificationAllowed = true
        } else showToast("Notifications Denied.")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding_acitivity1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnSMS = findViewById<Button>(R.id.btn1)
        btnNotification = findViewById<Button>(R.id.btn2)
        btnForeground = findViewById<Button>(R.id.btn3)
        btnBattery = findViewById<Button>(R.id.btn4)

        btnSMS.setOnClickListener {
            requestSmsLauncher.launch(Manifest.permission.READ_SMS)
        }

        btnNotification.setOnClickListener{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                requestNotificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                systemNotificationAllowed = true
                showToast("Notifications are granted by default on older versions.")
            }
        }

        btnForeground.setOnClickListener {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
                showToast("Foreground execution is active and ready!")
                foregroundPersistentExecutionAllowed = true
            } else {
                showToast("Please grant Notification permission first to ensure persistent execution.")
            }
        }

        btnBattery.setOnClickListener {
            val powerManager = getSystemService(POWER_SERVICE) as PowerManager
            val packageName = packageName;

            if(powerManager.isIgnoringBatteryOptimizations(packageName)){
                showToast("App is already exempted from battery optimization!")
                batteryExemptionOptimizationAllowed = true
            } else {
                val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                    data = Uri.parse("package:$packageName")
                }
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val powerManager = getSystemService(POWER_SERVICE) as PowerManager

        if (powerManager.isIgnoringBatteryOptimizations(packageName)) {
            batteryExemptionOptimizationAllowed = true
            showToast("Battery optimization disabled!")
        } else {
            batteryExemptionOptimizationAllowed = false
        }
    }

    private fun showToast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}