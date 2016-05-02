var n = 0;

function RWPromise() {
    var states = {pending: "pending", resolved: "resolved", rejected: "rejected"};

    var self = this;
    self.callBacks = [];
    self.children = [];
    self.state = states.pending;
    self.name = n++;// this is only for debugging, the name is like: 1 -> 1's son -> 1's grandson

    self.resolve = function (value) {
        self.tryResolveWith(value, states.resolved);
        return self;
    };

    self.reject = function (reason) {
        self.tryResolveWith(reason, states.rejected);
        return self;
    };

    self.tryResolveWith = function (x, state) {

        function resolveSelf() {
            if (self.callBacks.length === 0) {
                self.state = state;
            }
            else {
                self.callBacks.forEach(function (callBack) {
                    var isResolved = self.state === states.resolved;
                    var isTryingToResolve = (self.state === states.pending && state === states.resolved);
                    var f = (isResolved || isTryingToResolve) ? callBack.onFulFilled : callBack.onRejected;

                    if (typeof f === "function") {
                        try {
                            self.x = f(self.x);
                            self.state = states.resolved;
                        } catch (err) {
                            self.x = err;
                            self.state = states.rejected;
                        }
                    } else {
                        if (self.state === states.pending) {
                            self.state = state;
                        }
                    }
                });
            }
        }

        function resolveChildren() {
            self.children.forEach(function (branch) {
                branch.tryResolveWith(self.x, self.state);
            });
        }
        
        setTimeout(function () {
            if (!isSettled()) {
                self.x = x;
                resolveSelf();
                resolveChildren();
            }
        });
    };

    self.then = function (onFulfilled, onRejected) {
        var child = new RWPromise();
        child.name = self.name + " -> " + child.name;
        child.callBacks.push({onFulFilled: onFulfilled, onRejected: onRejected});

        self.children.push(child);

        setTimeout(function () {
            if (isSettled(self)) {
                child.tryResolveWith(self.x, self.state);
            }
        });

        return child;
    };

    var isSettled = function () {
        return self.state === states.resolved || self.state === states.rejected;
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