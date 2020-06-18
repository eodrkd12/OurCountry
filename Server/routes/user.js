var express = require('express');
var router = express.Router();

var db_user = require('../public/SQL/user_sql')()
var db_room = require('../public/SQL/chat_room_sql')()
var db_register = require('../public/SQL/register_sql')()
var db_follow = require('../public/SQL/follow_sql')()
var db_wishlist = require('../public/SQL/wishlist_sql')()
var db_view=require('../public/SQL/view_sql')()


/* GET users listing. */
router.post('/check/email', function (req, res, next) {
  var email = req.body.email

  db_user.check_email(email, function (err, result) {
    if (err) console.log(err)
    else res.send(result[0])
  })
});

router.get('/', function (req, res, next) {
  db_user.get_user(function (err, result) {
    if (err) console.log(err);
    else res.send(result);
  })
});

router.post('/search', function (req, res, next) {

  var search = req.body.search

  console.log(req.body)

  db_user.search_user(search, function (err, result) {
    if (err) console.log(err)
    else res.send(result)
  })
})

router.post('/join', function (req, res, next) {
  var id = req.body.id
  var password = req.body.password
  var nickname = req.body.nickname
  var userType = req.body.user_type
  var userBank = req.body.user_bank
  var userAccount = req.body.user_account
  var joinDate = req.body.user_join_date
  var image = req.body.image
  var phone = req.body.phone
  var loginType = req.body.login_type
  db_user.join(id, password, loginType, nickname, userType, image, phone, userBank, userAccount, joinDate, function (err, result) {
    if (err) console.log(err)
    else {
      var result = new Object()
      result.result = "success"
      console.log("회원가입")
      res.send(result)
    }
  })
})

router.post('/login', function (req, res, next) {
  db_user.login(req.body.id, function (err, result) {
    if (err) console.log(err);
    else {
      var data = result[0]
      res.send(data);
    }
  })
});

router.post('/web/login', function (req, res, next) {
  db_user.login(req.body.id, function (err, result) {
    if (err) console.log(err)
    else {
      if (result[0].user_pw == req.body.pw) {
        res.cookie("user", req.body.id, {
          expires: new Date(Date.now() + 900000),
          httpOnly: true
        })
        var obj = new Object()
        obj.result = "success"
        res.send(obj)
      }
      else {
        obj.result = "fail"
        res.send(obj)
      }
    }
  })
})

router.post('/update/editUserReq', function (req, res, next) {
  var id = req.body.id
  var nickname = req.body.nickname
  var phone = req.body.phone
  var about = req.body.about
  var address = req.body.address
  var userType = req.body.user_type
  var bank = req.body.bank
  var account= req.body.account

  db_user.edit_user(id, nickname, phone, about, address, userType,bank,account, function (err, result) {
    if (err) console.log(err)
    else {
      var object = new Object()
      object.result = "success"
      res.send(result)
    }
  })
})

router.post('/update/password', function (req, res, next) {
  var id = req.body.id
  var password = req.body.password

  db_user.edit_password(id, password, function (err, result) {
    if (err) console.log(err)
    else {
      var object = new Object()
      object.result = "success"
      res.send(result)
    }
  })
})

router.post('/my', function (req, res, next) {
  var id = req.body.id

  db_user.get_my_info(id, function (err, result) {
    if (err) console.log
    else res.send(result[0])
  })
})

router.post('/remove/token', function (req, res, next) {
  var id = req.body.id

  db_user.remove_token(id)

  var object = new Object()
  object.result = "success"
  res.send(object)
})

router.post('/insert/token', function (req, res, next) {
  var id = req.body.id
  var token = req.body.token
  db_user.insert_token(id, token)

  var object = new Object()
  object.result = "success"
  res.send(object)
})
router.post('/my/product', function (req, res, next) {
  var userId = req.body[0].user_id
  db_user.get_my_product(userId, function (err, result) {
    if (err) console.log(err)
    else res.send(result)
  })
})
router.post('/my/update/image', function (req, res, next) {
  var userId = req.body[0].id
  var bitmap=req.body[0].bitmap
  db_user.update_image(userId,bitmap, function (err, result) {
    if (err) console.log(err)
    else res.send(result)
  })
})
router.post('/get/point',function(req,res,next){
  var id=req.body.id
  
  db_user.get_point(id,function(err,result){
    if(err) console.log(err)
    else res.send(result)
  })
})
router.post('/delete',function(req,res,next){
  var id=req.body.id

  db_user.delete_user(id)
  db_register.delete_user_register(id)
  db_room.delete_user_maker(id)
  db_room.delete_user_partner(id)
  db_follow.delete_user_follow(id)
  db_view.delete_user_view(id)
  db_wishlist.delete_user_wishlist(id)

  var obj=new Object()
  obj.result="success"
  res.send(obj)
})

module.exports = router;
