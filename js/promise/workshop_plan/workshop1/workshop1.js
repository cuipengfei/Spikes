(function () {
  var https = require('https');// this is the https module from node itself, you don't have to worry about it
  var Promise = require("RWPromise");// this is the Promise implementation we'll be using

  //the following function send http request to github and receives the response
  //but right now, it does not do anything with the response
  //you need to fill the gap according to how the mocha tests specify the behavior of this function
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

  //this function is based on the above one
  //it adds additional behavior
  //you need to fill the gap according to how the mocha tests specify the behavior of this function
  var getMyGithubReposRanked = function (defaultRepo) {
    return getMyGithubRepos().then(
      function (repos) {
        return repos.filter(function (repo) {
          return repo.stars > 0;
        }).sort(function (x, y) {
          return y.stars - x.stars;
        });
      }, function (err) {
        return [defaultRepo];
      });
    // return getMyGithubRepos().then(???,???);
  };

  module.exports.getMyGithubRepos = getMyGithubRepos;
  module.exports.getMyGithubReposRanked = getMyGithubReposRanked;
  module.exports.one = 1;
})();