var adapter = require('../RWPromise');

describe("Promises/A+ Tests", function () {
    require("promises-aplus-tests").mocha(adapter);
});