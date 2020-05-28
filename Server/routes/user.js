var express = require('express');
var router = express.Router();

var db_user=require('../public/SQL/user_sql')()

/* GET users listing. */
router.post('/check/email', function(req, res, next) {
    var email=req.body.email

    db_user.check_email(email,function(err,result){
        if(err) console.log(err)
        else res.send(result[0])
    })
});

router.get('/', function(req, res, next) {
  db_user.get_user(function(err,result){
    if(err) console.log(err);
    else res.send(result);
  })
});

router.post('/join',function(req,res,next){
  var id=req.body.id
  var password=req.body.password
  var nickname=req.body.nickname
  var userType=req.body.user_type
  var userBank='asd'
  var userAccount=req.body.user_account
  var joinDate=req.body.user_join_date
  var image=req.body.image
  var phone=req.body.phone
  var loginType=req.body.login_type
  db_user.join(id,password,loginType,nickname,userType,image,phone,userBank,userAccount,joinDate,function(err,result){
    if(err) console.log(err)
    else{
      var result=new Object()
      result.result="success"
      console.log("회원가입")
      res.send(result)
    }
  })
})

router.post('/login', function(req,res,next){
  db_user.login(req.body.id,function(err,result){
    if(err) console.log(err);
    else {
      var data=result[0]
      res.send(data);
    }
  })
});

router.post('/update/editUserReq', function(req, res, next) {
	var id=req.body.id
	var nickname=req.body.nickname
	var phone = req.body.phone
	var about = req.body.about 
	var address= req.body.address

	db_user.edit_user(id, nickname,phone,about,address,function(err,result){
		if(err) console.log(err)
		else{
			var object=new Object()
			object.result="success"
			res.send(result)
		}
	})
})

router.post('/my',function(req,res,next){
  var id=req.body.id

  db_user.get_my_info(id,function(err,result){
    if(err) console.log
    else res.send(result[0])
  })
})

router.post('/remove/token',function(req,res,next){
  var nickname=req.body.nickname

  db_user.remove_token(nickname)

  var object=new Object()
  object.result="success"
  res.send(object)
})

router.post('/insert/token',function(req,res,next){
  var nickname=req.body.nickname
  var token=req.body.token
  db_user.insert_token(nickname,token)

  var object=new Object()
  object.result="success"
  res.send(object)
})

module.exports = router;
