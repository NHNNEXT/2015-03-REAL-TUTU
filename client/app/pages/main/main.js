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


angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('main', {
      url: "/",
      templateUrl: "/pages/main/main.html",
      controller: function ($scope) {
        var lectures = $scope.lectures = [];
        var teachLectures = $scope.teachLectures = [];
        var myLectures = $scope.myLectures = [];
        lectures.push(new Lecture(2014, 1, "자료구조와 알고리즘2", 0, 1, 3, [new ClassTime(3, "09:00", "11:00")], "김동진", 1, "설명"));
        lectures.push(new Lecture(2013, 2, "료구조와 알고리즘2", 2, 2, 4, [new ClassTime(3, "09:00", "11:00")], "김동진", 2, "설명"));
        lectures.push(new Lecture(2012, 3, "구조와 알고리즘2", 4, 1, 5, [new ClassTime(3, "09:00", "11:00")], "김동진", 3, "설명"));
        lectures.push(new Lecture(2011, 4, "조와 알고리즘2", 5, 1, 6, [new ClassTime(3, "09:00", "11:00")], "김동진", 4, "설명"));
        lectures.push(new Lecture(2010, 5, "와 알고리즘2", 6, 1, 7, [new ClassTime(3, "09:00", "11:00")], "김동진", 5, "설명"));
        teachLectures.push(new Lecture(2010, 5, "와 알고리즘2", 6, 1, 7, [new ClassTime(3, "09:00", "11:00")], "김동진", 5, "설명"));
        teachLectures.push(new Lecture(2010, 5, "와 알고리즘2", 6, 1, 7, [new ClassTime(3, "09:00", "11:00")], "김동진", 5, "설명"));
        myLectures.push(new Lecture(2010, 5, "와 알고리즘2", 6, 1, 7, [new ClassTime(3, "09:00", "11:00")], "김동진", 5, "설명"));
        myLectures.push(new Lecture(2010, 5, "와 알고리즘2", 6, 1, 7, [new ClassTime(3, "09:00", "11:00")], "김동진", 5, "설명"));


      }
    });
});
