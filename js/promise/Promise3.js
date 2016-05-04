function Promise(resolver) {
    var self = this
    self.callbacks = []
    self.status = 'pending'

    function resolve(value) {
        if (value instanceof Promise) {
            return value.then(resolve, reject)
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
    }

    function reject(reason) {
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
    }

    function then(onResolved, onRejected) {
        onResolved = typeof onResolved === 'function' ? onResolved : function (v) {
            return v
        }
        onRejected = typeof onRejected === 'function' ? onRejected : function (r) {
            throw r
        }
        var self = this
        var promise2

        if (self.status === 'resolved') {
            promise2 = new Promise(function (resolve, reject) {
                setTimeout(function () {
                    try {
                        resolvePromise(promise2, onResolved(self.data))
                    } catch (e) {
                        return reject(e)
                    }
                })
            })
        }

        if (self.status === 'rejected') {
            promise2 = new Promise(function (resolve, reject) {
                setTimeout(function () {
                    try {
                        resolvePromise(promise2, onRejected(self.data))
                    } catch (e) {
                        return reject(e)
                    }
                })
            })
        }

        if (self.status === 'pending') {
            promise2 = new Promise(function (resolve, reject) {
                self.callbacks.push({
                    onResolved: function (value) {
                        try {
                            resolvePromise(promise2, onResolved(value))
                        } catch (e) {
                            return reject(e)
                        }
                    },
                    onRejected: function (reason) {
                        try {
                            resolvePromise(promise2, onRejected(reason))
                        } catch (e) {
                            return reject(e)
                        }
                    }
                })
            })
        }

        return promise2
    }

    self.resolve = resolve;
    self.reject = reject;
    self.then = then

    try {
        resolver(resolve, reject)
    } catch (e) {
        reject(e)
    }
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
    var promise = new Promise(function () {
        //empty promise does nothing
    });

    return {
        promise: promise,
        resolve: promise.resolve,
        reject: promise.reject
    }
}