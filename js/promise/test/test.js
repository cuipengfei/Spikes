var adapter = require('../Promise3');

describe("Promises/A+ Tests", function () {
    require("promises-aplus-tests").mocha(adapter);
});