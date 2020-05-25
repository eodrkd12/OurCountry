var pool=require('../../config/db_config');

module.exports=function(){
    return {
        create_room: function(maker,partner,roomDate,callback){
            pool.getConnection(function(err,con){
                if(err) console.log(err)
                else{
                    var query=`insert into ChatRoom(maker,partner,room_date) values('${maker}','${partner}','${roomDate}')`
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
                    var query=`select * from ChatRoom where maker='${maker}' and partner='${partner}'`
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
