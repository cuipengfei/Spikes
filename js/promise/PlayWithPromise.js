var p3 = require("./Promise3");
var chalk = require("chalk");

(function stringFromWebExample() {
    function sayHello() {
        function getStringFromWeb(resolve, reject) {
            setTimeout(function () {
                var someString = "string from response";
                if (Math.random() >= 0.5) { //50/50 fail success rate
                    resolve(someString);
                } else {
                    reject("network failure");
                }
            }, 1000 + Math.random() * 2000); //each web request may take different time to finish
        }

        return new p3(getStringFromWeb);
    }

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

    var interleaveTimesMax = 400;

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
    //3. 异步操作完成之后,多次then的调用所构成的onFulfilled或者onRejected链条并不是在同一个time slot里面执行的
})();
