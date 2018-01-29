var chalk = require("chalk");
var RWPromise = require("./index");

//this is async api
function getProductPriceFromTaoBao(productName, found, notFound, error) {
    setTimeout(function () {
        var rate = Math.random();
        if (rate >= 0.5) { //50/50 fail success rate
            found(productName + " price from taobao is: " + (100 + Math.random() * 100));
        } else if (rate <= 0.3) {
            notFound("taobao does not have: " + productName);
        } else {
            error("network failed, please try again later");
        }
    }, 1000 + Math.random() * 2000); //each web request may take different time to finish
}

function buy(productName, bought, payFailed, error) {
    setTimeout(function () {
        var rate = Math.random();
        if (rate >= 0.5) { //50/50 fail success rate
            bought(productName + " bought, please wait for delivery");
        } else if (rate <= 0.3) {
            payFailed("Alipay failed");
        } else {
            error("network failed, please try again later");
        }
    }, 1000 + Math.random() * 2000); //each web request may take different time to finish
}

//this is client code
var wantedProduct = "bluetooth earphone";
// getProductPriceFromTaoBao(wantedProduct, function (result) {
//     console.log(chalk.green(result));
//     buy(wantedProduct, function (boughtMessage) {
//         console.log(chalk.green(boughtMessage));
//     }, function (paymentFailMessage) {
//         console.log(chalk.red(paymentFailMessage));
//     }, function (err) {
//         console.log(chalk.red(err));
//     });
// }, function (notFoundMessage) {
//     console.log(chalk.red(notFoundMessage));
// }, function (err) {
//     console.log(chalk.red(err));
// });


//if you want to make the client code cleaner, this might be what you do

var foundHandler = function (result) {
    console.log(chalk.green(result));
    buy(wantedProduct, boughtHandler, errorHandler, errorHandler);
};

var boughtHandler = function (boughtMessage) {
    console.log(chalk.green(boughtMessage));
};

var errorHandler = function (err) {
    console.log(chalk.red(err));
};

// getProductPriceFromTaoBao(wantedProduct, foundHandler, errorHandler, errorHandler);


//this is the async api written with promise

function getProductPriceFromTaoBaoP(productName) {
    var rwPromise = new RWPromise();
    setTimeout(function () {
        var rate = Math.random();
        if (rate >= 0.5) { //50/50 fail success rate
            rwPromise.resolve(productName + " price from taobao is: " + (100 + Math.random() * 100));
        } else if (rate <= 0.3) {
            rwPromise.reject("taobao does not have: " + productName);
        } else {
            rwPromise.reject("network failed, please try again later");
        }
    }, 1000 + Math.random() * 2000); //each web request may take different time to finish
    return rwPromise;
}

function buyP(productName) {
    var rwPromise = new RWPromise();
    setTimeout(function () {
        var rate = Math.random();
        if (rate >= 0.5) { //50/50 fail success rate
            rwPromise.resolve(productName + " bought, please wait for delivery");
        } else if (rate <= 0.3) {
            rwPromise.reject("Alipay failed");
        } else {
            rwPromise.reject("network failed, please try again later");
        }
    }, 1000 + Math.random() * 2000); //each web request may take different time to finish
    return rwPromise;
}

//this is client code for async api with promise

var errorHandlerP = function (err) {
    console.log(chalk.red(err));
};

getProductPriceFromTaoBaoP(wantedProduct)
    .then(function (result) {
        console.log(chalk.green(result));
        return buyP(wantedProduct);
    })
    .then(function (boughtMessage) {
        console.log(chalk.green(boughtMessage));
    }, errorHandlerP);

//此示例演示的是:
//Promise的作用在于
//1. 给异步算法的编写者和使用者之间提供一种统一的交流手段
//2. 给异步算法的使用者提供一种组织代码的手段,以便于将一层又一层嵌套的业务主流程变成一次一次的对then的调用
