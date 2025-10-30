package com.example.stellarid

import android.os.Bundle
import android.widget.EditText
import android.app.DatePickerDialog
import android.content.Intent
import android.widget.ImageButton
import android.widget.RadioGroup
import android.widget.RadioButton
import android.widget.Toast
import java.util.Calendar
import androidx.appcompat.app.AppCompatActivity

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val editTextBirthday = findViewById<EditText>(R.id.editTextBirthday)

        editTextBirthday.setOnClickListener {
            showDatePickerDialog(editTextBirthday)
        }

        val registerButton = findViewById<ImageButton>(R.id.registerButton)

        registerButton.setOnClickListener {
            val givenNameEditText = findViewById<EditText>(R.id.editTextGivenName)
            val surnameEditText = findViewById<EditText>(R.id.editTextSurname)
            val birthdayEditText = findViewById<EditText>(R.id.editTextBirthday)
            val mobileEditText = findViewById<EditText>(R.id.editTextMobile)
            val genderRadioGroup = findViewById<RadioGroup>(R.id.genderRadioGroup)

            val givenName = givenNameEditText.text.toString()
            val surname = surnameEditText.text.toString()
            val birthday = birthdayEditText.text.toString()
            val mobile = mobileEditText.text.toString()

            val selectedGenderId = genderRadioGroup.checkedRadioButtonId
            val gender = if (selectedGenderId != -1) {
                findViewById<RadioButton>(selectedGenderId).text.toString()
            } else {
                ""
            }

            var hasError = false
            if (givenName.isEmpty()) {
                givenNameEditText.error = "Field can't be empty."
                hasError = true
            }
            if (surname.isEmpty()) {
                surnameEditText.error = "Field can't be empty."
                hasError = true
            }
            if (birthday.isEmpty()) {
                birthdayEditText.error = "Field can't be empty."
                hasError = true
            }
            if (mobile.isEmpty()) {
                mobileEditText.error = "Field can't be empty."
                hasError = true
            } else if (!mobile.matches(Regex("^\\d{11}$"))) {
                mobileEditText.error = "Mobile number must be 11 digits."
                hasError = true
            }
            if (gender.isEmpty()) {
                Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show()
                hasError = true
            }

            if (!hasError) {
                val intent = Intent(this, HomeActivity::class.java)

                intent.putExtra("GIVEN_NAME", givenName)
                intent.putExtra("SURNAME", surname)
                intent.putExtra("BIRTHDAY", birthday)
                intent.putExtra("NUMBER", mobile)
                intent.putExtra("GENDER", gender)

                startActivity(intent)
                finish()
            }
        }

    }

    private fun showDatePickerDialog(dateField: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = String.format("%02d/%02d/%d", selectedMonth + 1, selectedDay, selectedYear)
                dateField.setText(selectedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }
}