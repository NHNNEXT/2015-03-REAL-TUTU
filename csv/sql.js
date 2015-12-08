var fs = require('fs');


fs.readFile('./import.sql', 'utf8', function read(err, data) {
    if (err) {
        throw err;
    }

    data = data.replace(/\{\{path\}\}/g, __dirname);

    fs.writeFile("./../src/main/resources/import.sql", data, function(err) {
        if(err) {
            return console.log(err);
        }

        console.log("The sql file was saved!");
    });
});


