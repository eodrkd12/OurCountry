var pool=require('../../config/db_config');

module.exports = function () {
    return {
        get_point: function ( callback) {
        pool.getConnection(function (err, con) {
          con.release()
          var sql = "select * from ReturnPoint order by status desc"
          con.query(sql, function (err, result) {
            if (err) console.log(err);
            else callback(null, result);
          })
        })
      },    
      search_point:function(search,callback){
        pool.getConnection(function(err,con){
          var sql="select * from ReturnPoint where user_id like '" + search + "' order by status desc"
          con.query(sql,function(err,result,fields){
            con.release()
            if(err) callback(err)
            else callback(null,result)
          })
        })
      },
	insert_point(id,refund_date,point,bank,account,callback){
		pool.getConnection(function(err,con){
			var sql=`insert into ReturnPoint value('${id}','${refund_date}',${point},'${bank}','${account}','처리안됨')`
			con.query(sql,function(err,result,fields){
				con.release()
				if(err) callback(err)
				else callback(null,result)
			})
		})
	},
	confirm_refund:function(id,date,callback){
		pool.getConnection(function(err,con){
			var sql="update ReturnPoint set status='처리됨' where user_id='"+id+"' and return_date='"+date+"'"
			con.query(sql,function(err,result,fields){
				con.release()
				if(err) callback(err)
				else callback(null,result)
			})
		})
	},
      pool: pool
    }
  }
  
