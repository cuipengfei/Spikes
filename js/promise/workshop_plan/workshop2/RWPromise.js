function RWPromise() {

    var self = this;
    var childrenResolutions = [];

    var isSettled = function () {
        return self.state === 'resolved' || self.state === 'rejected';
    };

    var changeStateTo = function (state, x) {
        if (!isSettled()) {
            self.x = x;
            self.state = state;
            childrenResolutions.forEach(function (childResolution) {
                childResolution(x);
            });
        }
    };

    self.resolve = function (value) {
        changeStateTo('resolved', value);
    };

    self.reject = function (reason) {
        changeStateTo('rejected', reason);
    };

    self.then = function (onFulfilled, onRejected) {
        var child = new RWPromise();

        function childResolution() {
            setTimeout(function () {
                try {
                    onFulfilled = typeof onFulfilled === 'function' ? onFulfilled : function (v) {
                        return v;
                    };

                    onRejected = typeof onRejected === 'function' ? onRejected : function (r) {
                        throw r;
                    };

                    var x;
                    if (self.state === 'resolved') {
                        x = onFulfilled(self.x);
                        child.resolve(x);
                    } else {
                        x = onRejected(self.x);
                        child.reject(x);
                    }
                } catch (err) {
                    child.reject(x);
                }
            });
        }

        if (isSettled()) {
            childResolution();
        } else {
            childrenResolutions.push(childResolution);
        }

        return child;
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