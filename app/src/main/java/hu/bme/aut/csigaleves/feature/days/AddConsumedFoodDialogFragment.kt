package hu.bme.aut.csigaleves.feature.days

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.google.android.material.internal.ViewUtils.getContentView
import hu.bme.aut.csigaleves.R
import hu.bme.aut.csigaleves.feature.data.ConsumedFood

class AddConsumedFoodDialogFragment : DialogFragment()  {

    private lateinit var nameEditText: EditText
    private lateinit var amountEditText: EditText

    //callback interface, amelyen keresztül a dialógust megjelenítő Activity értesülhet az új elem létrehozásáról.
    interface NewConsumedFoodDialogListener {
        fun onConsumedFoodAddded(food: ConsumedFood)
    }

    private lateinit var listener: NewConsumedFoodDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewConsumedFoodDialogListener
            ?: throw RuntimeException("Activity must implement the NewConsumedFoodDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.add_consumed_food)
            .setView(getContentView())
            .setPositiveButton("Add") { dialogInterface, i ->
                if (isValid()) {
                    listener.onConsumedFoodAddded(getFoodFromDialog())
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
    }

    private fun isValid() : Boolean {
        // ha talál iylet az apibaaaan, de egyedlőre ha nem üres
       return (nameEditText.text.isNotEmpty() && amountEditText.text.isNotEmpty())
    }

    private fun getFoodFromDialog() : ConsumedFood {
        val id = null
        val name = nameEditText.text.toString()
        val amount = amountEditText.text.toString().toInt()
        return ConsumedFood(id, name, amount)
    }

    companion object {
        const val TAG = "AddConsumedFoodDialogFragment"
    }

    @SuppressLint("InflateParams")
    private fun getContentView(): View {
        val contentView = LayoutInflater.from(context).inflate(R.layout.dialog_add_consumed_food, null)
        nameEditText = contentView.findViewById(R.id.NameEditText)
        amountEditText = contentView.findViewById(R.id.AmountEditText)
        return contentView
    }
}