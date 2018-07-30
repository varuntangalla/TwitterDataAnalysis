index.directive("twFeature", function() {
    return {
        restrict: "E",
        scope: {
            icon: "@icon",
            heading: "@heading"
        },
        transclude: true,
        templateUrl: "/assets/javascripts/app/index/directives/feature/feature.html"
    };
});
