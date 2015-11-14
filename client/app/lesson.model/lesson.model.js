angular.module('clientApp')
  .factory('Lesson', function () {
    function Lesson(param) {
      if (param === undefined)
        return;
      if (typeof param !== "object")
        return;
      this.id = param.id;
      this.name = param.name;
      this.description = param.description;
      this.start = new Date(param.start);
      this.end = new Date(param.end);
      this.likes = param.likes;
    }
    return Lesson;
  });
