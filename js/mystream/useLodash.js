var _ = require('lodash');

var list = _.range(50).map(x => Math.floor(Math.random() * 100));
console.log(`50个0到100之间的随机数： ${list}`);

var isDividedByThree = (x) => {
  var isDivided = x % 3 === 0;
  console.log(`${x} ${isDivided}`);
  return isDivided;
};

// 这行注释之前的代码不能碰

console.log(`其中能够被三整除的前两个是： ${_.take(_.filter(list, isDividedByThree), 2)}`);

// log说明，isDividedByThree被执行了50次。
// 有什么办法可以让isDividedByThree执行最少的次数吗？
// 毕竟我们只是需要找到前两个符合条件的数字而已，执行五十次有点浪费。
