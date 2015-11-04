angular.module('clientApp')
  .factory('types', function () {
    return {
      majorTypes: ["전공필수", "전공선택", "교양"],
      contentTypes: ["강의자료", "질문", "과제"],
      contentIcons: ['fa-newspaper-o', 'fa-question-circle', 'fa-home']
    };
  });
