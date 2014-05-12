var _ = require('underscore');
var express = require('express');
var http = require('http');
var uuid = require('node-uuid');
var app = express();
var conf=require('./server.json');
var httpProxy = require('http-proxy');

app.use(express.static(__dirname));

var proxy = httpProxy.createProxyServer({ web: true });



app.get('/api/*', function(req, res) {
    console.log("proxying GET request", req.url);
    proxy.web(req, res, { target: 'http://localhost:8080'});
});
app.post('/api/*', function(req, res) {
    console.log("proxying POST request", req.url);
    proxy.web(req, res, { target: 'http://localhost:8080'});
});

app.options('/api/*', function(req, res) {
    console.log("proxying OPTION request", req.url);
    proxy.web(req, res, { target: 'http://localhost:8080'});
});

app.put('/api/*', function(req, res) {
    console.log("proxying PUT request", req.url);
    proxy.web(req, res, { target: 'http://localhost:8080'});
});

app.delete('/api/*', function(req, res) {
    console.log("proxying DELETE request", req.url);
    proxy.web(req, res, { target: 'http://localhost:8080'});
});

var server = http.createServer(app);


server.listen(conf.port);
