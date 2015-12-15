(function () {
angular.module('clientApp')
  /* @ngInject */
  .service('containerRepository', function ($timeout,http,Container,packerService,candidateFactory) {

    var containerList = [];
    var self = this;
    var userCollection = {};
    var lectureCollection = {};
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

    function categorize(lectures) {
      var len = lectures.length;
        for(var i=0; i<len; i++) {
          _.set(userCollection,'lectures[i].hostUser.id' ,candidateFactory.create(lectures[i].hostUser));
          _.set(lectureCollection,'lectures[i].id' ,candidateFactory.create(lectures[i]));
        }
          // 컨테이너에 조건에 맞는 렉쳐를 넣는다.
          // 컨테이너의 종류는
          // 1. 즐겨찾기를 한 컨테이너
          // 2. 태그
          // 3. 공지는 탭말고 위쪽
          // 4. 즐겨찾는 사람ㅇㅇ

        packer.pack(containerList,lectureCollection);
        self.favoriteLectures = favoriteLectureContainer.contentList;
        self.favoritePersons = favoritePersonContainer.contentList;
        }
    function updateLecture(lecture) {
      _.set(userCollection,'lecture.hostUser.id' ,candidateFactory.create(lecture.hostUser));
      _.set(lectureCollection,'lecture.id' ,candidateFactory.create(lecture));
    }
    this.categorize = categorize;
  });
}());
