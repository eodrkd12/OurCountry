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


module.exports = router;
