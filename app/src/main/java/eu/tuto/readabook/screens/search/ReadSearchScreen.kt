@file:Suppress("UNREACHABLE_CODE")

package eu.tuto.readabook.screens.search

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import eu.tuto.readabook.components.InputField
import eu.tuto.readabook.components.ReaderAppBar
import eu.tuto.readabook.model.MBook
import eu.tuto.readabook.navigation.ReadScreens
import androidx.lifecycle.viewmodel.compose.viewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = viewModel()) {
    Scaffold(topBar = {
        ReaderAppBar(
            title = "Search Books",
            icon = Icons.Default.ArrowBack,
            navController = navController,
            showProfile = false
        ) {
            navController.navigate(ReadScreens.HomeScreen.name)
        }
    }) {
        Surface {
            Column() {
                SearchForm(
                    navController = navController, modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    viewModel = viewModel
                ) {query ->
                    viewModel.searchBooks(query)

                }
            }
            Spacer(modifier = Modifier.height(13.dp))
            BookList(navController = navController)
        }
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
    loading: Boolean = false,
    hint: String = "Search",
    navController: NavController,
    onSearch: (String) -> Unit = {}
) {
    Column {
        val searchQueryState = rememberSaveable { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQueryState.value) {
            searchQueryState.value.trim().isNotEmpty()
        }

        InputField(valueState = searchQueryState,
            labelId = "search",
            enabled = true,
            onAction = KeyboardActions {
                if (!valid) {
                    return@KeyboardActions
                    onSearch(searchQueryState.value.trim())
                    searchQueryState.value = ""
                    keyboardController?.hide()
                }
            })

        Spacer(modifier = Modifier.height(13.dp))
        BookList(navController = navController)
        LazyColumn(content = {

        })
    }
}

@Composable
fun BookList(navController: NavController) {
    val listOfBooks = listOf(
        MBook(id = "da", title = "Hello", authors = "all2", notes = "none"),
        MBook(id = "da", title = "Hello2", authors = "all1", notes = "none"),
        MBook(id = "da", title = "Hello1", authors = "al1l", notes = "none"),
        MBook(id = "da", title = "Hello4", authors = "all1", notes = "none")
    )

    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp)) {
        items(items = listOfBooks) { book ->
            BookRow(book, navController)
        }
    }
}

@Composable
fun BookRow(book: MBook, navController: NavController) {
    Card(modifier = Modifier
        .clickable { }
        .fillMaxWidth()
        .height(100.dp)
        .padding(3.dp),
        shape = RectangleShape,
        elevation = 7.dp) {
        Row(modifier = Modifier.padding(5.dp), verticalAlignment = Alignment.Top) {
            val imageUrl =
                "http://books.google.pl/books?id=pY-goAEACAAJ&dq=android&hl=&cd=1&source=gbs_api"
            Image(
                painter = rememberImagePainter(data = imageUrl), contentDescription = "book image",
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .padding(4.dp)
            )
            Column {
                Text(text = book.title.toString(), overflow = TextOverflow.Ellipsis)
                Text(
                    text = "Author: ${book.authors}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.caption
                )
                //TODO: Add more fields later
            }
        }
    }
}