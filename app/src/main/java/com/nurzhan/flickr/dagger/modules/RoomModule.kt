package com.nurzhan.flickr.dagger.modules

import android.content.Context
import androidx.room.Room
import com.nurzhan.flickr.dagger.scopes.AppScope
import com.nurzhan.flickr.room.dao.PhotosDao
import com.nurzhan.flickr.room.dao.SearchSuggestionsDao
import com.nurzhan.flickr.room.db.MyDatabase
import dagger.Module
import dagger.Provides

@Module
open class RoomModule {

    @AppScope
    @Provides
    open fun providesRoomDatabase(context: Context): MyDatabase {
        return Room.databaseBuilder(context.applicationContext,
            MyDatabase::class.java, "flickr.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @AppScope
    @Provides
    open fun providesSearchDao(db: MyDatabase): SearchSuggestionsDao {
        return db.searchSuggestionsDao()
    }

    @AppScope
    @Provides
    open fun providesPhotosDao(db: MyDatabase): PhotosDao {
        return db.photosDao()
    }


}