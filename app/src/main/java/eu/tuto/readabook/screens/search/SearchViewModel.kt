package eu.tuto.readabook.screens.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.tuto.readabook.data.Resource
import eu.tuto.readabook.model.Item
import eu.tuto.readabook.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {
    var list: List<Item> by mutableStateOf(listOf())
    var isLoading: Boolean by mutableStateOf(true)

    init {
        loadBooks("flutter")
    }

    fun loadBooks(query: String) {

        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                return@launch
            }
            try {
                when (val response = repository.getBooks(query)) {
                    is Resource.Success -> {
                        list = response.data!!
                        if (list.isNotEmpty()) {
                            isLoading = false
                        }
                    }
                    is Resource.Error -> {
                        isLoading = false
                        Log.d("Error", "loadBooks: Failed")
                    }
                    else -> { isLoading = false }
                }
            } catch (e: Exception) {
                isLoading = false
                Resource.Error(data = null, message = "error message: $e")
            }
        }
    }
}