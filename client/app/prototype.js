Date.prototype.format = function (f) {
  return moment().format(f);
};

Date.prototype.getDateTime = function(){
  return this.getDateString() + " (" + this.getDayKR() + ") " + this.getTimeString();
};

Date.prototype.toYMD = function () {
  if (new Date().isSameDay(this))
    return this.fromNow();
  return this.getDateTime();
};


Date.prototype.getDateString = function () {
  if (this.getFullYear() === new Date().getFullYear())
    return moment(this).format("M월 D일");
  return moment(this).format("YYYY년 M월 D일");
};

Date.prototype.fromNow = function () {
  return moment(this).fromNow();
};

Date.prototype.getTimeString = function () {
  return moment(this).format("HH:mm");
};

Date.prototype.isSameDay = function (date) {
  if (!date)
    return false;
  return this.getFullYear() === date.getFullYear() && this.getMonth() === date.getMonth() && this.getDate() === date.getDate();
};

Date.prototype.getDayKR = function () {
  return ["일", "월", "화", "수", "목", "금", "토"][this.getDay()];
};

Date.prototype.range = function (date) {
  var early, late;
  if (this <= date) {
    early = this;
    late = date;
  } else {
    early = date;
    late = this;
  }
  if (this.isSameDay(date))
    return this.getDateString() + " (" + this.getDayKR() + ") " + early.getTimeString() + " ~ " + late.getTimeString();
  return early.toYMD() + " ~ " + late.toYMD();
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

/* jshint ignore:start */
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
/* jshint ignore:end */

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

Array.prototype.findById = function (id) {
  return this.find(function (el) {
    return el.id === id;
  });
};

Array.prototype.removeById = function (id) {
  return this.remove(this.findById(id));
};

Number.prototype.toDay = function () {
  return ["월", "화", "수", "목", "금", "토", "일"][this % 7];
};


String.prototype.newLine = function () {
  return this.replace(/\n/g, '<br>');
};

String.prototype.removeTags = function () {
  return this.replace(/(<([^>]+)>)|(<[^<]+)$/ig, "");
};
