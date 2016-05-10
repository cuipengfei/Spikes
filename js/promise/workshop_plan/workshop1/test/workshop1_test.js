var chai = require('chai');
var nock = require('nock');
var workshop1 = require("../workshop1");
var chaiAsPromised = require("chai-as-promised");

var expect = chai.expect;
var assert = chai.assert;
chai.use(chaiAsPromised);

describe('sanity check', function () {
    it('if you can see this test pass, it means your env is set up properly', function () {
        assert.equal(workshop1.one, 1);
        assert.notEqual(workshop1.one, 2);
    });
});

describe('get git hub repos', function () {
    it('should eventually resolve to an array of repo names and stars', function () {
        //given: mock http response to return 200
        nock('https://api.github.com')
            .get('/users/cuipengfei/repos')
            .reply(200, [
                {name: 'a', stargazers_count: 10, IDontCare: 123},
                {name: 'b', stargazers_count: 12, ShouldNotBeInFinalResult: "blah blah"}]);

        //when
        var reposPromise = workshop1.getMyGithubRepos();

        //then
        return expect(reposPromise).to.eventually.deep.equal(
            [
                {name: 'a', stars: 10},
                {name: 'b', stars: 12}
            ]);
    });

    it('should reject with error message', function () {
        //given: mock http response to return 500
        nock('https://api.github.com')
            .get('/users/cuipengfei/repos')
            .reply(500, {error: "github is not working"});

        //when
        var reposPromise = workshop1.getMyGithubRepos();

        //then
        return expect(reposPromise).to.be.rejectedWith({error: "github is not working"});
    });
});

describe('get ranked git hub repos', function () {
    it('should eventually resolve to an array of repos ranked by stars', function () {
        //given: mock http response to return 200
        nock('https://api.github.com')
            .get('/users/cuipengfei/repos')
            .reply(200, [
                {name: 'c', stargazers_count: 0, WhatEver: 456},
                {name: 'a', stargazers_count: 10, IDontCare: 123},
                {name: 'b', stargazers_count: 12, ShouldNotBeInFinalResult: "blah blah"}]);

        //when
        var reposPromise = workshop1.getMyGithubReposRanked();

        //then
        return expect(reposPromise).to.eventually.deep.equal(
            [
                {name: 'b', stars: 12},
                {name: 'a', stars: 10}
            ]);
    });

    it('should resolve with default repo if network fails', function () {
        //given: mock http response to return 500 error
        nock('https://api.github.com')
            .get('/users/cuipengfei/repos')
            .reply(500, {error: "github is not working"});

        //when
        var defaultRepo = {
            name: 'this is a default repo that will be shown when http fails',
            stars: 99999
        };
        var reposPromise = workshop1.getMyGithubReposRanked(defaultRepo);

        //then
        return expect(reposPromise).to.eventually.deep.equal([defaultRepo]);
    });
});