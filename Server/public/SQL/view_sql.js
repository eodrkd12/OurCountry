var pool=require('../../config/db_config');

module.exports=function(){
    return {
        insert_view : function(userId, registerId, viewDate, registerTitle, callback){
            pool.getConnection(function(err, con){
                var sql=`insert into view values('${userId}', ${registerId}, '${viewDate}', '${registerTitle}')`
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
        get_view : function(userId, callback){
            pool.getConnection(function(err, con){
                var sql=`select Register.register_id, Register.user_id, register_title, product_category, product_subcategory, product_type, product_status, product_brand, product_price, seller_store, register_content, trade_option, seller_address, register_date, register_like, register_view, user_nickname from Register, view where Register.register_id=view.register_id and view.user_id='${userId}'`
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
