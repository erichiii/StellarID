package com.example.stellarid

import android.os.Bundle
import android.widget.EditText
import android.app.DatePickerDialog
import android.content.Intent
import android.widget.ImageButton
import java.util.Calendar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        // Find the EditText for the birthday
        val editTextBirthday = findViewById<EditText>(R.id.editTextBirthday)

        // Set a click listener on the birthday field
        editTextBirthday.setOnClickListener {
            showDatePickerDialog(editTextBirthday)
        }

        val getStartedButton = findViewById<ImageButton>(R.id.registerButton)

        getStartedButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showDatePickerDialog(dateField: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Create the DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // The months are 0-indexed, so we add 1 for display
                val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                dateField.setText(selectedDate)
            },
            year,
            month,
            day
        )
        // Show the dialog
        datePickerDialog.show()
    }
}