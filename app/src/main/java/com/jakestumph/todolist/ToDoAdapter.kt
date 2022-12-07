package com.jakestumph.todolist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*

class ToDoAdapter (
    private val todos: MutableList<ToDo>
    ) : RecyclerView.Adapter<ToDoAdapter.TodoViewHolder>(){

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }

    fun addToDo(todo: ToDo) {
        todos.add(todo)
        notifyItemInserted(todos.size-1)
    }

    fun deleteDoneToDos(){
        todos.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(tvToDoTitle: TextView, isChecked: Boolean){
        if(isChecked) {
            tvToDoTitle.paintFlags = tvToDoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvToDoTitle.paintFlags = tvToDoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currToDo = todos[position]
        holder.itemView.apply {
            tvToDoTitle.text = currToDo.title
            cbDone.isChecked = currToDo.isChecked
            toggleStrikeThrough(tvToDoTitle, currToDo.isChecked)
            cbDone.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(tvToDoTitle, isChecked)
                currToDo.isChecked = !currToDo.isChecked
            }
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}