function Promise() {
    var self = this;
    self.callbacks = [];
    self.status = 'pending';

    self.resolve = function (value) {
        if (self.status !== 'pending') {
            return
        }
        self.status = 'resolved';
        self.data = value;

        self.callbacks.forEach(function (callBack) {
            callBack.onResolved(value)
        });
    };

    self.reject = function (reason) {
        if (self.status !== 'pending') {
            return
        }
        self.status = 'rejected';
        self.data = reason;

        self.callbacks.forEach(function (callBack) {
            callBack.onRejected(reason)
        });
    };

    self.then = function (onResolved, onRejected) {
        onResolved = typeof onResolved === 'function' ? onResolved : function (v) {
            return v
        };
        onRejected = typeof onRejected === 'function' ? onRejected : function (r) {
            throw r
        };
        var promise2 = new Promise();

        if (self.status === 'pending') {
            self.callbacks.push({
                onResolved: function (value) {
                    setTimeout(function () {
                        try {
                            resolutionProcedure(promise2, onResolved(value));
                        } catch (e) {
                            return promise2.reject(e);
                        }
                    })
                },
                onRejected: function (reason) {
                    setTimeout(function () {
                        try {
                            resolutionProcedure(promise2, onRejected(reason));
                        } catch (e) {
                            return promise2.reject(e);
                        }
                    })
                }
            })
        } else {
            setTimeout(function () {
                try {
                    var x;
                    if (self.status === 'resolved') {
                        x = onResolved(self.data);
                    } else if (self.status === 'rejected') {
                        x = onRejected(self.data);
                    }
                    resolutionProcedure(promise2, x);
                } catch (e) {
                    return promise2.reject(e);
                }
            })
        }

        return promise2;
    };
}

function resolutionProcedure(promise, x) {
    var then;
    var thenCalledOrThrow = false;
    if (promise === x) {
        return promise.reject(new TypeError('Spec 2.3.1, promise and x should not be the same object'))
    }

    if ((x !== null) && ((typeof x === 'object') || (typeof x === 'function'))) {
        try {
            then = x.then;
            if (typeof then === 'function') {
                then.call(x, function (y) {
                    if (thenCalledOrThrow) return;
                    thenCalledOrThrow = true;
                    return resolutionProcedure(promise, y);
                }, function (r) {
                    if (thenCalledOrThrow) return;
                    thenCalledOrThrow = true;
                    return promise.reject(r);
                })
            } else {
                return promise.resolve(x);
            }
        } catch (e) {
            if (thenCalledOrThrow) return;
            thenCalledOrThrow = true;
            return promise.reject(e);
        }
    } else {
        return promise.resolve(x);
    }
}

module.exports.deferred = function () {
    var promise = new Promise();//empty pending promise
    return {
        promise: promise,
        resolve: promise.resolve,
        reject: promise.reject
    }
};