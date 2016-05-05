function Promise() {
    var states = {pending: 1, resolved: 2, rejected: 3};
    var self = this;
    self.callbacks = [];
    self.state = states.pending;

    function resolveWith(state, data) {
        if (self.state !== states.pending) {
            return;
        }
        self.state = state;
        self.data = data;
        self.callbacks.forEach(function (callBack) {
            callBack(data);//   2.2.6.1 If/when promise is fulfilled, all respective onFulfilled callbacks must execute in the order of their originating calls to then
            //and also          2.2.6.2 If/when promise is rejected, all respective onRejected callbacks must execute in the order of their originating calls to then
        });
    }

    self.resolve = function (value) {
        resolveWith(states.resolved, value);
    };

    self.reject = function (reason) {
        resolveWith(states.rejected, reason);
    };

    self.then = function (onResolved, onRejected) {
        onResolved = typeof onResolved === 'function' ? onResolved : function (v) {
            return v; //2.2.7.3 If onFulfilled is not a function and promise1 is fulfilled, promise2 must be fulfilled with the same value as promise1
        };
        onRejected = typeof onRejected === 'function' ? onRejected : function (r) {
            throw r; //2.2.7.4 If onRejected is not a function and promise1 is rejected, promise2 must be rejected with the same reason as promise1
        };
        var promise2 = new Promise();

        function resolvePromise2() {
            setTimeout(function () {
                try {
                    var x;
                    if (self.state === states.resolved) {
                        x = onResolved(self.data);
                    } else if (self.state === states.rejected) {
                        x = onRejected(self.data);
                    }
                    resolutionProcedure(promise2, x);//2.2.7.1 If either onFulfilled or onRejected returns a value x, run the Promise Resolution Procedure [[Resolve]](promise2, x).
                } catch (e) {
                    return promise2.reject(e);//2.2.7.2 If either onFulfilled or onRejected throws an exception e, promise2 must be rejected with e as the reason
                }
            });
        }

        if (self.state === states.pending) {
            self.callbacks.push(resolvePromise2);
        } else {
            resolvePromise2();
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