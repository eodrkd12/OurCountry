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
        get_room_info: function(maker,partner,roomTitle,callback){
            pool.getConnection(function(err,con){
                if(err) console.log(err)
                else{
                    var sql=`select * from ChatRoom where maker='${maker}' and partner='${partner}' and room_title='${roomTitle}'`
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
        check_chat_room: function(maker,partner,registerTitle,callback){
            pool.getConnection(function(err,con){
                if(err) console.log(err)
                else{
                    var sql=`select * from ChatRoom where maker='${maker}' and partner='${partner}' and room_title='${registerTitle}'`
                    con.query(sql,function(err,result,field){
                        if(err) callback(err)
                        else callback(null,result)
                    })
                }   
            })
        },
        delete_user_maker:function(id){
            pool.getConnection(function(err,con){
                if(err) console.log(err)
                else{
                    var sql="update ChatRoom set maker='탈퇴한 유저' where maker='"+id+"'"
                    con.query(sql,function(err,result,field){
                        con.release()
                        if(err) console.log(err)
                        else console.log("maker -> 탈퇴한 유저 변경 완료")
                    })
                }
            })
        },
        delete_user_partner:function(id){
            pool.getConnection(function(err,con){
                if(err) console.log(err)
                else{
                    var sql="update ChatRoom set partner='탈퇴한 유저' where partner='"+id+"'"
                    con.query(sql,function(err,result,field){
                        con.release()
                        if(err) console.log(err)
                        else console.log("partner -> 탈퇴한 유저 변경 완료")
                    })
                }
            })
        },
        pool:pool
    }
}
