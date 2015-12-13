(function () {
  'use strict';
  angular.module('clientApp')
  .factory('mainButler', mainButler);

  /* @ngInject */
  function mainButler() {
    var Butler = {
      date: initDate(),
      setDateNow : setDateNow,
      setDateOneWeek : setDateOneWeek
    };

    function initDate() {
      return changeDate(7, 7);
    }

    function setDateNow() {
      this.date = changeDate(180, 0);
    }

    function setDateOneWeek() {
      this.date = changeDate(0, 7);
    }

    function changeDate(beforeDates, afterDates) {
      var start = new Date();
      var end = new Date();

      start.setDate(start.getDate() - beforeDates);
      end.setDate(end.getDate() + afterDates);
      return {
        start: start,
        end: end
      };
    }
    return Butler;
  }
}());
