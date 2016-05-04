function RWPromise() {

    var self = this;

    self.resolve = function (value) {
    };

    self.reject = function (reason) {
    };

    self.then = function (onFulfilled, onRejected) {

    };
}

module.exports.deferred = function () {
    var rwPromise = new RWPromise();
    return {
        promise: rwPromise,
        resolve: rwPromise.resolve,
        reject: rwPromise.reject
    };
};