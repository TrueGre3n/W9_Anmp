package com.ubaya.todoapp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg todo:Todo)

    @Query("SELECT * FROM  todo WHERE is_done=0 ORDER BY priority DESC")
    fun selectAllTodo(): List<Todo>

    @Query("SELECT * FROM todo WHERE uuid= :id")
    fun selectTodo(id:Int): Todo

    @Delete
    fun deleteTodo(todo:Todo)

    //update cara 1
    @Query("UPDATE todo SET title=:title, notes=:notes, priority=:priority, is_done=:isDone WHERE uuid = :id")
    fun update(title:String, notes:String, priority:Int, isDone:Int, id:Int)

    @Query("UPDATE todo SET is_done=1 WHERE uuid = :id")
    fun setStatus(id:Int)

    //cara kedua
    @Update
    fun updateTodo(todo:Todo)

}