package com.example.stellarid

import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val givenName = intent.getStringExtra("GIVEN_NAME")
        val surname = intent.getStringExtra("SURNAME")
        val birthday = intent.getStringExtra("BIRTHDAY")
        val number = intent.getStringExtra("NUMBER")
        val gender = intent.getStringExtra("GENDER")

        val userNameTextView = findViewById<TextView>(R.id.userName)
        val birthdateValue = findViewById<TextView>(R.id.birthdateValue)
        val ageValue = findViewById<TextView>(R.id.ageValue)
        val genderValue = findViewById<TextView>(R.id.genderValue)
        val numberValue = findViewById<TextView>(R.id.numberValue)
        val telecomValue = findViewById<TextView>(R.id.telecomValue)
        val infoContainer = findViewById<FrameLayout>(R.id.infoContainer)

        if (!givenName.isNullOrEmpty() && !surname.isNullOrEmpty()) {
            val fullName = "$givenName $surname!"
            userNameTextView.text = fullName
        } else {
            userNameTextView.text = "User!"
        }

        if (!birthday.isNullOrEmpty()) {
            birthdateValue.text = birthday
            val age = calculateAge(birthday)
            ageValue.text = age.toString()
            val zodiacSign = getZodiacSign(birthday)

            val backgroundResource = getZodiacBackground(zodiacSign)
            infoContainer.setBackgroundResource(backgroundResource)
        } else {
            birthdateValue.text = "Invalid Date"
            ageValue.text = ""
        }

        if (gender != null) {
            genderValue.text = gender
        }

        if (number != null) {
            numberValue.text = number
            telecomValue.text = getTelecom(number)
        }
    }

    private fun calculateAge(birthday: String): Int {
        try {
            val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
            val birthDate: Date = sdf.parse(birthday) ?: return 0
            val birthDateCal = Calendar.getInstance()
            birthDateCal.time = birthDate

            val today = Calendar.getInstance()

            if (birthDateCal.after(today)) {
                return 0
            }

            var age = today.get(Calendar.YEAR) - birthDateCal.get(Calendar.YEAR)
            if (today.get(Calendar.DAY_OF_YEAR) < birthDateCal.get(Calendar.DAY_OF_YEAR)) {
                age--
            }
            return age
        } catch (e: ParseException) {
            Log.e("HomeActivity", "Error parsing birthday for age: $birthday", e)
            return 0
        }
    }

    private fun getZodiacSign(birthday: String): String {
        try {
            val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
            val birthDate: Date = sdf.parse(birthday) ?: return "Unknown"
            val birthDateCal = Calendar.getInstance()
            birthDateCal.time = birthDate

            if (birthDateCal.after(Calendar.getInstance())) {
                return "Unknown"
            }

            val month = birthDateCal.get(Calendar.MONTH) + 1
            val day = birthDateCal.get(Calendar.DAY_OF_MONTH)

            return when {
                (month == 3 && day >= 21) || (month == 4 && day <= 19) -> "Aries"
                (month == 4 && day >= 20) || (month == 5 && day <= 20) -> "Taurus"
                (month == 5 && day >= 21) || (month == 6 && day <= 20) -> "Gemini"
                (month == 6 && day >= 21) || (month == 7 && day <= 22) -> "Cancer"
                (month == 7 && day >= 23) || (month == 8 && day <= 22) -> "Leo"
                (month == 8 && day >= 23) || (month == 9 && day <= 22) -> "Virgo"
                (month == 9 && day >= 23) || (month == 10 && day <= 22) -> "Libra"
                (month == 10 && day >= 23) || (month == 11 && day <= 21) -> "Scorpio"
                (month == 11 && day >= 22) || (month == 12 && day <= 21) -> "Sagittarius"
                (month == 12 && day >= 22) || (month == 1 && day <= 19) -> "Capricorn"
                (month == 1 && day >= 20) || (month == 2 && day <= 18) -> "Aquarius"
                (month == 2 && day >= 19) || (month == 3 && day <= 20) -> "Pisces"
                else -> "Unknown"
            }
        } catch (e: ParseException) {
            Log.e("HomeActivity", "Error parsing birthday for zodiac: $birthday", e)
            return "Unknown"
        }
    }

    private fun getTelecom(number: String): String {
        if (number.length < 4) return "Unknown"
        val prefix = number.substring(0, 4)

        return when (prefix) {
            "0817" -> "Globe"
            "0895", "0896", "0897", "0898" -> "DITO"
            "0905", "0906", "0915", "0916", "0917", "0926", "0927", "0935", "0936", "0937", "0945", "0953", "0954", "0955", "0956", "0965", "0966", "0967", "0975", "0977", "0978", "0979", "0995", "0996", "0997" -> "Globe or TM"
            "0907", "0909", "0910", "0912", "0930", "0938", "0946", "0948", "0950" -> "TNT (Talk N’ Text)"
            "0908", "0918", "0919", "0920", "0921", "0928", "0929", "0939", "0947", "0951", "0961", "0998", "0999" -> "Smart"
            "0922", "0923", "0924", "0925", "0931", "0932", "0933", "0934", "0940", "0941", "0942", "0943", "0973", "0974" -> "Sun Cellular"
            "0976" -> "Globe or GOMO"
            "0991", "0992", "0993", "0994" -> "DITO"
            else -> "Unknown"
        }
    }

    private fun getZodiacBackground(zodiacSign: String): Int {
        return when (zodiacSign) {
            "Aries" -> R.drawable.aries
            "Taurus" -> R.drawable.taurus
            "Gemini" -> R.drawable.gemini
            "Cancer" -> R.drawable.cancer
            "Leo" -> R.drawable.leo
            "Virgo" -> R.drawable.virgo
            "Libra" -> R.drawable.libra
            "Scorpio" -> R.drawable.scorpio
            "Sagittarius" -> R.drawable.sagittarius
            "Capricorn" -> R.drawable.capricorn
            "Aquarius" -> R.drawable.aquarius
            "Pisces" -> R.drawable.pisces
            else -> R.drawable.form_background
        }
    }
}
