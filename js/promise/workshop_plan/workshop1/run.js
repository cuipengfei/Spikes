var workshop1 = require("./workshop1");

workshop1.getMyGithubRepos();//this is how we call getMyGithubRepos now

//the following if how we want to call it after it is promisified
// workshop1.getMyGithubRepos()
//     .then(function (repos) {
//         console.log("I have " + repos.length + " repos");
//         console.log("They are: \n ");
//         repos.forEach(function (repo) {
//             console.log(repo);
//         });
//     });