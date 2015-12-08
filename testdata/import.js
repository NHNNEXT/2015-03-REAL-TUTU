var tables = [
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
    "user",
    "user_group",
    "user_enrolled_lecture",
    "user_group_can_read_content",
    "user_group_can_read_todo",
    "user_group_can_write_content",
    "user_likes_content",
    "user_likes_lecture",
    "user_likes_reply"
];

var mysql = require("mysql");
var con = mysql.createConnection({
    host: "localhost",
    user: "root",
    database: "NEXT-LectureManager",
    password: ""
});

function getImportSql(tableName) {
    return "LOAD DATA LOCAL INFILE \"" +
        __dirname + "/csv/" + tableName + ".csv" +
        "\" INTO TABLE " +
        tableName +
        " FIELDS TERMINATED BY \",\";";
}

doImport();

function doImport() {
    var tableName = tables.splice(0, 1);
    con.query(getImportSql(tableName), function (err) {
        if (err) throw err;
        console.log(tableName + " Import Success");
        if (tables.length === 0){
            con.end();
            process.exit();
            return;
        }
        doImport();
    });
}