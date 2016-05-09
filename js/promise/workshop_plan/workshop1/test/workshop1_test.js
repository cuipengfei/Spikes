var workshop1 = require("../workshop1");

var chai = require('chai');
var assert = chai.assert;
var expect = chai.expect;

var chaiAsPromised = require("chai-as-promised");
chai.use(chaiAsPromised);

var nock = require('nock');

describe('sanity check', function () {
    it('if you can see this test pass, it means your env is set up properly', function () {
        assert.equal(workshop1.one, 1);
        assert.notEqual(workshop1.one, 2);
    });
});

describe('get git hub repos', function () {
    it('should pass an array of 23 repo names to onFullfilled', function () {
        //given: mock http response
        nock('https://api.github.com')
            .get('/users/cuipengfei/repos')
            .reply(200, [{name: 'a'}, {name: 'b'}]);

        //when
        var reposPromise = workshop1.getMyGithubRepos();

        //then
        return expect(reposPromise).to.eventually.deep.equal(['a', 'b']);
    });
});