var _ = require("underscore");
var rp = require('request-promise');
var Promise = require("bluebird");

var j = rp.jar();
var cookie = rp.cookie("token=秘密");
j.setCookie(cookie, "http://facehub.net");

var userIds = _.range(500);
var retrieveUserPromises = _.map(userIds, function (id) {
    var url = "http://facehub.net/api/users/" + id;
    return Promise.delay(id * 10).then(function () {//异步请求一下子发太多会挂掉,间隔开10ｍｓ
        return rp({uri: url, method: "GET", jar: j})
            .then(
                function (user) {
                    console.log("ok id: " + id);
                    return JSON.parse(user);
                }, function (f) {
                    console.log("failed id: " + id + " http code: " + f.statusCode);
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
        .pluck("birthday")
        .filter(function (bday) {
            return bday !== undefined;
        })
        .map(f).value();

    var groups = _.chain(bdayAttributes)
        .groupBy(function (x) {
            return x;
        })
        .value();

    console.log(_.chain(_.keys(groups))
        .map(function (key) {
            var groupMembers = groups[key];
            var percentageOfKey = {};
            percentageOfKey[keyName] = key;
            percentageOfKey.percentage = (groupMembers.length / bdayAttributes.length) * 100;
            return percentageOfKey;
        })
        .sortBy("percentage")
        .value().reverse());
}

Promise.all(retrieveUserPromises).then(function (users) {
    var nonUser = _.chain(users).filter(function (u) {
        return u.failedId !== undefined;
    }).value();
    console.log("获取到用户数量" + (500 - nonUser.length));

    percentageBy(users, function (bday) {
        return parseInt(bday.split("-")[0]);
    }, "月份");

    percentageBy(users, function (bday) {
        var month_day = bday.split("-");
        return getZodiacSign(parseInt(month_day[1]), parseInt(month_day[0]));
    }, "星座");

    percentageBy(users, function (bday) {
        return bday;
    }, "同日");
});
