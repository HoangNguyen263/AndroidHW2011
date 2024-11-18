package vn.edu.hust.studentman

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), DialogFragment.DialogListener {
  lateinit var add: Button
  lateinit var students: MutableList<StudentModel>
  lateinit var studentAdapter: StudentAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    students = mutableListOf(
      StudentModel("Nguyễn Văn An", "SV001"),
      // ... other students
    )

    add = findViewById(R.id.btn_add_new)
    add.setOnClickListener {
      showAlertDialog()
    }

    studentAdapter = StudentAdapter(students, supportFragmentManager)

    findViewById<RecyclerView>(R.id.recycler_view_students).run {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }
  }

  override fun onDialogPositiveClick(userName: String, userId: String) {
    // Handle the data received from the dialog
    val newStudent = StudentModel(userName, userId)
    students.add(newStudent)
    studentAdapter.notifyItemInserted(students.size - 1)
  }

  private fun showAlertDialog() {
    val dialog = DialogFragment()
    val fragmentManager: FragmentManager = supportFragmentManager
    dialog.show(fragmentManager, "Enter your information")
  }
}