var _ = require("underscore");
var chalk = require("chalk");
var util = require('util');
var requestPromise = require('request-promise');
var Promise = require("bluebird");

var cookie = requestPromise.cookie("token=xxx");
var jar = requestPromise.jar();
jar.setCookie(cookie, "http://facehub.net");

var userAmount = 500;
var userIds = _.range(userAmount);//用0到500当做id去试

var retrieveUserPromises = _.map(userIds, function (id) {
    var url = "http://facehub.net/api/users/" + id;
    return Promise.delay(id * 10).then(function () {//异步请求一下子发太多会fail掉,间隔开10ｍｓ
        return requestPromise({uri: url, method: "GET", jar: jar})
            .then(
                function (user) {
                    console.log(chalk.green.bgBlue.bold("成功获取到了id为 " + id + " 的用户信息"));
                    return JSON.parse(user);
                }, function (f) {
                    console.log(chalk.red.bgWhite.bold("获取不到id为" + id + " 的用户　" + f.statusCode));
                    return {failedId: id, code: f.statusCode};
                });
    });
});

function getZodiacSign(day, month) {
    var zodiacSigns = {
        'capricorn': '摩羯',
        'aquarius': '水瓶',
        'pisces': '双鱼',
        'aries': '白羊',
        'taurus': '金牛',
        'gemini': '双子',
        'cancer': '巨蟹',
        'leo': '狮子',
        'virgo': '处女',
        'libra': '天秤',
        'scorpio': '天蝎',
        'sagittarius': '射手'
    };

    if ((month == 1 && day <= 20) || (month == 12 && day >= 22)) {
        return zodiacSigns.capricorn;
    } else if ((month == 1 && day >= 21) || (month == 2 && day <= 18)) {
        return zodiacSigns.aquarius;
    } else if ((month == 2 && day >= 19) || (month == 3 && day <= 20)) {
        return zodiacSigns.pisces;
    } else if ((month == 3 && day >= 21) || (month == 4 && day <= 20)) {
        return zodiacSigns.aries;
    } else if ((month == 4 && day >= 21) || (month == 5 && day <= 20)) {
        return zodiacSigns.taurus;
    } else if ((month == 5 && day >= 21) || (month == 6 && day <= 21)) {
        return zodiacSigns.gemini;
    } else if ((month == 6 && day >= 22) || (month == 7 && day <= 22)) {
        return zodiacSigns.cancer;
    } else if ((month == 7 && day >= 23) || (month == 8 && day <= 23)) {
        return zodiacSigns.leo;
    } else if ((month == 8 && day >= 24) || (month == 9 && day <= 23)) {
        return zodiacSigns.virgo;
    } else if ((month == 9 && day >= 24) || (month == 10 && day <= 23)) {
        return zodiacSigns.libra;
    } else if ((month == 10 && day >= 24) || (month == 11 && day <= 22)) {
        return zodiacSigns.scorpio;
    } else if ((month == 11 && day >= 23) || (month == 12 && day <= 21)) {
        return zodiacSigns.sagittarius;
    }
}

function percentageBy(users, f, keyName) {
    var bdayAttributes = _.chain(users)
        .map(f).value();

    var groups = _.chain(bdayAttributes)
        .groupBy(function (x) {
            return x;
        })
        .value();

    console.log(chalk.red.bgBlue.bold(util.inspect(_.chain(_.keys(groups))
        .map(function (key) {
            var groupMembers = groups[key];
            var percentageOfKey = {};
            percentageOfKey[keyName] = key;
            percentageOfKey.percentage = (groupMembers.length / bdayAttributes.length) * 100;
            return percentageOfKey;
        })
        .sortBy("percentage")
        .value().reverse())));
}

Promise.all(retrieveUserPromises).then(function (results) {
    var users = _.chain(results).filter(function (u) {
        return u.failedId === undefined;
    }).value();
    console.log("获取到用户数量 " + (users.length));

    percentageBy(users, function (user) {
        return parseInt(user.birthday.split("-")[0]);
    }, "出生月份");

    percentageBy(users, function (user) {
        return parseInt(user.onboard.split("-")[1]);
    }, "入职月份");

    percentageBy(users, function (user) {
        var month_day = user.birthday.split("-");
        return getZodiacSign(parseInt(month_day[1]), parseInt(month_day[0]));
    }, "星座");
});
