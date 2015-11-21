angular.module('clientApp').directive('calendar', function () {
  return {
    restrict: 'E',
    scope: {
      contents: '='
    },
    link: function (scope, element) {
      scope.$watch('contents', function (contents) {
        if (!contents || !contents.forEach)
          return;
        var events = [];
        contents.forEach(function (content) {

          if (!content.startTime && !content.endTime)
            return;
          var event = {};
          event.start = content.startTime;
          event.end = content.endTime;
          if (!event.start)
            event.start = event.end;
          if (!event.end)
            event.end = event.start;
          event.title = content.title;
          events.push(event);
        });
        $(element).fullCalendar({
          events: events,
          header: {
            left: 'title',
            right: 'today prev,next, agendaWeek,month'
          },
          defaultView: 'agendaWeek',
          viewDisplay: function () {
            var currentView = $(element).fullCalendar('getView');
            if (currentView.name === 'agendaWeek' || currentView.name === 'agendaDay') {
              // if height is too big for these views, then scrollbars will be hidden
              currentView.setHeight(9999);
            }
          }
        });
      });
    }
  };
});
