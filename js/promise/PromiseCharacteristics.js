var RWPromise = require("./index");
var chalk = require("chalk");

function sayHello() {
    var rwPromise = new RWPromise();

    setTimeout(function () {
        var someString = "string from response";
        if (Math.random() >= 0.5) { //50/50 fail success rate
            rwPromise.resolve(someString);
        } else {
            rwPromise.reject("network failure");
        }
    }, 1000 + Math.random() * 2000); //each web request may take different time to finish

    return rwPromise;
}

(function stringFromWebExample() {
    function sendAsyncWebRequestAndHandleResult(index) {
        function successHandlerStep1(value) {
            console.log("{\n" + chalk.green("    " + index + " success step 1: '" + value + "' add additional info to value"));
            return value + " additional information";
        }

        function successHandlerStep2(value) {
            console.log(chalk.green("    " + index + " success step 2: '" + value + "' saved to DB") + "\n}\n");
        }

        function failureHandlerStep1(reason) {
            console.log("{\n" + chalk.red("    " + index + " failure step 1: '" + reason + "'"));
            throw reason + " oh, no !!";
        }

        function failureHandlerStep2(reason) {
            console.log(chalk.red("    " + index + " failure step 2: '" + reason + "' reported to admin") + "\n}\n");
        }

        sayHello()
            .then(
                function (value) {
                    return successHandlerStep1(value);
                },
                function (reason) {
                    failureHandlerStep1(reason);
                })
            .then(
                function (value) {
                    successHandlerStep2(value);
                },
                function (reason) {
                    failureHandlerStep2(reason);
                });
    }

    for (var i = 0; i < 200; i++) {
        console.log(chalk.gray(i + 1 + "triggering web request"));
        sendAsyncWebRequestAndHandleResult(i + 1);
    }

    var interleaveTimesMax = 350;

    setTimeout(function () {
        (function tryToInterleave() {
            if (interleaveTimesMax == 0) {
                return;
            }

            setTimeout(function () {
                console.log(chalk.yellow("试图插队,如果这一行出现在{}中间的话,说明插队成功"));
                interleaveTimesMax--;
                tryToInterleave();
            }, 5);
        })();
    }, 900);

    //这个例子演示的是:
    //1. 异步操作是被顺序触发的
    //2. 每个异步操作完成所需要的时间不等
    //3. 异步操作完成之后,多次then的调用所构成的onFulfilled或者onRejected链条并不是在同一个time slot里面执行的2.2.4
})();

(function branchChainsExample() {

    function testBranches(index) {
        var promiseOfHello = sayHello();

        var chain1 = promiseOfHello.then(function (value) {
            console.log(chalk.green(index + " this is the first chain"));
            return value + " (this is from the first chain)";
        }, function (reason) {
            console.log(chalk.red(index + " this is the first chain"));
            throw reason + " the first chain rethrows the error";
        });

        var chain2 = promiseOfHello.then(
        function (value) {
            console.log(chalk.green(index + " this is the second chain"));
            return value + " (this is from the second chain)";
        },
        function (reason) {
            console.log(chalk.red(index + " this is the second chain"));
            return reason + " the second chain returns the error";
        });

        var finalOnFulfilled = function (value) {
            console.log(chalk.yellow(index + " this is final onFulfilled function, value is: " + value));
        };
        var finalOnRejected = function (reason) {
            console.log(chalk.yellow(index + " this is final onRejected function, error is: " + reason));
        };

        chain1.then(finalOnFulfilled, finalOnRejected);
        chain2.then(finalOnFulfilled, finalOnRejected);
    }

    for (var i = 0; i < 10; i++) {
        testBranches(i);
    }

    //这个例子演示的是:
    //1. 每次调用then会形成一个新的支链
    //2. 支链之间是互不干扰的
    //3. onRejected如果返回一个值,会导致它的下一个onFulfilled被调用
});
