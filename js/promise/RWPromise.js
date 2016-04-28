module.exports.deferred = function () {
    console.log("hello, deferred is running");
};

module.exports.resolved = function (value) {
    console.log("hello, resolved is running");
};

module.exports.rejected = function (reason) {
    console.log("hello, rejected is running");
};