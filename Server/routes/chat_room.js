var express=require('express');
var router=express.Router();

var db_chat_room=require('../public/SQL/chat_room_sql')();


var admin = require("firebase-admin");

var serviceAccount = require("../ourcountry-10694-firebase-adminsdk-fkur0-43c13f06a2.json");

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://ourcountry-10694.firebaseio.com"
});


router.post('/fcm/send',function(req,res,next){

	var topic = req.body.topic
	var content = req.body.content
	var title=req.body.title


	var message={
		notification : {
			body : content,
			title : title
		},
		topic : topic

	}

	admin.messaging().send(message)
		.then((response) => {
			//Response is a message ID string
			console.log('Successfully sent message:', response)
			var object=new Object()
			object.result=response
			res.send(object)
		})
		.catch((error) => {
			console.log('Error sending message:', error)
			var object=new Object()
			object.result=error
			res.send(object)
		})
})

module.exports = router;
