var tables = [
    "user",
    "lecture",
    "content_group",
    "content",
    "message",
    "content_link_content",
    "external",
    "reply",
    "tag",
    "term",
    "uploaded_file",
    "user_have_to_submit",
    "submit",
    "user_group",
    "user_enrolled_lecture",
    "user_group_can_read_content",
    "user_group_can_read_todo",
    "user_group_can_write_content",
    "user_likes_content",
    "user_likes_lecture",
    "user_likes_reply"
];

var setting = require('./setting.js');
var mysql = require("mysql");
var con;

setting.then(function(map){
    con = mysql.createConnection({
        host: "localhost",
        database: "NEXT-LectureManager",
        user: map["spring.datasource.username"],
        password: map["spring.datasource.password"]
    });
    doImport();
});


function doImport() {
    var tableName = tables.splice(0, 1);
    con.query(getImportSql(tableName), function (err) {
        if (err) throw err;
        console.log(tableName + " Import Success");
        if (tables.length === 0) {
            con.end();
            process.exit();
            return;
        }
        doImport();
    });
}

function getImportSql(tableName) {
    return "LOAD DATA LOCAL INFILE \"" +
        __dirname + "/csv/" + tableName + ".csv" +
        "\" INTO TABLE " +
        tableName +
        " FIELDS TERMINATED BY \",\";";
}