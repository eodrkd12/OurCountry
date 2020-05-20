var express=require('express');
var router=express.Router();

var db_register=require('../public/SQL/register_sql')();

router.get('/',function(req,res,ext){
  res.send("mm")
})

router.post('/insert', function(req, res, next){
  var userId=req.body.user_id
  var registerTitle=req.body.register_title
  var productCategory=req.body.product_category
  var productSubCategory=req.body.product_subcategory
  var productType=req.body.product_type
  var productStatus=req.body.product_status
  var productBrand=req.body.product_brand
  var productPrice=req.body.product_price
  var sellerStore=req.body.seller_store
  var registerContent=req.body.register_content
  var tradeOption=req.body.trade_option
  var sellerAddress=req.body.seller_address
  var registerDate=req.body.register_date
  var registerLike=req.body.register_like
  var registerView=req.body.register_view

  db_register.register_product(userId, registerTitle, productCategory, productSubCategory, productType, productStatus, productBrand, productPrice, sellerStore, 
    registerContent, tradeOption, sellerAddress, registerDate, registerLike, registerView)

  db_register.get_register(userId,registerTitle,registerDate,function(err,result){
    if(err) console.log(err)
    else res.send(result[0])
  })

})

router.post('/insert/image', function(req,res,next){
  var registerId=req.body.register_id
  var registerTitle=req.body.register_title
  var image=req.body.image

  db_register.insert_image(registerId,registerTitle,image,function(err,result){
    if(err) console.log(err)
    else {
      var object=new Object()
      object.result="success"
      res.send(object)
    }
  })
})

router.post('/recent',function(req,res,next){
  db_register.get_register_recent(function(err,result){
    if(err) console.log(err)
    else res.send(result)
  })
})

router.post('/popular',function(req,res,next){
  db_register.get_register_popular(function(err,result){
    if(err) console.log(err)
    else res.send(result)
  })
})

module.exports = router;
