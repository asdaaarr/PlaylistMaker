package com.practicum.playlistmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlistmaker.databinding.ActivitySettingsBinding

class SettingsActivity: AppCompatActivity() {
    @SuppressLint("QueryPermissionsNeeded")

    private lateinit var settingsBinding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(settingsBinding.root)

        // Получаем текущий режим (светлый или темный)
        val currentMode = AppCompatDelegate.getDefaultNightMode()

        // Инициализация состояния переключателя в зависимости от текущей темы
        settingsBinding.switchTheme.isChecked = currentMode == AppCompatDelegate.MODE_NIGHT_YES

        // Обработчик изменения состояния переключателя
        settingsBinding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Включить темную тему
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // Включить светлую тему
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        settingsBinding.btnBack.setOnClickListener {
            // Закрываем текущую активность и возвращаемся на предыдущий экран
            finish()
        }

        settingsBinding.btnShare.setOnClickListener {
            val shareTitle = getString(R.string.title_share)
            val shareText = getText(R.string.text_share)
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }

            val chooser = Intent.createChooser(shareIntent, shareTitle)

            startActivity(chooser)
        }

        settingsBinding.btnSupport.setOnClickListener {
            val supportAddressEmail = getString(R.string.address_email)
            val supportTitle = getString(R.string.title_email)
            val supportMessage = getString(R.string.text_email)
            val supportIntent = Intent(Intent.ACTION_SENDTO)

            supportIntent.data = Uri.parse("mailto:")
            supportIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(supportAddressEmail))
            supportIntent.putExtra(Intent.EXTRA_SUBJECT, supportTitle)
            supportIntent.putExtra(Intent.EXTRA_TEXT, supportMessage)

            startActivity(supportIntent)
        }

        settingsBinding.btnAgreement.setOnClickListener {
            val agreementLink = getString(R.string.agreement_link)
            val agreementIntent = Intent(Intent.ACTION_VIEW, Uri.parse(agreementLink))

            startActivity(agreementIntent)
        }
    }
}