function RWPromise() {
    var self = this;
    self.isFrozen = false;
    self.isInitiated = false;

    self.then = function (onFulfilled, onRejected) {
        self.onFulfilled = onFulfilled;
        self.onRejected = onRejected;
        self.isInitiated = true;
    };

    self.resolve = function (value) {
        if (self.isInitiated && !self.isFrozen) {
            self.onFulfilled(value);
            self.isFrozen = true;
        }
    };

    self.reject = function (reason) {
        if (self.isInitiated && !self.isFrozen) {
            self.onRejected(reason);
            self.isFrozen = true;
        }
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