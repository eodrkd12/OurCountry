var pool=require('../../config/db_config');

module.exports=function(){
    return {
        insert_view : function(userId, registerId, viewDate, callback){
            pool.getConnection(function(err, con){
                var sql=`insert into view values('${userId}', ${registerId}, '${viewDate}')`
                con.query(sql, function(err, result, fields){
                    con.release()
                    if(err) console.log(err)
                    else console.log("viewdate 등록 완료")
                })
            })
        },
        update_view : function(userId, registerId, viewDate, callback){
            pool.getConnection(function(err, con){
                var sql=`update view set view_date='${viewDate}' where user_id='${userId}' and register_id=${registerId}`
                con.query(sql, function(err, result, fields){
                    con.release()
                    if(err) console.log(err)
                    else console.log("viewdate 업데이트 완료")
                })
            })
        },
        check_view : function(userId, registerId, callback){
            pool.getConnection(function(err, con){
                var sql=`count (*) as count from view where user_id='${userId}' and register_id=${registerId}`
                con.query(sql, function(err, result, fields){
                    con.release()
                    if(err) console.log(err)
                    else console.log("viewdate 체크완료")
                })
            })
        },
        pool: pool
    }
};
