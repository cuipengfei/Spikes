var workshop1 = require("./workshop1");

workshop1
    .getMyGithubRepos()
    .then(function (repos) {
        console.log("I have " + repos.length + " repos");
        console.log("They are: \n ");
        repos.forEach(function (repo) {
            console.log(repo);
        });
    }, function (err) {
        console.log(err);
    });

workshop1
    .getMyGithubReposAdvanced()
    .then(function (repos) {
        console.log("I have " + repos.length + " repos that have stars");
        console.log("They are (high to low): \n ");
        repos.forEach(function (repo) {
            console.log(repo);
        });
    });