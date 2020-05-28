var pool=require('../../config/db_config');

module.exports=function(){
    return {
        create_room: function(maker,partner,roomDate,roomTitle,callback){
            pool.getConnection(function(err,con){
                if(err) console.log(err)
                else{
                    var sql=`insert into ChatRoom(maker,partner,room_date,room_title) values('${maker}','${partner}','${roomDate}','${roomTitle}')`
                    con.query(sql,function(err,result,field){
                        con.release()
                        if(err) callback(err)
                        else callback(null,result)
                    })
                }
            })
        },
        get_room_info: function(maker,partner,roomDate,callback){
            pool.getConnection(function(err,con){
                if(err) console.log(err)
                else{
                    var sql=`select * from ChatRoom where maker='${maker}' and partner='${partner}' and room_date='${roomDate}'`
                    con.query(sql,function(err,result,field){
                        con.release()
                        if(err) callback(err)
                        else callback(null,result)
                    })
                }
            })
        },
        get_my_room: function(id,callback){
            pool.getConnection(function(err,con){
                if(err) console.log(err)
                else{
                    var sql=`select * from ChatRoom where maker='${id}' or partner='${id}'`
                    con.query(sql,function(err,result,field){
                        con.release()
                        if(err) callback(err)
                        else callback(null,result)
                    })
                }
            })
        },
        pool:pool
    }
}
