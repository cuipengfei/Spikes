function RWPromise() {
    var self = this;
    self.isPending = true;

    self.resolve = function (value) {
        if (!isSettled(self)) {
            kickOffChainReaction(value, self.fulfillChain);
            self.isResolved = true;
        }
        return self;
    };

    self.reject = function (reason) {
        if (!isSettled(self)) {
            kickOffChainReaction(reason, self.rejectChain);
            self.isRejected = true;
        }
        return self;
    };

    self.then = function (onFulfilled, onRejected) {
        function chain() {
            var fulfillNode = {func: onFulfilled};
            if (self.fulfillChain === undefined) {
                self.fulfillChain = fulfillNode;
                self.fulfillChain.last = fulfillNode;
            } else {
                self.fulfillChain.last.next = fulfillNode;
                self.fulfillChain.last = fulfillNode;
            }

            var rejectNode = {func: onRejected};
            if (self.rejectChain === undefined) {
                self.rejectChain = rejectNode;
                self.rejectChain.last = rejectNode;
            } else {
                self.rejectChain.last.next = rejectNode;
                self.rejectChain.last = rejectNode;
            }
        }

        if (isSettled(self)) {
            if (self.isResolved) {
                kickOffChainReaction(self.x, {func: onFulfilled});
            } else if (self.isRejected) {
                kickOffChainReaction(self.x, {func: onRejected});
            }
        } else {
            chain();
        }
        return self;
    };

    var isSettled = function (p) {
        return p.isResolved || p.isRejected;
    };

    var kickOffChainReaction = function (x, chainNode) {
        tryReplaceX(self, x);
        if (chainNode !== undefined) {
            setTimeout(function () {
                tryReplaceX(self, callIfIsFunction(chainNode.func, self.x));
                kickOffChainReaction(self.x, chainNode.next);
            });
        }
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