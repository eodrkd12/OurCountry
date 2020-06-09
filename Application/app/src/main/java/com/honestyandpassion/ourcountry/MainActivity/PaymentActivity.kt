package com.honestyandpassion.ourcountry.MainActivity

import android.os.Bundle
import android.os.Message
import android.util.Log
import com.honestyandpassion.ourcountry.Class.ToolbarSetting
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_payment.*
import kr.co.bootpay.Bootpay
import kr.co.bootpay.BootpayAnalytics
import kr.co.bootpay.enums.Method
import kr.co.bootpay.enums.PG
import kr.co.bootpay.enums.UX
import kr.co.bootpay.model.BootExtra
import kr.co.bootpay.model.BootUser
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class PaymentActivity : ToolbarSetting() {

    var registerTitle:String?=null
    var registerPrice:String?=null
    var registerId:Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        var toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_payment)
        toolbarBinding(toolbar, "")

        var intent = intent

        registerTitle= intent.getStringExtra("registerTitle")
        registerPrice= intent.getStringExtra("registerPrice")
        registerId=intent.getIntExtra("registerId",0)

        text_paymentprice.text = registerPrice
        text_paymentprice1.text = registerPrice
        text_paymentregistertitle.text = registerTitle

        BootpayAnalytics.init(this, "59a4d326396fa607cbe75de5");

        btn_payment.setOnClickListener {
            when (radiogroup_payment.checkedRadioButtonId) {
                R.id.radio_paymentaccount -> {
                    payment("계좌이체")
                }
                R.id.radio_paymentcard -> {
                    payment("카드")
                }
                R.id.radio_paymentkakaopay -> {
                    payment("카카오")
                }
            }
        }
    }

    fun payment(type:String){
        var bootUser = BootUser().setPhone("010-4154-5154")
        var bootExtra = BootExtra().setQuotas(IntArray(3){0;2;3});

        var orderId="${registerTitle}_${ZonedDateTime.now(ZoneId.of("Asia/Seoul"))}"

        var paymentDate=ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        var pg:PG?=null
        var method:Method?=null
        when(type){
            "계좌이체" -> {
                pg=PG.INICIS
                method=Method.BANK
            }
            "카드" -> {
                pg=PG.NICEPAY
                method=Method.CARD
            }
            "카카오" -> {
                pg=PG.KAKAO
                method=Method.EASY
            }
        }

        Bootpay.init(fragmentManager)
            .setApplicationId("59a4d326396fa607cbe75de5") // 해당 프로젝트(안드로이드)의 application id 값
            .setPG(pg!!) // 결제할 PG 사
            .setMethod(method!!) // 결제수단
            .setContext(this)
            .setBootUser(bootUser)
            .setBootExtra(bootExtra)
            .setUX(UX.PG_DIALOG)
            //.setUserPhone(buyerPhone) // 구매자 전화번호
            .setName(registerTitle) // 결제할 상품명
            .setOrderId(orderId) // 결제 고유번호expire_month
            .setPrice(registerPrice!!.toInt()) // 결제할 금액
            .onConfirm{
                // 결제가 진행되기 바로 직전 호출되는 함수로, 주로 재고처리 등의 로직이 수행
                Bootpay.confirm(it)
                //else Bootpay.removePaymentWindow(); // 재고가 없어 중간에 결제창을 닫고 싶을 경우
                //Log.d("confirm", message);
            }
            .onDone {
                // 결제완료시 호출, 아이템 지급 등 데이터 동기화 로직을 수행합니다
                // 주문내역 서버에 입력
                VolleyService.paymentReq(orderId,registerId, UserInfo.ID,registerPrice!!,paymentDate,type,registerTitle,this)
                var handler=ChatActivity.HANDLER
                var msg=handler!!.obtainMessage()
                msg.what=0
                handler.sendMessage(msg)
                finish()
            }
            .onReady {
                // 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
            }
            .onCancel {
                // 결제 취소시 호출
            }
            .onError {
                // 에러가 났을때 호출되는 부분
                Log.d("test",it)
            }.onClose {
                //결제창이 닫힐때 실행되는 부분
            }.request()
    }
}
