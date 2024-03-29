var express = require('express');
var router = express.Router();
var db_payment = require('../public/SQL/payment_sql')()
var db_user = require('../public/SQL/user_sql')()

router.get('/', function (req, res, next) {
    db_payment.get_payment(function (err, result) {
        res.send(result)
    })
})
router.post('/', function (req, res, next) {
    var orderId = req.body.order_id
    var registerId = req.body.register_id
    var userId = req.body.user_id
    var registerPrice = req.body.register_price
    var paymentDate = req.body.payment_date
    var type = req.body.type
    var registerTitle = req.body.register_title

    db_payment.insert_payment(orderId, registerId, userId, registerPrice, paymentDate, type, registerTitle)
    db_user.save_point(seller, registerPrice)

    var object = new Object()
    object.result = "success"
    res.send(object)
});
router.post('/search', function (req, res, next) {
    var search = req.body.search
    console.log(search)

    db_payment.search_payment(search, function (err, result) {
        if (err) console.log(err)
        else res.send(result)
    })
})

module.exports = router;
