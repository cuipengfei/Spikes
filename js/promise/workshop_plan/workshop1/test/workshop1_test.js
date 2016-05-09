var assert = require('chai').assert;
var workshop1 = require("../workshop1");

describe('sanity check', function () {
    it('if you can see this test pass, it means your env is set up properly', function () {
        assert.equal(workshop1.one, 1);
        assert.notEqual(workshop1.one, 2);
    });
});