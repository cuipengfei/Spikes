var express = require('express');
var app = express();
var pg = require('pg');

var config = {
    user: 'postgres', //env var: PGUSER
    database: 'open_lmis', //env var: PGDATABASE
    password: 'p@ssw0rd', //env var: PGPASSWORD
    host: 'db', // Server hosting the postgres database
    port: 5432, //env var: PGPORT
    max: 10, // max number of clients in the pool
    idleTimeoutMillis: 30000, // how long a client is allowed to remain idle before being closed
};

var pool = new pg.Pool(config);

pool.on('error', function(err, client) {
    console.error('idle client error', err.message, err.stack)
})

app.get('/', function(req, res) {
    pool.connect(function(err, client, done) {
        if (err) {
            return console.error('error fetching client from pool', err);
        }
        client.query('CREATE TABLE IF NOT EXISTS hello_table(a INT, b VARCHAR(123))');
        client.query("INSERT  INTO hello_table VALUES (1,'a')");
        client.query('select * from hello_table', function(err, result) {
            //call `done()` to release the client back to the pool
            done();
            res.send(result.rows);

            if (err) {
                return console.error('error running query', err);
            }
            //output: 1
        });
    });
});

app.listen(8888, function() {
    console.log('Example app listening on port 8888, this file has been changed!');
});
