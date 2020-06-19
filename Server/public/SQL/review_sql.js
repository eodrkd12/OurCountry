var pool = require('../../config/db_config');


module.exports = function () {
    return {
        update_review: function (user_id,register_id,review_content,date, callback) {
            pool.getConnection(function (err, con) {
              var sql = `UPDATE Review set user_id='${user_id}', register_id='${register_id}', review_content='${review_content}',review_date='${date}' where user_id='${id}'`
              con.query(sql, function (err, result, fields) {
                con.release();
                if (err) callback(err);
                else callback(null, result);
              })
            })
          },
          pool: pool
    }
}