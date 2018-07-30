home.directive("twChart", function() {
    return {
        restrict: "E",
        scope: {
            id: "@chartId",
            height: "@height",
            width: "@width",
            data: "=",
            type: "@type",
            xkey: "@xkey",
            ykey: "@ykey",
            label: "@label"
        },
        link: function(scope) {
            var _drawGeoChart = function() {
                var options = {
                    displayMode: "markers",
                    magnifyingGlass: {enable: false},
                    legend: "none",
                    tooltip: {trigger: "none"},
                    sizeAxis: {minValue: 0, maxValue: 100}
                };
                var dataTable = googleVisualization.arrayToDataTable(scope.data);
                var chart =  new googleVisualization.GeoChart(document.getElementById(scope.id));
                chart.draw(dataTable, options);
            };

            var drawGeoChart = function() {
                if(!googleVisualization)
                    setTimeout(drawGeoChart, 1000);
                else
                    setTimeout(_drawGeoChart, 1000);
            };

            var drawLineChart = function() {
                if($("#entities-tab-selector").hasClass("active"))
                    return;

                setTimeout(function() {
                    if(!scope.chart) {
                        var keys = Object.keys(scope.data[0]);
                        scope.chart = new Morris.Line({
                            element: scope.id,
                            data: scope.data,
                            xkey: scope.xkey,
                            ykeys: [scope.ykey],
                            labels: [scope.label]
                        });
                    }
                    else
                        scope.chart.setData(scope.data);
                }, 1000);
            };

            scope.$watch("data", function() {
                if(!scope.data || !scope.data.length || scope.data.length <= 2) {
                    $("#" + scope.id + "-message").html("Not enough data.");
                    $("#" + scope.id).addClass("hidden");
                    return;
                }
                else {
                    $("#" + scope.id + "-message").html("");
                    $("#" + scope.id).removeClass("hidden");
                }

                if(scope.type == "line")
                    drawLineChart();
                else if(scope.type == "geo")
                    drawGeoChart();
            });
        },
        templateUrl: "/assets/javascripts/app/home/directives/chart/chart.html"
    }
});
