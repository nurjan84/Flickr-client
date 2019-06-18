package com.nurzhan.flickr

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.nurzhan.flickr.api.FlickrApi
import com.nurzhan.flickr.api.FlickrRepository
import com.nurzhan.flickr.dagger.components.AppComponent
import com.nurzhan.flickr.dagger.components.DaggerAppComponent
import com.nurzhan.flickr.dagger.modules.AppModule
import com.nurzhan.flickr.room.dao.SearchSuggestionsDao
import com.nurzhan.flickr.room.db.MyDatabase
import com.nurzhan.flickr.room.entities.SearchSuggestions
import it.cosenonjaviste.daggermock.DaggerMockRule
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import javax.inject.Inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(RobolectricTestRunner::class)
class ExampleUnitTest {

    private lateinit var suggestionsDao: SearchSuggestionsDao
    private lateinit var db: MyDatabase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockitoRule: DaggerMockRule<AppComponent> =
        DaggerMockRule(AppComponent::class.java)
            .customizeBuilder(DaggerMockRule.BuilderCustomizer<AppComponent.Builder> { builder ->
                builder.application(getApp())
            })
            .set { component ->
                repo = component.getFlickrRepository()
                flickrService = component.getFlickrAPI()
            }

    private lateinit var repo: FlickrRepository
    private lateinit var flickrService: FlickrApi

    private fun getApp():Application{
        return InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as Application
    }


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext<Context>(), MyDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        suggestionsDao = db.searchSuggestionsDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testInsert(){
        suggestionsDao.insert(SearchSuggestions("test"))

        suggestionsDao.getAllLike("test%")
            .test()
            .assertValue {
                it[0] == "test"
            }
    }

    @Test
    fun getPhotosTest(){
        repo.loadPhotos("Almaty", 1)
            .test()
            .assertNoErrors()
            .assertValue {
                it.photos.photo.isNotEmpty()
            }
    }
}
