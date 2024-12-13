package com.practicum.playlistmaker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.internal.ViewUtils.hideKeyboard

class SearchActivity : AppCompatActivity() {

    private var searchRequest: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val backButton = findViewById<ImageView>(R.id.btn_back)
        val inputSearch = findViewById<EditText>(R.id.input_search)
        val clearButton = findViewById<ImageView>(R.id.input_clear)

        // Если есть сохранённый текст, восстановить его
        savedInstanceState?.let {
            searchRequest = it.getString("search_request")
            inputSearch.setText(searchRequest)
        }

        clearButton.setOnClickListener {
            inputSearch.setText("")
            hideKeyboard()
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
                }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                clearButton.visibility = clearButtonVisibility(s)
                searchRequest = s.toString() // Сохраняем текущий текст
                }

            override fun afterTextChanged(s: Editable?) {
                 // empty
                 }
             }
        inputSearch.addTextChangedListener(simpleTextWatcher)

        backButton.setOnClickListener {
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

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

    // Функция для скрытия клавиатуры
    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocus = currentFocus
        if (currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }
}