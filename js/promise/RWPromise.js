var n = 0;

function RWPromise() {
    var states = {pending: "pending", resolved: "resolved", rejected: "rejected"};

    var self = this;
    self.callBacks = undefined;
    self.children = [];
    self.state = states.pending;
    self.name = (n++).toString();// this is only for debugging, the name is like: 1 -> 1's son -> 1's grandson

    self.resolve = function (value) {
        self.tryResolveWith(value, states.resolved);
        return self;
    };

    self.reject = function (reason) {
        self.tryResolveWith(reason, states.rejected);
        return self;
    };

    self.tryResolveWith = function (x, state) {

        var takeOverThen;

        function resolveSelf() {

            function resolveWith(v, s) {
                self.x = v;
                self.state = s;
            }

            function potentialThenableResolve() {
                //self is promise1
                //child is promise2
                var newXThen = newX.then;
                if (typeof newXThen === "function") {
                    //if then is a function, call it with x as this, first argument resolutionProcedure, and second argument rejectPromise, where:
                    // If/when resolutionProcedure is called with a value y, run [[Resolve]](promise, y).
                    //     If/when rejectPromise is called with a reason r, reject promise with r.
                    takeOverThen = newXThen;
                    self.x = newX;
                    self.isTakenOver = true;
                    takeOverThen.call(newX, self.resolve, self.reject);
                } else {
                    resolveWith(newX, states.resolved);
                }
            }

            function resolveBasedOnNewX() {
                var isSameRef = newX === self;
                var isPotentialThenable = (newX !== null) && ((typeof newX === 'object') || (typeof newX === 'function'));

                if (isSameRef) {
                    resolveWith(new TypeError("Violation of 2.3.1."), states.rejected);
                } else if (isPotentialThenable) {
                    potentialThenableResolve();
                }
                else {
                    resolveWith(newX, states.resolved);
                }
            }

            if (self.callBacks === undefined) {
                resolveWith(x, state);
            }
            else {
                var isResolved = self.state === states.resolved;
                var isTryingToResolve = (self.state === states.pending && state === states.resolved);
                var f = (isResolved || isTryingToResolve) ? self.callBacks.onFulfilled : self.callBacks.onRejected;

                var newX;
                if (self.isTakenOver) {
                    newX = x;
                    try {
                        resolveBasedOnNewX();
                    } catch (err) {
                        resolveWith(err, states.rejected);
                    }
                }
                else if (typeof f === "function") {
                    try {
                        newX = self.isTakenOver ? x : f(x);
                        resolveBasedOnNewX();
                    } catch (err) {
                        resolveWith(err, states.rejected);
                    }
                } else if (self.state === states.pending) {
                    resolveWith(x, state);
                }
            }
        }

        function resolveChildren() {
            if (!takeOverThen) {
                self.children.forEach(function (child) {
                    child.tryResolveWith(self.x, self.state);
                });
            }
        }

        setTimeout(function () {
            if (!isSettled()) {
                // console.log("TRYING " + state + " " + self.name + " with: " + JSON.stringify(x));
                resolveSelf();
                // console.log("FINISHED " + self.state + " " + self.name + " with: " + JSON.stringify(self.x));
                // console.log();
                resolveChildren();
            }
        });
    };

    self.then = function (onFulfilled, onRejected) {
        var child = new RWPromise();
        child.callBacks = {onFulfilled: onFulfilled, onRejected: onRejected};

        if (isSettled()) {
            child.name = self.name + " -> " + child.name + " (bastard, created after settled)";
            child.tryResolveWith(self.x, self.state);
        } else {
            child.name = self.name + " -> " + child.name;
            self.children.push(child);
        }

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