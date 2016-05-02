var n = 0;
function RWPromise() {
    var self = this;
    self.callBacks = [];
    self.branches = [];
    self.name = n++;

    self.resolve = function (value) {
        if (!isSettled(self)) {
            self.x = value;
            kickOffChainReaction(true);
        }
        return self;
    };

    self.reject = function (reason) {
        if (!isSettled(self)) {
            self.x = reason;
            kickOffChainReaction(false);
        }
        return self;
    };

    self.then = function (onFulfilled, onRejected) {
        var branch = new RWPromise();
        branch.name = self.name + " - " + branch.name;
        branch.callBacks.push({onFulFilled: onFulfilled, onRejected: onRejected});

        self.branches.push(branch);

        setTimeout(function () {
            if (isSettled(self)) {
                if (self.state === "resolved") {
                    branch.resolve(self.x);
                } else if (self.state === "rejected") {
                    branch.reject(self.x);
                }
            }
        });

        return branch;
    };

    var isSettled = function (p) {
        return p.state === "resolved" || p.state === "rejected";
    };

    var kickOffChainReaction = function (tryFulfill) {
        setTimeout(function () {
            if (self.callBacks.length === 0) {
                if (tryFulfill) {
                    self.state = "resolved";
                } else {
                    self.state = "rejected";
                }
            }
            else {
                self.callBacks.forEach(function (callBack) {

                    var f = (self.state === "resolved" || (self.state === undefined && tryFulfill)) ? callBack.onFulFilled : callBack.onRejected;
                    if (typeof f === "function") {
                        try {
                            self.x = f(self.x);
                            self.state = "resolved";
                        } catch (err) {
                            self.x = err;
                            self.state = "rejected";
                        }
                    } else {
                        if (self.state === undefined) {
                            if (tryFulfill) {
                                self.state = "resolved";
                            } else {
                                self.state = "rejected";
                            }
                        }
                    }
                });
            }

            if (self.state === "resolved") {
                self.branches.forEach(function (branch) {
                    branch.resolve(self.x);
                });
            } else {
                self.branches.forEach(function (branch) {
                    branch.reject(self.x);
                });
            }
        });
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