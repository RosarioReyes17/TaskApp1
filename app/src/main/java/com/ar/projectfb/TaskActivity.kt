package com.ar.projectfb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class TaskActivity : AppCompatActivity() {

    lateinit var taskTitleEdit : EditText
    lateinit var taskDescriptionEdit : EditText
    lateinit var addUpdateBtn : Button
    lateinit var viewModal: TaskViewModal
    var TaskID = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(TaskViewModal::class.java)


        taskTitleEdit = findViewById(R.id.idEditTaskTitle)
        taskDescriptionEdit = findViewById(R.id.idTaskEditDescription)
        addUpdateBtn = findViewById(R.id.idBtnAddUpdateTask)


        // on below line we are getting data passed via an intent.
        val TaskType = intent.getStringExtra("TaskType")
        if (TaskType.equals("Edit")) {

            // on below line we are setting data to edit text.
            val taskTitle = intent.getStringExtra("TaskTitle")
            val taskDescription = intent.getStringExtra("TaskDescription")
            TaskID = intent.getIntExtra("TaskID", -1)
            addUpdateBtn.setText("UPDATE TASK")
            taskTitleEdit.setText(taskTitle)
            taskDescriptionEdit.setText(taskDescription)
        } else {
            addUpdateBtn.setText("SAVE TASK")
        }


        addUpdateBtn.setOnClickListener {
            // on below line we are getting
            // title and desc from edit text.
            val taskTitle = taskTitleEdit.text.toString()
            val taskDescription = taskDescriptionEdit.text.toString()
            val user = FirebaseAuth.getInstance().currentUser!!.uid
            // on below line we are checking the type
            // and then saving or updating the data.
            if (TaskType.equals("Edit")) {
                if (taskTitle.isNotEmpty() && taskDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())

                    val updatedTask = Task(taskTitle, taskDescription, currentDateAndTime, user)
                    updatedTask.id = TaskID
                    viewModal.updateTask(updatedTask)
                    Toast.makeText(this, "TASK UPDATED...", Toast.LENGTH_LONG).show()
                }
            } else {
                if (taskTitle.isNotEmpty() && taskDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    // if the string is not empty we are calling a
                    // add note method to add data to our room database.
                    viewModal.addTask(Task(taskTitle, taskDescription, currentDateAndTime,user))
                    Toast.makeText(this, "$taskTitle ADDED", Toast.LENGTH_LONG).show()
                }


            }

            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }


    }
}