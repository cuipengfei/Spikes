function Promise() {
    var self = this
    self.callbacks = []
    self.status = 'pending'

    self.resolve = function (value) {
        if (value instanceof Promise) {
            return value.then(self.resolve, self.reject)
        }
        setTimeout(function () {
            if (self.status !== 'pending') {
                return
            }
            self.status = 'resolved'
            self.data = value

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
            self.status = 'rejected'
            self.data = reason

            for (var i = 0; i < self.callbacks.length; i++) {
                self.callbacks[i].onRejected(reason)
            }
        })
    };

    self.then = function (onResolved, onRejected) {
        onResolved = typeof onResolved === 'function' ? onResolved : function (v) {
            return v
        }
        onRejected = typeof onRejected === 'function' ? onRejected : function (r) {
            throw r
        }
        var promise2 = new Promise();

        if (self.status === 'resolved') {
            setTimeout(function () {
                try {
                    resolvePromise(promise2, onResolved(self.data))
                } catch (e) {
                    return promise2.reject(e)
                }
            })
        }

        if (self.status === 'rejected') {
            setTimeout(function () {
                try {
                    resolvePromise(promise2, onRejected(self.data))
                } catch (e) {
                    return promise2.reject(e)
                }
            })
        }

        if (self.status === 'pending') {
            self.callbacks.push({
                onResolved: function (value) {
                    try {
                        resolvePromise(promise2, onResolved(value))
                    } catch (e) {
                        return promise2.reject(e)
                    }
                },
                onRejected: function (reason) {
                    try {
                        resolvePromise(promise2, onRejected(reason))
                    } catch (e) {
                        return promise2.reject(e)
                    }
                }
            })
        }

        return promise2
    };
}

function resolvePromise(promise, x) {
    var then
    var thenCalledOrThrow = false
    if (promise === x) {
        return promise.reject(new TypeError('Chaining cycle detected for promise!'))
    }

    if (x instanceof Promise) {
        if (x.status === 'pending') {
            x.then(function (v) {
                resolvePromise(promise, v);
            }, promise.reject);
        } else {
            x.then(promise.resolve, promise.reject)
        }
        return
    }

    if ((x !== null) && ((typeof x === 'object') || (typeof x === 'function'))) {
        try {
            then = x.then
            if (typeof then === 'function') {
                then.call(x, function rs(y) {
                    if (thenCalledOrThrow) return
                    thenCalledOrThrow = true
                    return resolvePromise(promise, y)
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
}