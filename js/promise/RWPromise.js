function RWPromise() {
    var self = this;
    self.isInitiated = false;

    self.then = function (onFulfilled, onRejected) {
        self.onFulfilled = onFulfilled;
        self.onRejected = onRejected;
        if (self.isRejected) {
            callIfIsFunction(onRejected);
        }
        if (self.isResolved) {
            callIfIsFunction(onFulfilled);
        }
        return self;
    };

    self.resolve = function (value) {
        if (!isFrozen(self)) {
            callIfIsFunction(self.onFulfilled, value);
            self.isResolved = true;
        }
        return self;
    };

    self.reject = function (reason) {
        if (!isFrozen(self)) {
            callIfIsFunction(self.onRejected, reason);
            self.isRejected = true;
        }
        return self;
    };

    function callIfIsFunction(func, para) {
        var isFunc = typeof func === "function";
        if (isFunc) {
            func(para);
        }
    }

    function isFrozen(p) {
        return p.isRejected || p.isResolved;
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