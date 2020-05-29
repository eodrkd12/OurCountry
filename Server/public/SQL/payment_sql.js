var pool=require('../../config/db_config');

module.exports=function(){
    return {
        insert_payment : function(orderId, registerId, userId, registerPrice, paymentDate, type, registerTitle){
            pool.getConnection(function(err, con){
                var sql=`insert into payment values('${orderId}',${registerId},'${userId}','${registerPrice}','${paymentDate}','입금','${type}','${registerTitle}})`
                con.query(sql, function(err, result, fields){
                    con.release()
                    if(err) console.log(err)
                    else console.log("주문 내역 압력 완료")
                })
            })
        },
        pool : pool
    }
}