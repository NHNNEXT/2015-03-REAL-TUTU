var fs = require('fs');
var eol = require('os').EOL;

var setting = new Promise(function(resolve){
    var map = {};
    fs.readFile("./../src/main/resources/application.properties", "utf8", function (err, data) {
        data.split(eol).forEach(function (line) {
            if (line.match("=")) {
                var split = line.split("=");
                map[split[0]] = split[1];
            }
        });
        var url = map["spring.datasource.url"];
        var regex = /^jdbc:mysql:\/\/(\S+)\:(\w+)\/(\S+)$/i
        var split  = regex.exec(url);
        map.host = split[1];
        map.port = split[2];
        map.database = split[3];
        resolve(map);
    });
});

module.exports = setting;