package com.practicum.playlistmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity: AppCompatActivity() {
    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backButton = findViewById<ImageView>(R.id.btn_back)
        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)
        val shareButton = findViewById<TextView>(R.id.btn_share)
        val supportButton = findViewById<TextView>(R.id.btn_support)
        val agreementButton = findViewById<TextView>(R.id.btn_agreement)

        // Получаем текущий режим (светлый или темный)
        val currentMode = AppCompatDelegate.getDefaultNightMode()

        // Инициализация состояния переключателя в зависимости от текущей темы
        switchTheme.isChecked = currentMode == AppCompatDelegate.MODE_NIGHT_YES

        // Обработчик изменения состояния переключателя
        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Включить темную тему
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // Включить светлую тему
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        backButton.setOnClickListener {
            // Закрываем текущую активность и возвращаемся на предыдущий экран
            finish()
        }

        shareButton.setOnClickListener {
            val shareTitle = getString(R.string.title_share)
            val shareText = getText(R.string.text_share)
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }

            val chooser = Intent.createChooser(shareIntent, shareTitle)

            startActivity(chooser)
        }

        supportButton.setOnClickListener {
            val supportAddressEmail = getString(R.string.address_email)
            val supportTitle = getString(R.string.title_email)
            val supportMessage = getString(R.string.text_email)
            val supportIntent = Intent(Intent.ACTION_SENDTO)

            supportIntent.data = Uri.parse("mailto:")
            supportIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(supportAddressEmail))
            supportIntent.putExtra(Intent.EXTRA_SUBJECT, supportTitle)
            supportIntent.putExtra(Intent.EXTRA_TEXT, supportMessage)

            // Проверяем, есть ли почтовое приложение на устройстве
            val resolveInfo = packageManager.queryIntentActivities(supportIntent, 0)
            if (resolveInfo.isNotEmpty()) {
                startActivity(supportIntent)
            } else {
                // Если почтовое приложение не найдено, выводим ошибку или уведомление
                Toast.makeText(this, "Нет доступных почтовых приложений", Toast.LENGTH_SHORT).show()
            }
        }

        agreementButton.setOnClickListener {
            val agreementLink = getString(R.string.agreement_link)
            val agreementIntent = Intent(Intent.ACTION_VIEW, Uri.parse(agreementLink))

            // Проверяем, есть ли доступные браузеры для открытия ссылки
            if (agreementIntent.resolveActivity(packageManager) != null) {
                startActivity(agreementIntent)
            } else {
                Toast.makeText(this, "Нет доступных браузеров", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
