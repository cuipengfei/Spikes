function RWPromise() {
    var self = this;
    self.isInitiated = false;

    self.then = function (onFulfilled, onRejected) {
        self.onFulfilled = onFulfilled;
        self.onRejected = onRejected;
        if (self.isResolved) {
            callIfIsFunction(onFulfilled, self.resolvedValue);
        }
        if (self.isRejected) {
            callIfIsFunction(onRejected, self.rejectedReason);
        }
        return self;
    };

    self.resolve = function (value) {
        self.isResolved = true;
        self.resolvedValue = value;
        setTimeout(function () {
            if (!isFrozen(self)) {
                callIfIsFunction(self.onFulfilled, value);
            }
        });
        return self;
    };

    self.reject = function (reason) {
        self.isRejected = true;
        self.rejectedReason = reason;
        setTimeout(function () {
            if (!isFrozen(self)) {
                callIfIsFunction(self.onRejected, reason);
            }
        });
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