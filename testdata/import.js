var path = require('path');
var pwd = path.sep === '\\' ? __dirname.replace(/\\/g, "/") : __dirname;
var make = process.argv.indexOf("--make") !== -1;
var fs = require('fs');
var execute = require('./execute.js');
var eol = require('os').EOL;

var queries = ["SET foreign_key_checks = 0;"];

fs.readdir("./csv", function (err, files) {
    if (err) throw err;
    files.forEach(function (file) {
        queries.push(getImportSql(file));
    });
    queries.push("SET foreign_key_checks = 1;");
    if (make) {
        var file = "./../src/main/resources/import.sql";
        fs.writeFile(file, queries.join(eol), function (err) {
            if (err)
                console.log(err);
            else
                console.log(file + " saved");
        });
        return;
    }
    execute(queries);
});

function getImportSql(fileName) {
    return "LOAD DATA LOCAL INFILE \"" + pwd + "/csv/" + fileName + "\" INTO TABLE " + fileName.substr(0, fileName.length - 4) + " FIELDS TERMINATED BY \",\";";
}