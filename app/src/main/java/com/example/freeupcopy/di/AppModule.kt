package com.example.freeupcopy.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
//    @Provides
//    @Singleton
//    fun provideNoteDatabase(app: Application): TestDatabase {
//        return Room.databaseBuilder(
//            app,
//            TestDatabase::class.java,
//            "note.db"
//        ).build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideNoteDao(db: TestDatabase) = db.testDao
}