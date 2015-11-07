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
              left: 'prev,next today',
              center: 'title',
              right: 'month,basicWeek,basicDay'
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


$('#calendar').fullCalendar({
  header: {
    left: 'prev,next today',
    center: 'title',
    right: 'month,basicWeek,basicDay'
  },
  defaultDate: '2015-02-12',
  editable: true,
  eventLimit: true, // allow "more" link when too many events
  events: [
    {
      title: 'All Day Event',
      start: '2015-02-01'
    },
    {
      title: 'Long Event',
      start: '2015-02-07',
      end: '2015-02-10'
    },
    {
      id: 999,
      title: 'Repeating Event',
      start: '2015-02-09T16:00:00'
    },
    {
      id: 999,
      title: 'Repeating Event',
      start: '2015-02-16T16:00:00'
    },
    {
      title: 'Conference',
      start: '2015-02-11',
      end: '2015-02-13'
    },
    {
      title: 'Meeting',
      start: '2015-02-12T10:30:00',
      end: '2015-02-12T12:30:00'
    },
    {
      title: 'Lunch',
      start: '2015-02-12T12:00:00'
    },
    {
      title: 'Meeting',
      start: '2015-02-12T14:30:00'
    },
    {
      title: 'Happy Hour',
      start: '2015-02-12T17:30:00'
    },
    {
      title: 'Dinner',
      start: '2015-02-12T20:00:00'
    },
    {
      title: 'Birthday Party',
      start: '2015-02-13T07:00:00'
    },
    {
      title: 'Click for Google',
      url: 'http://google.com/',
      start: '2015-02-28'
    }
  ]
});
