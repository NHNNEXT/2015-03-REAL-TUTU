(function () {
angular.module('clientApp')
  /* @ngInject */
  .service('containerRepository', function ($q,http,Container,packerService,listFactory) {

    var containerList = [];
    var lectures = {};
    var self = this;

    var candidateList = [];
    var packer = packerService;

    var favoriteLectureContainer = new Container();
    favoriteLectureContainer.setType(["lecture","favorite"]);
    var favoriteTagContainer = new Container();
    favoriteTagContainer.setType(["tag","favorite"]);
    var favoritePersonContainer = new Container();
    favoritePersonContainer.setType("person");
    var noticeContentContainer = new Container();
    noticeContentContainer.setType("notice");
    var LectureNormalContainer = new Container();
    LectureNormalContainer.setType(["lecture","normal"]);

    containerList.push(favoriteLectureContainer);
    containerList.push(favoritePersonContainer);
    containerList.push(favoriteTagContainer);
    containerList.push(noticeContentContainer);
    containerList.push(LectureNormalContainer);

    init();

    function init() {
      return http.get('/api/v1/user/session')
        .then(function (result) {
        lectures = result.lectures;
        for(var i=0; i<lectures.length; i++) {
            candidateList.push(listFactory.create(lectures[i].hostUser));
            candidateList.push(listFactory.create(lectures[i]));
        }
          // root user 에서 lecturelist 를 가져온다
          // 컨테이너에 조건에 맞는 렉쳐를 넣는다.
          // 컨테이너의 종류는
          // 1. 즐겨찾기를 한 컨테이너
          // 2. 태그
          // 3. 공지는 탭말고 위쪽
          // 4. 즐겨찾는 사람ㅇㅇ
        packer.pack(containerList,candidateList);
        self.favoriteLectures = favoriteLectureContainer.contentList;
        self.favoritePersons = favoritePersonContainer.contentList;
        });
    }
    function getfavoriteLectures() {
      init();
    }
    this.init = init;
  });
}());
