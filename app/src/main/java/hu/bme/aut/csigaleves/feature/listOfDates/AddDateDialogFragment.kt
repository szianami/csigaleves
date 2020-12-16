package hu.bme.aut.csigaleves.feature.listOfDates

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatDialogFragment
import hu.bme.aut.csigaleves.R

class AddDateDialogFragment : AppCompatDialogFragment() {
    private var listener: AddDayDialogListener? = null
    private var editText: EditText? = null

    interface AddDayDialogListener {
        fun onDateAdded(day: String)
        /// ????
        fun onDateRemoved(date: String?)
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listener = if (activity is AddDayDialogListener) {
            activity as AddDayDialogListener?
        } else {
            throw RuntimeException("Activity must implement AddCityDialogListener interface!")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_day)
            .setView(contentView)
            .setPositiveButton(R.string.ok) { _, _ ->
                listener?.onDateAdded(
                    editText?.text.toString()
                )
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    private val contentView: View
        get() {
            val view: View = LayoutInflater.from(context).inflate(R.layout.dialog_new_day, null)
            editText = view.findViewById(R.id.NewDayDialogEditText)
            return view
        }
}