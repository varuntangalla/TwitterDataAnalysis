home.directive("twEntity", function() {
    return {
        restrict: "E",
        scope: {
            entity: "=",
            display: "&"
        },
        templateUrl: "/assets/javascripts/app/home/directives/entity/entity.html"
    };
});
