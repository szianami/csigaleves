package hu.bme.aut.csigaleves.feature.day

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatDialogFragment
import hu.bme.aut.csigaleves.R
import kotlinx.coroutines.NonCancellable.cancel

class AddDayDialogFragment : AppCompatDialogFragment() {
    private var listener: AddDayDialogListener? = null
    private var editText: EditText? = null

    interface AddDayDialogListener {
        fun onDayAdded(day: String)
        /// ????
        fun onDayRemoved(day: String?)
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
                listener?.onDayAdded(
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