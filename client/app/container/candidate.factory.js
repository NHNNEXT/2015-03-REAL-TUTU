angular.module('clientApp')
  /* @ngInject */
  .factory('candidateFactory', function (containerSpecificationFactory) {
    "use strict";
    //can set,get containerSpec

    /**
     * @class
     */
    function Candidate(candidate) {
      if(this instanceof Candidate){
        this.type = [];
        this.id = candidate.id;
      } else {
        return new Candidate(candidate);
      }
    }

    function setContainerSpecification(specification) {
      if (!this.spec) {
      this.spec = specification;
      }
    }

    function getContainerSpecification() {
      return this.spec;
    }

    Candidate.prototype = {
      setContainerSpecification: setContainerSpecification,
      getContainerSpecification: getContainerSpecification
    };

    //type: [notice,submit,schedule,default]
    //lecture({id:lecture.id})

    /**
     * @class
     * @augments Candidate
     */
    function LectureCandidate(candidate) {
      if(this instanceof LectureCandidate){
        Candidate.call(this,candidate);
        this.type.push("lecture");
        this.name = candidate.name;
        this.url = "lecture/" + candidate.id;
        if(candidate.sideMenu !== null) {
          this.type.push("favorite");
        }else {
          this.type.push("normal");
        }
        this.setContainerSpecification(containerSpecificationFactory.create(this.type));
      } else {
        new LectureCandidate(candidate);
      }
    }

    LectureCandidate.prototype = _.create(Candidate.prototype, {
      'constructor': LectureCandidate
    });

    /**
     * @class
     * @augments Candidate
     */
    function UserCandidate(candidate) {
      if(this instanceof UserCandidate){
        Candidate.call(this,candidate);
        this.type.push("person");
        this.name = candidate.name;
        this.url = "profile/" + candidate.id;
        //this.type.push("favorite");
        this.setContainerSpecification(containerSpecificationFactory.create(this.type[0]));
      } else {
        new UserCandidate(candidate);
      }
    }

    UserCandidate.prototype = _.create(Candidate.prototype, {
      'constructor': UserCandidate
    });

    return {
      create: function(candidate) {
        if(candidate.hasOwnProperty("sideMenu")) {
          return new LectureCandidate(candidate);
        }
        if(candidate.hasOwnProperty("email")) {
          return new UserCandidate(candidate);
        }
        return new Candidate(candidate);
      }
    };
  });
