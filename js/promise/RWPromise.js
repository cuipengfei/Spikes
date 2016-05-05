function RWPromise() {
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
        var promise2 = new RWPromise();

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
            self.callbacks.push(schedulePromise2Resolution);//they will be scheduled when self is resolved/rejected
        } else {
            schedulePromise2Resolution();//it is being scheduled now, but ran later
        }

        return promise2;//2.2.7 then must return a promise. promise2 = promise1.then(onFulfilled, onRejected);
    };
}

function resolutionProcedure(promise, x) {//2.3
    if (promise === x) {//2.3.1 If promise and x refer to the same object, reject promise with a TypeError as the reason.
        promise.reject(new TypeError('Spec 2.3.1, promise and x should not be the same object'))
    }

    if ((x !== null) && ((typeof x === 'object') || (typeof x === 'function'))) {//2.3.3
        try {
            var thenCalledOrThrow = false;
            var then = x.then;//2.3.3.1 Let then be x.then.
            if (typeof then === 'function') {
                then.call(x, function (y) {//2.3.3.3 If then is a function, call it with x as this, first argument resolvePromise, and second argument rejectPromise
                    if (!thenCalledOrThrow) {
                        thenCalledOrThrow = true;
                        resolutionProcedure(promise, y);//2.3.3.1 If/when resolvePromise is called with a value y, run [[Resolve]](promise, y).
                    }
                }, function (r) {
                    if (!thenCalledOrThrow) {
                        thenCalledOrThrow = true;
                        promise.reject(r);//2.3.3.3.2 If/when rejectPromise is called with a reason r, reject promise with r.
                    }
                });
            } else {
                promise.resolve(x);//2.3.3.4 If then is not a function, fulfill promise with x.
            }
        } catch (e) {
            if (!thenCalledOrThrow) {//2.3.3.3.4.1
                thenCalledOrThrow = true;
                promise.reject(e);//2.3.3.3.4.2 & 2.3.3.2 If retrieving the property x.then results in a thrown exception e, reject promise with e as the reason.
            }
        }
    } else {
        promise.resolve(x);
    }
}

module.exports.deferred = function () {
    var promise = new RWPromise();
    return {
        promise: promise,
        resolve: promise.resolve,
        reject: promise.reject
    }
};