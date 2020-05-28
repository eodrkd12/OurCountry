package com.honestyandpassion.ourcountry.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.honestyandpassion.ourcountry.Class.ToolbarSetting
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : ToolbarSetting() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        var toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_payment)
        toolbarBinding(toolbar, "")

        var intent = intent

        var registerTitle : String = intent.getStringExtra("registerTitle")
        var registerPrice : String = intent.getStringExtra("registerPrice")

        text_paymentprice.text = registerPrice
        text_paymentprice1.text = registerPrice
        text_paymentregistertitle.text = registerTitle
    }
}
