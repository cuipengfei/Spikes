var RWPromise = require("./RWPromise");

module.exports.deferred = function () {
    var promise = new RWPromise();
    return {
        promise: promise,
        resolve: promise.resolve,
        reject: promise.reject
    }
};