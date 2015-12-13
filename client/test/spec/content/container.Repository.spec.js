'use strict';

describe('Repo: containerRepo', function () {
  // load the service's module
  beforeEach(module('clientApp'));

  // instantiate service
  var containerRepository;
  var listFactory;
  var Specification;
  var Container;
  var packerService;
  beforeEach(inject(function (_Specification_, _listFactory_, _Container_, _containerRepository_,_packerService_) {
    listFactory = _listFactory_;
    Specification = _Specification_;
    Container = _Container_;
    containerRepository = _containerRepository_;
    packerService = _packerService_;
  }));

  it('should make return list', function () {

    var containerList = [];

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

    var lectures =[
        {
          id: 1,
          name: "dasdsa",
          majorType: 1,
          sideMenu: true,
          approvalState: null,
          hostUser: {
            group: null,
            id: 1,
            email: "test1@test.com",
            name: "asd",
            profileUrl: null,
            major: null,
            introduce: null
          }
        }
      ];

    //var userLectures = rootUser.lectures;
    //var waitingLectures = rootUser.waitingLectures;
    //var lectures = _.union(userLectures,waitingLectures);
    var candidateList = [];
    var packer = packerService;

    _.forEach(lectures,function (lecture) {
      candidateList.push(listFactory.create(lecture.hostUser));
      candidateList.push(listFactory.create(lecture));
    });

    // root user 에서 lecturelist 를 가져온다
    // 컨테이너에 조건에 맞는 렉쳐를 넣는다.
    // 컨테이너의 종류는
    // 1. 즐겨찾기를 한 컨테이너
    // 2. 태그
    // 3. 공지는 탭말고 위쪽
    // 4. 즐겨찾는 사람ㅇㅇ
    packer.pack(containerList,candidateList);

    this.favoriteLectureContainer = favoriteLectureContainer;
    this.favoritePersonContainer = favoritePersonContainer;
    console.log(favoriteLectureContainer.contentList[0]);
    expect(favoriteLectureContainer.contentList[0].name).toEqual("dasdsa");
  });
});

