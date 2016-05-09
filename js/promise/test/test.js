var adapter = require('../adapter');

describe("Promises/A+ Tests", function () {
    require("promises-aplus-tests").mocha(adapter);
});