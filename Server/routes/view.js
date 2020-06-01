var express=require('express');
var router=express.Router();

var db_view=require('../public/SQL/view_sql')();

router.post('/insert', function(req,res,next){
  var registerId=req.body.register_id
  var userId=req.body.user_id
  var viewDate=req.body.view_date

  db_view.insert_view(userId, registerId, viewDate, function(err,result){
    if(err) console.log(err)
    else {
      var object = new Object()
      object.result="success"
      res.send(object)
    }
  })
})

router.post('/update', function(req,res,next){
  var registerId=req.body.register_id
  var userId=req.body.user_id
  var viewDate=req.body.view_date

  db_view.update_view(userId, registerId, viewDate, function(err, result){
      if(err) console.log(err)
      else{
          var object = new Object()
          object.result="success"
          res.send(object)
      }
  })
})

router.post('/check', function(req, res, next){
    var userId=req.body.user_id
    var registerId=req.body.register_id

    db_view.check_view(userId, registerId, function(err, result){
        if(err) console.log(err)
        else res.send(result)
    })
})

module.exports = router;
