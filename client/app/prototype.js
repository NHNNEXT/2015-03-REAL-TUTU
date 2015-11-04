Date.prototype.format = function (f) {
  if (!this.valueOf()) return " ";

  var weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
  var d = this;

  return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function ($1) {
    switch ($1) {
      case "yyyy":
        return d.getFullYear();
      case "yy":
        return (d.getFullYear() % 1000).zf(2);
      case "MM":
        return (d.getMonth() + 1).zf(2);
      case "dd":
        return d.getDate().zf(2);
      case "E":
        return weekName[d.getDay()];
      case "HH":
        return d.getHours().zf(2);
      case "hh":
        return ((h = d.getHours() % 12) ? h : 12).zf(2);
      case "mm":
        return d.getMinutes().zf(2);
      case "ss":
        return d.getSeconds().zf(2);
      case "a/p":
        return d.getHours() < 12 ? "오전" : "오후";
      default:
        return $1;
    }
  });
};

String.prototype.string = function (len) {
  var s = '', i = 0;
  while (i++ < len) {
    s += this;
  }
  return s;
};

String.prototype.zf = function (len) {
  return "0".string(len - this.length) + this;
};


Number.prototype.zf = function (len) {
  return this.toString().zf(len);
};

Date.prototype.toYMD = function () {
  return this.getDateString() + " (" + this.getDayKR() + ") " + this.getTimeString();
};

Date.prototype.getDateString = function () {
  return this.format("yyyy년 MM월 dd일");
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
}


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


function ClassTime(day, startTime, endTime) {
  this.day = day;
  this.startTime = startTime;
  this.endTime = endTime;
}

ClassTime.prototype.getTime = function () {
  return this.day.toDay() + " " + this.startTime + " ~ " + this.endTime;
};

function Lecture(year, semester, name, classNo, type, credit, times, prof, id, desc) {
  this.year = year;
  this.semester = semester;
  this.name = name;
  this.classNo = classNo;
  this.type = ["전공필수", "전공선택", "교양"][type];
  this.credit = credit;
  this.time = "";
  for (var i = 0; i < times.length; i++)
    this.time += times[i].getTime() + "<br>";
  this.time = this.time.substr(0, this.time.length - 4);
  this.prof = prof;
  this.id = id;
  this.desc = desc;
}
