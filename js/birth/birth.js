var _ = require("underscore");
var rp = require('request-promise');
var Promise = require("bluebird");

var j = rp.jar();
var cookie = rp.cookie("token=这个可不能乱写");
j.setCookie(cookie, "http://facehub.net");

var userIds = _.range(500);
var getUserPromises = _.map(userIds, function (id) {
    var url = "http://facehub.net/api/users/" + id;
    return Promise.delay(id * 7).then(function () {
        return rp({uri: url, method: "GET", jar: j}).then(function (user) {
            console.log("ok " + id);
            return JSON.parse(user);
        }, function (f) {
            console.log("bad " + id);
            return {id: id, code: f.statusCode};
        });
    });
});

Promise.all(getUserPromises).then(function (users) {
    var nonUser = _.chain(users).filter(function (u) {
        return u.code !== undefined;
    }).value();
    console.log("获取到用户数量" + (500 - nonUser.length));

    var months = _.chain(users)
        .pluck("birthday")
        .filter(function (bday) {
            return bday !== undefined;
        })
        .map(function (bday) {
            return parseInt(bday.split("-")[0]);
        }).value();
    var groups = _.chain(months)
        .groupBy(function (month) {
            return month;
        })
        .value();
    console.log(_.chain(_.keys(groups))
        .map(function (key) {
            var n = groups[key];
            return {month: key, percent: (n.length / months.length) * 100};
        })
        .sortBy("percent")
        .value().reverse());
}, function (failures) {
    console.log(failures);
});
