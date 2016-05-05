function Promise() {
    var states = {pending: 1, resolved: 2, rejected: 3};
    var self = this;
    self.callbacks = [];
    self.state = states.pending;

    function resolveWith(state, x) {
        if (self.state === states.pending) {//2.1.1.1 & 2.1.2.1 & 2.1.3.1
            self.state = state;//2.1.1.1
            self.x = x;//2.1.2.2 & 2.1.3.2
            self.callbacks.forEach(function (callBack) {
                callBack(x);//2.2.6.1 & 2.2.6.2 If/when promise is fulfilled, all respective callbacks must execute in the order of their originating calls to then
            });
        }
    }

    self.resolve = function (value) {
        resolveWith(states.resolved, value);
    };

    self.reject = function (reason) {
        resolveWith(states.rejected, reason);
    };

    self.then = function (onResolved, onRejected) {
        var promise2 = new Promise();

        function schedulePromise2Resolution() {
            setTimeout(function () {//2.2.4 onFulfilled or onRejected must not be called until the execution context stack contains only platform code.
                try {
                    onResolved = typeof onResolved === 'function' ? onResolved : function (v) {
                        return v;//2.2.7.3 If onFulfilled is not a function and promise1 is fulfilled, promise2 must be fulfilled with the same value as promise1
                    };
                    onRejected = typeof onRejected === 'function' ? onRejected : function (r) {
                        throw r;//2.2.7.4 If onRejected is not a function and promise1 is rejected, promise2 must be rejected with the same reason as promise1
                    };
                    var x = self.state === states.resolved ? onResolved(self.x) : onRejected(self.x);
                    resolutionProcedure(promise2, x);//2.2.7.1 If either onFulfilled or onRejected returns a value x, run the Promise Resolution Procedure [[Resolve]](promise2, x).
                } catch (e) {
                    return promise2.reject(e);//2.2.7.2 If either onFulfilled or onRejected throws an exception e, promise2 must be rejected with e as the reason
                }
            });
        }

        if (self.state === states.pending) {
            self.callbacks.push(schedulePromise2Resolution);
        } else {
            schedulePromise2Resolution();
        }

        return promise2;//2.2.7 then must return a promise. promise2 = promise1.then(onFulfilled, onRejected);
    };
}

function resolutionProcedure(promise, x) {//2.3
    var then;
    var thenCalledOrThrow = false;
    if (promise === x) {//2.3.1 If promise and x refer to the same object, reject promise with a TypeError as the reason.
        promise.reject(new TypeError('Spec 2.3.1, promise and x should not be the same object'))
    }

    if ((x !== null) && ((typeof x === 'object') || (typeof x === 'function'))) {
        try {
            then = x.then;
            if (typeof then === 'function') {
                then.call(x, function (y) {
                    if (thenCalledOrThrow) return;
                    thenCalledOrThrow = true;
                    resolutionProcedure(promise, y);
                }, function (r) {
                    if (thenCalledOrThrow) return;
                    thenCalledOrThrow = true;
                    promise.reject(r);
                })
            } else {
                promise.resolve(x);
            }
        } catch (e) {
            if (thenCalledOrThrow) return;
            thenCalledOrThrow = true;
            promise.reject(e);
        }
    } else {
        promise.resolve(x);
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