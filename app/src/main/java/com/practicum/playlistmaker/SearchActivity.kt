package com.practicum.playlistmaker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.practicum.playlistmaker.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private var searchRequest: String? = null
    private lateinit var searchBinding: ActivitySearchBinding
    private lateinit var trackAdapter: TrackAdapter


    val trackList = arrayListOf(
        Track(
            trackName = "Smells Like Teen Spirit",
            artistName = "Nirvana",
            trackTime = "5:01",
            artworkUrl100 = "https://is5-ssl.mzstatic.com/image/thumb/Music115/v4/7b/58/c2/7b58c21a-2b51-2bb2-e59a-9bb9b96ad8c3/00602567924166.rgb.jpg/100x100bb.jpg"
        ),
        Track(
            trackName = "Billie Jean",
            artistName = "Michael Jackson",
            trackTime = "4:35",
            artworkUrl100 = "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/3d/9d/38/3d9d3811-71f0-3a0e-1ada-3004e56ff852/827969428726.jpg/100x100bb.jpg"
        ),
        Track(
            trackName = "Stayin' Alive",
            artistName = "Bee Gees",
            trackTime = "4:10",
            artworkUrl100 = "https://is4-ssl.mzstatic.com/image/thumb/Music115/v4/1f/80/1f/1f801fc1-8c0f-ea3e-d3e5-387c6619619e/16UMGIM86640.rgb.jpg/100x100bb.jpg"
        ),
        Track(
            trackName = "Whole Lotta Love",
            artistName = "Led Zeppelin",
            trackTime = "5:33",
            artworkUrl100 = "https://is2-ssl.mzstatic.com/image/thumb/Music62/v4/7e/17/e3/7e17e33f-2efa-2a36-e916-7f808576cf6b/mzm.fyigqcbs.jpg/100x100bb.jpg"
        ),
        Track(
            trackName = "Sweet Child O'Mine",
            artistName = "Guns N' Roses",
            trackTime = "5:03",
            artworkUrl100 = "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/a0/4d/c4/a04dc484-03cc-02aa-fa82-5334fcb4bc16/18UMGIM24878.rgb.jpg/100x100bb.jpg"
        ),
    )

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

        // Инициализируем адаптер
        trackAdapter = TrackAdapter(trackList)
        searchBinding.rvTracks.adapter = trackAdapter
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