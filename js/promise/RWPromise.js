function RWPromise() {
    var self = this;
    self.isInitiated = false;

    self.then = function (onFulfilled, onRejected) {
        self.onFulfilled = onFulfilled;
        self.onRejected = onRejected;
        self.isInitiated = true;
        if (self.isRejected) {
            onRejected();
        }
        return self;
    };

    self.resolve = function (value) {
        var isOnFulfilledFunction = typeof self.onFulfilled === "function";
        if (isOnFulfilledFunction && !isFrozen(self)) {
            self.onFulfilled(value);
        }
        self.isResolved = true;
        return self;
    };

    self.reject = function (reason) {
        var isOnRejectedFunction = typeof self.onFulfilled === "function";
        if (isOnRejectedFunction && !isFrozen(self)) {
            self.onRejected(reason);
        }
        self.isRejected = true;
        return self;
    };

    function isFrozen(p) {
        return p.isInitiated && (p.isRejected || p.isResolved);
    }
}

module.exports.deferred = function () {
    var rwPromise = new RWPromise();
    return {
        promise: rwPromise,
        resolve: rwPromise.resolve,
        reject: rwPromise.reject
    };
};