home.directive("twStatCompare", function() {
    return {
        restrict: "E",
        scope: {
            stat: "@stat",
            entity1: "=",
            entity2: "="
        },
        templateUrl: "/assets/javascripts/app/home/directives/statcompare/statcompare.html"
    };
});
