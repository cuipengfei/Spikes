var _ = require('lodash');
var MyStream = require("./myJsStream").MyStream;

var list = _.range(50).map(x => Math.floor(Math.random() * 100));
console.log(`50个0到100之间的随机数： ${list}`);

var isDividedByThree = (x) => {
  var isDivided = x % 3 === 0;
  console.log(`${x} ${isDivided}`);
  return isDivided;
};

// 这一行注释前面的是一摸一样的，不用看

let arr = MyStream.from(list).filter(isDividedByThree).take(2).toArray();
console.log(`其中能够被三整除的前两个是： ${arr}`);
