var express = require('express');
var router = express.Router();
var db_register=require('../public/SQL/register_sql')()

router.post('/subcategory', function(req, res, next) {
    var productSubCategory=req.body.product_subcategory

    db_register.get_subcategory(productSubCategory ,function(err,result){
        if(err) console.log(err)
        else {
            var object = new Object()
            object.result="success"
            res.send(object)
            console.log("상품불러오기완료")
        }
    })
});

router.post('/update', function(req, res, next) {
    var userId = req.body.user_id
    var registerTitle = req.body.register_title
    var productCategory = req.body.product_category
    var productSubCategory = req.body.product_subcategory
    var productType = req.body.product_type
    var productStatus = req.body.product_status
    var productBrand = req.body.product_brand
    var productPrice = req.body.product_price
    var sellerStore = req.body.seller_store
    var registerContent = req.body.register_content
    var tradeOption = req.body.trade_option
    var sellerAddress = req.body.seller_address
    var registerDate = req.body.register_date

    db_register.update_product(userId, registerTitle, productCategory, productSubCategory, productType, productStatus, productBrand, productPrice, sellerStore, registerContent, tradeOption, sellerAddress, registerDate, function(err, result) {
            if(err) console.log(err)
            else res.send("success")
            console.log("상품업데이트완료")
        })

})

router.post('/increase/view', function(req, res, next) {
    var registerId=req.body.register_id

    db_register.increase_register_view(registerid, function(err, result){
        if(err) console.log(err)
        else {
            var object = new Object()
            object.result="success"
            res.send(object)
        }
    })
})


module.exports = router;
