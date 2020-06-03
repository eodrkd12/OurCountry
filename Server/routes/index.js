var express = require('express');
var router = express.Router();
var db_test=require('../public/SQL/test')()


/* GET home page. */
router.get('/', function(req, res, next) {
	res.render("login",{})
});

router.get('/main', function(req,res,next){
	res.render("sidebar_menu",{})
});

router.get('/sidebar', function(req,res,next){
	res.render("sidebar_menu",{})
})

router.get('/member',function(req,res,next){
	res.render("member/member",{})
})

module.exports = router;
