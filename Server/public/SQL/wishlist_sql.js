var pool=require('../../config/db_config');

module.exports=function(){
    return {
        insert_wishlist : function(registerId, userId, callback){
            pool.getConnection(function(err, con){
                var sql=`insert into Wishlist values(${registerId}, '${userId}')`
                con.query(sql, function(err, result, fields){
                    con.release()
                    if(err) console.log(err)
                    else console.log("위시리스트 등록 완료")
                })
            })
        },
        delete_wishlist : function(registerId, userId, callback){
            pool.getConnection(function(err, con){
                var sql=`delete from Wishlist where register_id=${registerId} and user_id='${userId}'`
                con.query(sql, function(err, result, fields){
                    con.release()
                    if(err) console.log(err)
                    else console.log("위시리스트 삭제 완료")
                })
            })
        },
        check_wishlist : function(registerId, userId, callback){
            pool.getConnection(function(err, con){
                var sql=`select * from Wishlist where register_id=${registerId} and user_id='${userId}'`
                con.query(sql, function(err, result, fields){
                    con.release()
                    if(err) console.log(err)
                    else callback(null, result)
                })
            })
        },
        pool: pool
    }
};
