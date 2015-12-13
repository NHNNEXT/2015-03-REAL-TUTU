(function () {
  'use strict';
  angular.module('clientApp')
  .service('mainButler', mainButler);

  /* @ngInject */
  function mainButler() {

    var self = this;

    var date = initDate();
    var scrollWidth = (function() {
      var outer = document.createElement("div");
      outer.style.visibility = "hidden";
      outer.style.width = "100px";
      outer.style.msOverflowStyle = "scrollbar"; // needed for WinJS apps
      document.body.appendChild(outer);
      var widthNoScroll = outer.offsetWidth;
      // force scrollbars
      outer.style.overflow = "scroll";
      // add innerdiv
      var inner = document.createElement("div");
      inner.style.width = "100%";
      outer.appendChild(inner);
      var widthWithScroll = inner.offsetWidth;
      // remove divs
      outer.parentNode.removeChild(outer);
      return widthNoScroll - widthWithScroll;
    })();


    function initDate() {
      return changeDate(7, 7);
    }

    function setDateNow() {
      self.date = changeDate(180, 0);
    }

    function setDateOneWeek() {
      self.date = changeDate(0, 7);
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

    this.date= date;
    this.setDateNow= setDateNow;
    this.setDateOneWeek= setDateOneWeek;
    this.scrollWidth= scrollWidth;
  }
}());
