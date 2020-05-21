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
  var userNicknmae=req.body.user_nickname

  db_register.register_product(userId, registerTitle, productCategory, productSubCategory, productType, productStatus, productBrand, productPrice, sellerStore, 
    registerContent, tradeOption, sellerAddress, registerDate, registerLike, registerView, userNickname)

  db_register.get_register(userId,registerTitle,registerDate,function(err,result){
    if(err) console.log(err)
    else res.send(result[0])
  })

})

router.post('/image',function(req,res,next){
  var registerId=req.body[0].register_id
	
	db_register.get_image(registerId,function(err,result){
		if(err) console.log(err)
		else{
      var array=new Array()
      for(var i in result){
        var object=new Object()
        object.image_id=result[i].image_id
        object.register_id=result[i].register_id
        object.register_title=result[i].register_title
        const buf=result[i].product_image
        var str=buf.toString()
        object.product_image=str

        array.push(object)
      }
			res.send(array)
		}
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

router.post('/search', function(req, res, next){
  db_register.get_register_search(function(err, result){
    if(err) console.log(err)
    else res.send(result)
  })
})


module.exports = router;
