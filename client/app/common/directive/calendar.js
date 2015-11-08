angular.module('clientApp')
  .directive('calendar', function ($timeout) {
    return {
      restrict: 'A',
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
            defaultDate: scope.$eval(attrs.current),
            events: scope.$eval(attrs.calendar)
          });
        });

        //scope.$watch(attrs.calendar, function(){}, true);

      }
    };
  });
