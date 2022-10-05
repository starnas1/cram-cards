package com.example.cramcards

import androidx.room.*

@Dao
interface FlashcardDao {
    @Query("SELECT * FROM flashcard")
    fun getAll(): List<Flashcard>

    @Insert
    fun insertAll(vararg flashcards: Flashcard)

    @Delete
    fun delete(flashcard: Flashcard)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(flashcard: Flashcard)
}
