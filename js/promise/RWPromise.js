var n = 0;

function RWPromise() {
    var states = {pending: "pending", resolved: "resolved", rejected: "rejected"};

    var self = this;
    self.callBacks = undefined;
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

        var stepParentPromise;

        function resolveSelf() {
            console.log("children: " + self.children.length);
            console.log("");

            if (self.callBacks === undefined) {
                self.state = state;
            }
            else {
                var isResolved = self.state === states.resolved;
                var isTryingToResolve = (self.state === states.pending && state === states.resolved);
                var f = (isResolved || isTryingToResolve) ? self.callBacks.onFulFilled : self.callBacks.onRejected;

                if (typeof f === "function") {
                    try {
                        var newX = f(self.x);
                        var newXThen = newX.then;
                        if (newX instanceof RWPromise) {
                            if (newX === self) {
                                self.x = new TypeError("Violation of 2.3.1.");
                                self.state = states.rejected;
                            } else {
                                stepParentPromise = newX;
                            }
                        }
                        // else if (typeof newXThen === "function") {
                        //     newXThen.call();
                        // }
                        else {
                            self.x = newX;
                            self.state = states.resolved;
                        }
                    } catch (err) {
                        self.x = err;
                        self.state = states.rejected;
                    }
                } else {
                    if (self.state === states.pending) {
                        self.state = state;
                    }
                }
            }
        }

        function resolveChildren() {
            self.children.forEach(function (child) {
                if (stepParentPromise) {//if there is a step parent, let it adopt the children
                    stepParentPromise.children.push(child);
                } else {
                    child.tryResolveWith(self.x, self.state);
                }
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
        child.callBacks = {onFulFilled: onFulfilled, onRejected: onRejected};

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