(function () {
    var https = require('https');
    var Promise = require("RWPromise");

    var getMyGithubRepos = function () {
        var p = new Promise();
        var responseBody = "";
        https.get({
            host: 'api.github.com',
            path: '/users/cuipengfei/repos',
            headers: {
                'User-Agent': 'Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.13) Gecko/20080311 Firefox/2.0.0.13'
            }
        }, function (res) {
            res.on('data', function (chunk) {
                responseBody += chunk;
            });

            if (res.statusCode === 200) {
                res.on('end', function () {
                    p.resolve(JSON.parse(responseBody).map(function (repo) {
                        return {name: repo.name, stars: repo.stargazers_count};
                    }));
                });
            } else {
                res.on('end', function () {
                    p.reject(responseBody);
                });
            }
        }).on("error", function (err) {
            p.reject(err);
        });
        return p;
    };

    var getMyGithubReposAdvanced = function () {
        return getMyGithubRepos().then(
            function (repos) {
                return repos.filter(function (repo) {
                    return repo.stars > 0;
                }).sort(function (x, y) {
                    return y.stars - x.stars;
                });
            }, function (err) {
                return [{
                    name: 'We can not get your repos right now, but we are sure you must have a lot of great repos',
                    stars: 100000
                }];
            });
    };

    module.exports.getMyGithubRepos = getMyGithubRepos;
    module.exports.getMyGithubReposAdvanced = getMyGithubReposAdvanced;
    module.exports.one = 1;
})();