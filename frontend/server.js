const path = require('path');
const express = require('express');
const app = express();
const PORT = process.env.PORT || 8080;

app.use(express.static(__dirname));

app.get('/', function(request, response) {
    response.sendFile(__dirname + '/index.html');
});

app.listen(PORT, error => {
    error
        ? console.error(error)
        : console.info(`==> Listening on port ${PORT}. Visit http://localhost:${PORT}/ in your browser.`)
});