var express = require('express');
var router = express.Router();
var db_payment=require('../public/SQL/payment_sql')()

router.post('/', function(req, res, next) {
    var orderId=req.body.order_id
    var registerId=req.body.register_id
    var userId=req.body.user_id
    var registerPrice=req.body.register_price
    var paymentDate=req.body.payment_date
    var type=req.body.type
    var registerTitle=req.body.register_title

    db_payment.insert_payment(orderId,registerId,userId,registerPrice,paymentDate,type,registerTitle)

    var object=new Object()
    object.result="success"
    res.send(object)
});


module.exports = router;
