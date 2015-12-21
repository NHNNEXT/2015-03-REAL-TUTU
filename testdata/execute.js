var setting = require("./setting.js");
var mysql = require("mysql");

var execute = function (queries) {
    setting.then(function (map) {
        var con;
        con = mysql.createConnection({
            host: map.host,
            database: map.database,
            user: map["spring.datasource.username"],
            password: map["spring.datasource.password"]
        });
        doQuery(queries, con);
    });
};

function doQuery(quries, con) {
    if (quries.length === 0) {
        con.end();
        process.exit();
        return;
    }
    var query = quries.splice(0, 1)[0];
    con.query(query, function (err) {
        if (err)
            console.log(err);
        else
            console.log(query + " success");
        doQuery(quries, con);
    });
}

module.exports = execute;