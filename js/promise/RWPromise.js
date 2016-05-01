function RWPromise() {
    var self = this;
    self.isPending = true;
    self.callBacks = [];

    self.resolve = function (value) {
        if (!isSettled(self)) {
            kickOffChainReaction(value, self.callBacks.map(function (callBack) {
                return callBack.onFulFilled;
            }));
            self.isResolved = true;
        }
        return self;
    };

    self.reject = function (reason) {
        if (!isSettled(self)) {
            kickOffChainReaction(reason, self.callBacks.map(function (callBack) {
                return callBack.onRejected;
            }));
            self.isRejected = true;
        }
        return self;
    };

    self.then = function (onFulfilled, onRejected) {
        if (isSettled(self)) {
            if (self.isResolved) {
                kickOffChainReaction(self.x, [onFulfilled]);
            } else if (self.isRejected) {
                kickOffChainReaction(self.x, [onRejected]);
            }
        } else {
            self.callBacks.push({onFulFilled: onFulfilled, onRejected: onRejected});
        }
        return self;
    };

    var isSettled = function (p) {
        return p.isResolved || p.isRejected;
    };

    var kickOffChainReaction = function (x, callBacks) {
        tryReplaceX(self, x);
        setTimeout(function () {
            for (var i = 0; i < callBacks.length; i++) {
                tryReplaceX(self, callIfIsFunction(callBacks[i], self.x));
            }
        });
    };

    var tryReplaceX = function (p, newX) {
        if (!isSettled(p)) {
            p.x = newX;
        }
    };

    var callIfIsFunction = function (f, param) {
        if (typeof f === "function") {
            try {
                return f(param);
            } catch (err) {
            }
        } else {
            return param;
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