package vn.edu.hust.studentman

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class DialogFragment : DialogFragment() {
    interface DialogListener {
        fun onDialogPositiveClick(userName: String, userId: String)
    }

    private lateinit var listener: DialogListener
    private var studentName: String? = null
    private var studentId: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as DialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() + " must implement DialogListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        studentName = arguments?.getString("studentName")
        studentId = arguments?.getString("studentId")

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_dialog, null)

            val userNameEditText: EditText = view.findViewById(R.id.editName)
            val userIdEditText: EditText = view.findViewById(R.id.editId)

            userNameEditText.setText(studentName)
            userIdEditText.setText(studentId)

            builder.setView(view)
                .setPositiveButton("OK") { dialog, id ->
                    val userName = userNameEditText.text.toString()
                    val userId = userIdEditText.text.toString()
                    listener.onDialogPositiveClick(userName, userId)
                }
                .setNegativeButton("Cancel") { dialog, id ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        fun newInstance(studentName: String, studentId: String): DialogFragment {
            val fragment = DialogFragment()
            val args = Bundle()
            args.putString("studentName", studentName)
            args.putString("studentId", studentId)
            fragment.arguments = args
            return fragment
        }
    }
}