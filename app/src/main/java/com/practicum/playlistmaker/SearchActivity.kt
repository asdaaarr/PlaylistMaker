package com.practicum.playlistmaker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.practicum.playlistmaker.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private var searchRequest: String? = null
    private lateinit var searchBinding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(searchBinding.root)

        // Если есть сохранённый текст, восстановить его
        savedInstanceState?.let {
            searchRequest = it.getString("search_request")
            searchBinding.inputSearch.setText(searchRequest)
        }

        searchBinding.inputClear.setOnClickListener {
            searchBinding.inputSearch.setText("")
            hideKeyboard()
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
                }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                searchBinding.inputClear.isVisible = clearButtonVisibility(s)
                searchRequest = s.toString() // Сохраняем текущий текст
                }

            override fun afterTextChanged(s: Editable?) {
                 // empty
                 }
             }
        searchBinding.inputSearch.addTextChangedListener(simpleTextWatcher)

        searchBinding.btnBack.setOnClickListener {
            // Закрываем текущую активность и возвращаемся на предыдущий экран
            finish()
        }
    }

    // Сохранение состояния активности
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Сохраняем текущий текст поискового запроса
        outState.putString("search_request", searchRequest)
    }

    // Восстановление состояния активности
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Восстанавливаем текст из savedInstanceState
        searchRequest = savedInstanceState.getString("search_request")
    }

    private fun clearButtonVisibility(s: CharSequence?): Boolean {
        return !s.isNullOrEmpty()  // Возвращаем true, если текст не пустой
    }


    // Функция для скрытия клавиатуры
    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocus = currentFocus
        currentFocus?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}