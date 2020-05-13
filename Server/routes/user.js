var express = require('express');
var router = express.Router();

var db_user=require('../public/SQL/user_sql')()

/* GET users listing. */
router.post('/check/email', function(req, res, next) {
    var email=req.body.email

    db_user.check_email(email,function(err,result){
        if(err) console.log(err)
        else res.send(result)
    })
});

module.exports = router;
