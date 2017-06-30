var portfolioModule = angular.module('portfolioApp', [ ]);


portfolioModule.controller('portfolioController', function ($scope,$http) {

	var urlBase="/api";
	$http.defaults.headers.post["Content-Type"] = "application/json";
	$scope.getPortfolioCashflow = getPortfolioCashflow;
	$scope.getPortfolioHoldings = getPortfolioHoldings;
	$scope.hideCashflow = true;
	$scope.hideHoldings = true;

	findAllPortfolios();

    function findAllPortfolios() {
        $scope.portfolios = [];
        var url = "/getallportfolios";
        $http.get(urlBase + url).
            then(function (response) {
                if (response != undefined) {
                    $scope.portfolios = response.data;
                } else {
                    $scope.portfolios = [];
                }
            });
    }

    function getPortfolioCashflow(clientId, portfolioId){
        var url = "/getportfoliocashflow/"+clientId+"/"+portfolioId;
        $scope.portfolioCashflows = [];
        $scope.hideCashflow = false;
        $scope.hideHoldings = true;
        $http.get(urlBase + url).
            then(function(response){
                if (response != undefined) {
                    $scope.portfolioCashflows = response.data;
                } else {
                    $scope.portfolioCashflows = [];
                }
            });
    }

    function getPortfolioHoldings(clientId, portfolioId){
        var url = "/getportfolioholdings/"+clientId+"/"+portfolioId;
        $scope.portfolioHoldings = [];
        $scope.hideHoldings = false;
        $scope.hideCashflow = true;
        $http.get(urlBase + url).
            then(function(response){
                if (response != undefined) {
                    $scope.portfolioHoldings = response.data;
                } else {
                    $scope.portfolioHoldings = [];
                }
            });
    }

});

portfolioModule.filter("benchmarkType", function () {
    return function (gender) {
        switch (gender) {
            case 1: return "Standard";
            case 2: return "Customized"
        }
    }
})