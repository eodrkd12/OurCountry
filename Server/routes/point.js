var express = require('express');
var router = express.Router();

var db_point=require('../public/SQL/point_sql')()


router.get('/', function(req, res, next) {
    db_point.get_point(function(err,result){
      if(err) console.log(err);
      else res.send(result);
    })
  });
  
  module.exports = router;
