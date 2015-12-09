if (process.argv.indexOf("--reset") !== -1) {
    var fs = require('fs');
    fs.readdir("./csv", function (err, files) {
        if (err) throw err;
        files.forEach(function (file) {
            fs.unlink("./csv/" + file);
        });
    });
}
var path = require('path');
var pwd = path.sep === '\\' ? __dirname.replace(/\\/g, "/") : __dirname;
var mysql = require("mysql");
var setting = require("./setting.js");
var execute = require('./execute.js');

setting.then(function (map) {
    var con;

    con = mysql.createConnection({
        host: "localhost",
        database: "NEXT-LectureManager",
        user: map["spring.datasource.username"],
        password: map["spring.datasource.password"]
    });
    con.connect(function (err) {
        if (err) {
            console.log(err);
            return;
        }
        con.query('show tables', function (err, rows) {
            if (err) throw err;
            var queries = [];
            rows.forEach(function (row) {
                var key = Object.keys(row)[0];
                queries.push(getExportSql(row[key]));
            });
            execute(queries);
        });
    });

});


function getExportSql(tableName) {
    return "SELECT * FROM " + tableName + " INTO OUTFILE '" + pwd + '/csv/' + tableName + ".csv' CHARACTER SET utf8 FIELDS TERMINATED BY ','";
}