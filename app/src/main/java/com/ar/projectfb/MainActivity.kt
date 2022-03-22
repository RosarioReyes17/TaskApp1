package com.ar.projectfb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ar.projectfb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TaskClickInterface, TaskClickDeleteInterface {

    lateinit var taskRV: RecyclerView
    lateinit var addFAB: FloatingActionButton
    lateinit var binding: ActivityMainBinding
    lateinit var viewModal: TaskViewModal


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        taskRV = binding.idRVTasks
        addFAB = binding.idBtnAddTask
        taskRV.layoutManager = LinearLayoutManager(this)

        val taskRVAdapter = TaskRVAdapter(this, this, this)
        taskRV.adapter = taskRVAdapter
        viewModal = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(TaskViewModal::class.java)
        viewModal.allTask.observe(this, Observer { list->
            list?.let {
                taskRVAdapter.updateList(it)
            }
        })



        addFAB.setOnClickListener {
            val intent = Intent(this@MainActivity, TaskActivity::class.java)
            startActivity(intent)
            this.finish()
        }



    }

    override fun onTaskClick(task: Task) {

        val intent = Intent(this@MainActivity,TaskActivity::class.java)
        intent.putExtra("TaskType","Edit")
        intent.putExtra("TaskTitle",task.taskTitle)
        intent.putExtra("TaskDescription",task.taskDescription)
        intent.putExtra("TaskID",task.id)
        startActivity(intent)
        this.finish()

    }

    override fun onDeleteIconClick(task: Task) {
        viewModal.deleteTask(task)
        Toast.makeText(this, "${task.taskTitle} Deleted", Toast.LENGTH_LONG).show()
    }
}