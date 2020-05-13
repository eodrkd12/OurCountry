var pool=require('../../config/db_config');

module.exports=function(){
    return {
        check_email:function(email,callback){
            pool.getConnection(function(err,con){
                var sql=`select * from user where user_id='${email}'`
                con.query(sql,function(err,result){
                    if(err) console.log(err);
                    else callback(null,result);
                })
            })
        },  
        pool:pool
    }
}