if (process.argv.indexOf("--reset") !== -1) {
    var fs = require('fs');
    fs.readdir("./csv", function (err, files) {
        if (err) throw err;
        files.forEach(function (file) {
            fs.unlink("./csv/" + file);
        });
    });
}

var mysql = require("mysql");
var setting = require("./setting.js");

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

            var end = rows.length;

            rows.forEach(function (row) {
                var key = Object.keys(row)[0];
                con.query(getExportSql(row[key]), function (err) {
                    end--;
                    if (end === 0)
                        ending();
                    if (err)
                        console.log(err);
                    else
                        console.log(row[key] + " table exported as csv");
                });
            });
        });
    });

    function ending() {
        con.end();
        process.exit();
    }

});


function getExportSql(tableName) {
    return "SELECT * FROM " +
        tableName +
        " INTO OUTFILE '" +
        __dirname + '/csv/' + tableName +
        ".csv' CHARACTER SET utf8 FIELDS TERMINATED BY ','";
}