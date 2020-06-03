var pool = require('../../config/db_config');

module.exports = function () {
  return {
    get_user: function(callback){
      pool.getConnection(function(err,con){
        var sql=`select * from user`
        con.query(sql,function(err,result,fields){
          con.release()
          if (err) callback(err)
          else callback(null,result)
        })
      })
    },
    search_user:function(search,callback){
      pool.getConnection(function(err,con){
        var sql=`select * from user where user_id like '%${search}%'`
        con.query(sql,function(err,result,fields){
          con.release()
          if(err) callback(err)
          else callback(null,result)
        })
      })
    },
    check_email: function (email, callback) {
      pool.getConnection(function (err, con) {
        var sql = `select * from user where user_id='${email}'`
        con.query(sql, function (err, result,fields) {
          con.release()
          if (err) console.log(err);
          else callback(null, result);
        })
      })
    },
    login: function (id, callback) {
      pool.getConnection(function (err, con) {
        var sql = `select * from user where user_id='${id}'`
        con.query(sql, function (err, result, fields) {
          con.release();
          if (err) callback(err);
          else callback(null, result);
        })
      })
    },
    join: function (id, password, loginType, nickname, userType, image, phone, userBank, userAccount, joinDate, callback) {
      pool.getConnection(function (err, con) {
        var sql = `insert into user values('${id}','${password}','${loginType}','${nickname}','${userType}','${phone}',null,'${image}',0,0,'${joinDate}','${userAccount}',null,'${userBank}',0,0)`
        con.query(sql, function (err, result) {
          con.release()
          if (err) callback(err)
          else callback(null, result)
        })
      })
    },
    edit_user: function (id, nickname, phone, about, address, callback) {
      pool.getConnection(function (err, con) {
        var sql = `UPDATE user set user_nickname='${nickname}', user_phone='${phone}', user_address='${address}', user_about='${about}' where user_id='${id}'`
        con.query(sql, function (err, result, fields) {
          con.release();
          if (err) callback(err);
          else callback(null, result);
        })
      })
    },
    get_my_info: function (id, callback) {
      pool.getConnection(function (err, con) {
        var sql = `select * from user where user_id='${id}'`
        con.query(sql, function (err, result, fields) {
          con.release()
          if (err) callback(err)
          else callback(null, result)
        })
      })
    },
    remove_token: function (nickname) {
      pool.getConnection(function (err, con) {
        var sql = "update user set token='' where user_nickname='" + nickname + "'"
        con.query(sql, function (err, result, fields) {
          con.release()
          if (err) console.log(err)
        })
      })
    },
    insert_token: function (nickname, token) {
      pool.getConnection(function (err, con) {
        var sql = "update user set token='" + token + "' where user_nickname='" + nickname + "'"
        con.query(sql, function (err, result, fields) {
          con.release()
          if (err) console.log(err)
        })
      })
    },
    get_token:function(nickname,callback){
      pool.getConnection(function(err,con){
              var sql=`select token from user where user_nickname='${nickname}'`
              con.query(sql,function(err,result,fields){
                      con.release()
                      if(err) console.log(err)
                      else callback(null,result)
              })
      })
    },
    pool: pool
  }
}
