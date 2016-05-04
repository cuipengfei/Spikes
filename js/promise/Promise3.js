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

    try {
        resolver(resolve, reject)
    } catch (e) {
        reject(e)
    }
}

function resolvePromise(promise, x, resolve, reject) {
    var then
    var thenCalledOrThrow = false

    if (promise === x) {
        return reject(new TypeError('Chaining cycle detected for promise!'))
    }

    if (x instanceof Promise) {
        if (x.status === 'pending') {
            x.then(function (v) {
                resolvePromise(promise, v, resolve, reject);
            }, reject);
        } else {
            x.then(resolve, reject)
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
                    return resolvePromise(promise, y, resolve, reject)
                }, function rj(r) {
                    if (thenCalledOrThrow) return
                    thenCalledOrThrow = true
                    return reject(r)
                })
            } else {
                return resolve(x)
            }
        } catch (e) {
            if (thenCalledOrThrow) return
            thenCalledOrThrow = true
            return reject(e)
        }
    } else {
        return resolve(x)
    }
}

Promise.prototype.then = function (onResolved, onRejected) {
    onResolved = typeof onResolved === 'function' ? onResolved : function (v) {
        return v
    }
    onRejected = typeof onRejected === 'function' ? onRejected : function (r) {
        throw r
    }
    var self = this
    var promise2

    if (self.status === 'resolved') {
        return promise2 = new Promise(function (resolve, reject) {
            setTimeout(function () {
                try {
                    var value = onResolved(self.data)
                    resolvePromise(promise2, value, resolve, reject)
                } catch (e) {
                    return reject(e)
                }
            })
        })
    }

    if (self.status === 'rejected') {
        return promise2 = new Promise(function (resolve, reject) {
            setTimeout(function () {
                try {
                    var value = onRejected(self.data)
                    resolvePromise(promise2, value, resolve, reject)
                } catch (e) {
                    return reject(e)
                }
            })
        })
    }

    if (self.status === 'pending') {
        return promise2 = new Promise(function (resolve, reject) {
            self.callbacks.push({
                onResolved: function (value) {
                    try {
                        var value = onResolved(value)
                        resolvePromise(promise2, value, resolve, reject)
                    } catch (e) {
                        return reject(e)
                    }
                },
                onRejected: function (reason) {
                    try {
                        var value = onRejected(reason)
                        resolvePromise(promise2, value, resolve, reject)
                    } catch (e) {
                        return reject(e)
                    }
                }
            })
        })
    }
}

module.exports.deferred = function () {
    var dfd = {}
    dfd.promise = new Promise(function (resolve, reject) {
        dfd.resolve = resolve
        dfd.reject = reject
    })
    return dfd
}