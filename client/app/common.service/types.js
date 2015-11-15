angular.module('clientApp')
  .factory('types', function () {
    return {
      majorTypes: ["전공필수", "전공선택", "교양"],
      registerPoilicyTypes: ["신청으로 가입", "승인 후 가입", "초대 받은 멤버만 가입"],
      contentTypes: ["강의자료", "질문", "과제"],
      contentIcons: ['fa-newspaper-o', 'fa-question-circle', 'fa-home']
    };
  });
