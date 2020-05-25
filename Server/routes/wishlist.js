var express=require('express');
var router=express.Router();

var db_wishlist=require('../public/SQL/wishlist_sql')();

router.post('/insert', function(req,res,next){
  var registerId=req.body.register_id
  var userId=req.body.user_id

  db_wishlist.insert_wishlist(registerId, userId, function(err,result){
    if(err) console.log(err)
    else {
      var object=new Object()
      object.result="success"
      res.send(object)
    }
  })
})

router.post('/delete', function(req,res,next){
  var registerId=req.body.register_id
  var userId=req.body.user_id

  db_register.delete_wishlist(registerId, userId, function(err,result){
    if(err) console.log(err)
    else {
      var object=new Object()
      object.result="success"
      res.send(object)
    }
  })
})

router.post('/check', function(req, res, next){
  var registerId=req.body.register_id
  var userId=req.body.user_id

  db_wishlist.check_wishlist(registerId, userId, function(err, result){
    if(err) console.log(err)
    else {
      var object = new Object()
      object.result="success"
      res.send(object)
      console.log("위시리스트 체크완료")
    }
  })
})

module.exports = router;
