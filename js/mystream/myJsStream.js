class MyStream {

  // 工厂方法，用一个array作为数据来源，返回一个新创建的stream。
  static from(elems) {
    if (elems.length === 0) {
      return new Empty();
    }
    else {
      const [head, ...tail] = elems;
      return new NotEmpty(head, () => MyStream.from(tail));
    }
  }

  // 过滤。返回值是一个新的stream
  filter(predicate) {
    if (!(this instanceof NotEmpty)) {
      return new Empty();
    } else {
      if (predicate(this.head)) {
        return new NotEmpty(this.head, () => this.tail().filter(predicate));
      } else {
        return this.tail().filter(predicate);
      }
    }
  }

  // 截取前n个元素。返回值是一个新的stream
  take(n) {
    if (n > 0 && (this instanceof NotEmpty)) {
      if (n === 1) {
        return new NotEmpty(this.head, () => new Empty());
      } else {
        return new NotEmpty(this.head, () => this.tail().take(n - 1));
      }
    }

    return new Empty();
  }

  // 把stream变成数组。
  toArray() {
    if (this instanceof NotEmpty) {
      return [this.head, ...this.tail().toArray()];
    } else {
      return [];
    }
  }

}

// 空stream。
class Empty extends MyStream {
}

// 非空stream。
class NotEmpty extends MyStream {
  constructor(h, t) {
    super();
    this.head = h;
    this.tail = t;
  }
}

exports.MyStream = MyStream;
