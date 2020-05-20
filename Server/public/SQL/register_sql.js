var pool=require('../../config/db_config');

module.exports=function(){
    return {
        register_product : function(userId, registerTitle, productCategory, productSubCategory, productType, productStatus, productBrand, productPrice, sellerStore, registerContent, tradeOption, sellerAddress, registerDate, registerLike, registerView){
            pool.getConnection(function(err, con){
                var sql=`insert into Register(user_id,register_title,product_category,product_subcategory,product_type,product_status,product_brand,product_price,seller_store,register_content,trade_option,seller_address,register_date,register_like,register_view) values('${userId}', '${registerTitle}', '${productCategory}', '${productSubCategory}', '${productType}', '${productStatus}', '${productBrand}', '${productPrice}', ${sellerStore}, '${registerContent}', '${tradeOption}', '${sellerAddress}', '${registerDate}', 0, 0)`
                con.query(sql, function(err, result, fields){
                    con.release()
                    if(err) console.log(err)
                    else console.log("상품 등록 완료")
                })
            })
        },
        get_register : function(userId,registerTitle,registerDate,callback){
            pool.getConnection(function(err,con){
                var sql=`select * from Register where user_id='${userId}' and register_title='${registerTitle}' and register_date='${registerDate}'`
                con.query(sql, function(err,result,fields){
                    con.release()
                    if(err) callback(err)
                    else callback(null,result)
                })
            })
        },
        insert_image : function(registerId,registerTitle,image,callback){
            pool.getConnection(function(err,con){
                var sql=`insert into image(register_id,register_title,product_image) values(${registerId},'${registerTitle}','${image}')`
                con.query(sql, function(err,result,fields){
                    con.release()
                    if(err) callback(err)
                    else callback(null,result)
                })
            })
        },
        get_register_recent:function(callback){
            pool.getConnection(function(err,con){
                var sql=`select * from Register order by register_date desc`
                con.query(sql, function(err,result,fields){
                    con.release()
                    if(err) callback(err)
                    else callback(null,result)
                })
            })
        },
        get_register_popular:function(callback){
            pool.getConnection(function(err,con){
                var sql=`select * from Register order by register_like desc`
                con.query(sql, function(err,result,fields){
                    con.release()
                    if(err) callback(err)
                    else callback(null,result)
                })
            })
        },
        pool: pool
    }
};
