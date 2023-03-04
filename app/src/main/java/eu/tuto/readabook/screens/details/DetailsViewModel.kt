package eu.tuto.readabook.screens.details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.tuto.readabook.data.Resource
import eu.tuto.readabook.model.Item
import eu.tuto.readabook.repository.BookRepository
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: BookRepository): ViewModel() {
    suspend fun getBookInfo(bookId: String):Resource<Item> {
        return repository.getBookInfo(bookId)
    }
}