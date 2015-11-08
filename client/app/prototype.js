Date.prototype.format = function (f) {
  return moment().format(f);
};

Number.prototype.toYMD = function () {
  return new Date(this).toYMD();
};

Date.prototype.toYMD = function () {
  return this.getDateString() + " (" + this.getDayKR() + ") " + this.getTimeString();
};

Date.prototype.getDateString = function () {
  return this.format("YYYY년 MM월 DD일");
};

Date.prototype.fromNow = function () {
  return moment(this).fromNow();
};

Date.prototype.getTimeString = function () {
  return this.format("hh:mm");
};

Date.prototype.isSameDay = function (date) {
  if (!date)
    return false;
  return this.getFullYear() === date.getFullYear() && this.getMonth() === date.getMonth() && this.getDate() === date.getDate();
};

Date.prototype.getDayKR = function () {
  return ["일", "월", "화", "수", "목", "금", "토"][this.getDay()];
};


if (!Array.prototype.includes) {
  Array.prototype.includes = function (searchElement /*, fromIndex*/) {
    'use strict';
    var O = Object(this);
    var len = parseInt(O.length) || 0;
    if (len === 0) {
      return false;
    }
    var n = parseInt(arguments[1]) || 0;
    var k;
    if (n >= 0) {
      k = n;
    } else {
      k = len + n;
      if (k < 0) {
        k = 0;
      }
    }
    var currentElement;
    while (k < len) {
      currentElement = O[k];
      if (searchElement === currentElement ||
        (searchElement !== searchElement && currentElement !== currentElement)) {
        return true;
      }
      k++;
    }
    return false;
  };
}

Array.prototype.ignoreTypeIncludes = function (searchElement /*, fromIndex*/) {
  'use strict';
  var O = Object(this);
  var len = parseInt(O.length) || 0;
  if (len === 0) {
    return false;
  }
  var n = parseInt(arguments[1]) || 0;
  var k;
  if (n >= 0) {
    k = n;
  } else {
    k = len + n;
    if (k < 0) {
      k = 0;
    }
  }
  var currentElement;
  while (k < len) {
    currentElement = O[k];
    if (searchElement == currentElement ||
      (searchElement != searchElement && currentElement != currentElement)) {
      return true;
    }
    k++;
  }
  return false;
};

Array.prototype.toggle = function (el) {
  if (this.includes(el)) {
    this.remove(el);
    return;
  }
  this.push(el);
};

Array.prototype.remove = function (el) {
  if (!this.includes(el))
    return;
  this.splice(this.indexOf(el), 1);
};

Array.prototype.pushIfNotExist = function (el) {
  if (this.includes(el))
    return;
  this.push(el);
};

Number.prototype.toDay = function () {
  return ["월", "화", "수", "목", "금", "토", "일"][this % 7];
};


String.prototype.newLine = function () {
  return this.replace(/\n/g, '<br>');
};

String.prototype.removeTags = function () {
  return this.replace(/(<([^>]+)>)/ig, "");
};
