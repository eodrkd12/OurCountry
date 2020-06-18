var pool=require('../../config/db_config');

module.exports = function () {
    return {
        get_usertype: function ( callback) {
        pool.getConnection(function (err, con) {
          var sql = `select * from UserTypeChange `
          con.query(sql, function (err, result) {
	    con.release()
            if (err) console.log(err);
            else callback(null, result);
          })
        })
      },
	search_usertype: function(search,callback){
		pool.getConnection(function(err,con){
			var sql=`select * from UserTypeChange where user_id like '%${search}%'`
			con.query(sql,function(err,result){
				con.release()
				if(err)callback(err)
				else callback(null,result)
			})		
		})
	},
	insert: function(id,curType,changeType,date,callback){
		pool.getConnection(function(err,con){
			var sql=`insert into UserTypeChange value('${id}','${curType}','${changeType}','${date}')`
			con.query(sql,function(err,result){
				con.release()
				if(err)callback(err)
				else callback(null,result)
			})
		})
	},
      pool: pool
    }
  }
  
