package dgtic.unam.modulosiete.Dialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import dgtic.unam.modulosiete.R

@Suppress("DEPRECATION")
class MyDialogEdit(private val title:String, private val message:String, context: Context,val id: String,val position: Int) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val spinner = Spinner(context)
        val spinnerArrayAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.status_array, android.R.layout.simple_spinner_item)
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerArrayAdapter

        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Aceptar") { dialog, x ->
                sendResult(1,id, position)
            }
            .setNegativeButton("Cancelar") { dialog, x ->
                sendResult(0,id, position)
            }
        return builder.create()
    }
    private fun sendResult(delete: Int,id :String, position:Int) {
        val bundle = Bundle()
        bundle.putInt("delete", delete)
        bundle.putString("id", id)
        bundle.putInt("position", position)
        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, Intent().putExtras(bundle))
        dismiss()
    }

}