package eu.tuto.readabook.di


import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import eu.tuto.readabook.network.BooksApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.tuto.readabook.repository.BookRepository
import eu.tuto.readabook.repository.FireRepository
import eu.tuto.readabook.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton

    @Provides
    fun provideFireStoreRepository() =
        FireRepository(queryBook = FirebaseFirestore.getInstance().collection("book"))

    @Singleton
    @Provides
    fun provideBookRepository(api: BooksApi) = BookRepository(api)


    @Singleton
    @Provides
    fun provideBookApi(): BooksApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }
}