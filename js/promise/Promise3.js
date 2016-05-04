function Promise() {
    var self = this;
    self.callbacks = [];
    self.status = 'pending';

    self.resolve = function (value) {
        
        setTimeout(function () {
            if (self.status !== 'pending') {
                return
            }
            self.status = 'resolved';
            self.data = value;

            for (var i = 0; i < self.callbacks.length; i++) {
                self.callbacks[i].onResolved(value)
            }
        })
    };

    self.reject = function (reason) {
        setTimeout(function () {
            if (self.status !== 'pending') {
                return
            }
            self.status = 'rejected';
            self.data = reason;

            for (var i = 0; i < self.callbacks.length; i++) {
                self.callbacks[i].onRejected(reason);
            }
        })
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
                    try {
                        resolutionProcedure(promise2, onResolved(value));
                    } catch (e) {
                        return promise2.reject(e);
                    }
                },
                onRejected: function (reason) {
                    try {
                        resolutionProcedure(promise2, onRejected(reason));
                    } catch (e) {
                        return promise2.reject(e);
                    }
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
    var then
    var thenCalledOrThrow = false
    if (promise === x) {
        return promise.reject(new TypeError('Chaining cycle detected for promise!'))
    }

    if ((x !== null) && ((typeof x === 'object') || (typeof x === 'function'))) {
        try {
            then = x.then
            if (typeof then === 'function') {
                then.call(x, function rs(y) {
                    if (thenCalledOrThrow) return
                    thenCalledOrThrow = true
                    return resolutionProcedure(promise, y)
                }, function rj(r) {
                    if (thenCalledOrThrow) return
                    thenCalledOrThrow = true
                    return promise.reject(r)
                })
            } else {
                return promise.resolve(x)
            }
        } catch (e) {
            if (thenCalledOrThrow) return
            thenCalledOrThrow = true
            return promise.reject(e)
        }
    } else {
        return promise.resolve(x)
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