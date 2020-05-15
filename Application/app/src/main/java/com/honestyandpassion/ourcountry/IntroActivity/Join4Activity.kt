package com.honestyandpassion.ourcountry.IntroActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.honestyandpassion.ourcountry.Adapter.BankAdapter
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_join4.*

class Join4Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join4)

        grid_bank.bringToFront()

        edit_bank.setOnClickListener {
            grid_bank.visibility= View.VISIBLE
        }
    }
}
