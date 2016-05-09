(function () {
    var https = require('https');
    var Promise = require("bluebird");

    var getMyGithubRepos = function () {
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

                });
            } else {
                res.on('end', function () {

                });
            }
        });
    };

    var getMyGithubReposAdvanced = function () {
        // return getMyGithubRepos().then(???,???);
    };

    module.exports.getMyGithubRepos = getMyGithubRepos;
    module.exports.getMyGithubReposAdvanced = getMyGithubReposAdvanced;
    module.exports.one = 1;
})();