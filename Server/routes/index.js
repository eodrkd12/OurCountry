var express = require('express');
var router = express.Router();
var db_test=require('../public/SQL/test')()


/* GET home page. */
router.get('/', function(req, res, next) {
  console.log("adfasdfsadfasdf")
  db_test.get_chat(function(err,result){
    if(err) console.log(err)
    else res.send(result)
  })
});

module.exports = router;
