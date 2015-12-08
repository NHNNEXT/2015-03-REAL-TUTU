var fs = require('fs');

var setting = new Promise(function(resolve){
    var map = {};
    fs.readFile("./../src/main/resources/application.properties", "utf8", function (err, data) {
        data.split("\n").forEach(function (line) {
            if (line.match("=")) {
                var split = line.split("=");
                map[split[0]] = split[1];
            }
        });
        resolve(map);
    });
});

module.exports = setting;