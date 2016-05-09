var workshop1 = require("../workshop1");

var chai = require('chai');
var assert = chai.assert;
var expect = chai.expect;

var chaiAsPromised = require("chai-as-promised");
chai.use(chaiAsPromised);

describe('sanity check', function () {
    it('if you can see this test pass, it means your env is set up properly', function () {
        assert.equal(workshop1.one, 1);
        assert.notEqual(workshop1.one, 2);
    });
});

describe('get git hub repo', function () {
    it('gets an array of github repo names under my account', function () {
        return expect(workshop1.getMyGithubRepos()).to.eventually.have.length(23);//I have 23 repos
    });
});