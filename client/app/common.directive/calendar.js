angular.module('clientApp')
  .directive('calendar', function () {
    return {
      restrict: 'A',
      /* @ngInject */
      link: function (scope, element, attrs) {
        if (!attrs.calendar)
          return;
        scope.$watch(attrs.calendar, function (events) {
          if (!events)
            return;
          $(element).fullCalendar({
            header: {
              left: 'title',
              right: 'prev,next month,basicWeek,basicDay'
            },
            editable: true,
            eventLimit: true,
            defaultDate: new Date(),
            events: scope.$eval(attrs.calendar)
          });
        });


      }
    };
  });
