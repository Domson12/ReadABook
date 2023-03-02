package eu.tuto.readabook.screens.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.tuto.readabook.data.DataOrException
import eu.tuto.readabook.model.Item
import eu.tuto.readabook.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {
    var listOfBooks: MutableState<DataOrException<List<Item>, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, true, Exception("")))

    init {
        searchBooks("android")
    }

    fun searchBooks(query: String) {

        viewModelScope.launch(Dispatchers.IO) {
            if (query.isEmpty()) {
                return@launch
            }

            //listOfBooks.value.loading = true
            listOfBooks.value = repository.getBooks(query)

            if (listOfBooks.value.data.toString().isNotEmpty()) {
                listOfBooks.value.loading = false
            }
        }
    }
}