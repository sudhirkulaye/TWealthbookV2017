var portfolioModule = angular.module('portfolioApp', [ ]);


portfolioModule.controller('portfolioController', function ($scope, $http, $filter) {

	var urlBase="/api";
	$http.defaults.headers.post["Content-Type"] = "application/json";
	$scope.getPortfolioCashflow = getPortfolioCashflow;
	$scope.getPortfolioHoldings = getPortfolioHoldings;
	$scope.hideCashflow = true;
	$scope.hideHoldings = true;
	$scope.cashflowArray = [];
	$scope.holdingsArray = [];
	$scope.historicalHoldingsArray = []

	findAllPortfolios();

    function findAllPortfolios() {
        $scope.portfolios = new Array;
        var url = "/getallportfolios";
        var totalPortfolios = 0;
        $http.get(urlBase + url).
            then(function (response) {
                if (response != undefined) {
                    $scope.portfolios = response.data;
                    $scope.cashflowArray = new Array($scope.portfolios.length, 1);
                    $scope.holdingsArray = new Array($scope.portfolios.length, 1);
                    $scope.historicalHoldingsArray = new Array($scope.portfolios.length, 1);
                    for (var i=0; i<$scope.portfolios.length; i++){
                        $scope.cashflowArray[i] = [];
                        $scope.holdingsArray[i] = [];
                        $scope.historicalHoldingsArray[i] = [];
                    }
                } else {
                    $scope.portfolios = [];
                }
            });
    }

    function setPortfolioSelection(index){
        $scope.relationship = $scope.portfolios[index].relationship;
        $scope.portfolioDescription = $scope.portfolios[index].portfolioDescription;
        $scope.portfolioStartDate = $scope.portfolios[index].portfolioStartDate;
        $scope.portfolioActiveStatus = $filter('portfolioActiveStatus')($scope.portfolios[index].portfolioActiveStatus);
        $scope.portfolioCurrentStrategy = $scope.portfolios[index].portfolioCurrentStrategy;
        $scope.portfolioBenchmarkType = $filter('benchmarkType')($scope.portfolios[index].portfolioBenchmarkType);
        $scope.portfolioBenchmark = $scope.portfolios[index].portfolioBenchmark;
        $scope.portfolioValue = $filter('currency')($scope.portfolios[index].portfolioValue, "₹", 0);
    }

    function getPortfolioCashflow(index){
        $scope.hideCashflow = false;
        $scope.hideHoldings = true;
        if ($scope.cashflowArray[index][0] != undefined) {
            $scope.cashflows = $scope.cashflowArray[index][0];
        } else {
            url = "/getportfoliocashflow/"+ $scope.portfolios[index]["clientId"]+"/"+$scope.portfolios[index]["portfolioId"];
            $http.get(urlBase + url).
                then(function(response){
                    if (response != undefined) {
                        $scope.cashflowArray[index][0] = response.data;
                        $scope.cashflows = response.data;
                    } else {
                        $scope.cashflowArray[i] = [];
                        $scope.cashflows = [];
                    }
                });
        }
        setPortfolioSelection(index);
    }

    function getPortfolioHoldings(index){
        $scope.hideHoldings = false;
        $scope.hideCashflow = true;
        if ($scope.holdingsArray[index][0] != undefined) {
            $scope.holdings = $scope.holdingsArray[index][0];
        } else {
            url = "/getportfolioholdings/"+ $scope.portfolios[index]["clientId"]+"/"+$scope.portfolios[index]["portfolioId"];
            $http.get(urlBase + url).
                then(function(response){
                    if (response != undefined) {
                        $scope.holdingsArray[index][0] = response.data;
                        $scope.holdings = response.data;
                    } else {
                        $scope.holdingsArray[i] = [];
                        $scope.holdings = [];
                    }
                });
        }
        setPortfolioSelection(index);
    }

    $scope.searchSecurity = function (holding) {
        if ($scope.searchSecurityText == undefined) {
            return true;
        } else {
            if (holding.portfolioHoldingsKey.securityId.toLowerCase().indexOf($scope.searchSecurityText.toLowerCase()) != -1 ) {
                return true;
            }
        }
        return false;
    }

});

portfolioModule.filter("benchmarkType", function () {
    return function (benchmarkType) {
        switch (benchmarkType) {
            case 1: return "Standard";
            case 2: return "Customized"
        }
    }
})

portfolioModule.filter("portfolioActiveStatus", function () {
    return function (portfolioActiveStatus) {
        switch (portfolioActiveStatus) {
            case 1: return "Active";
            case 2: return "Inactive"
            case 3: return "Inactive and Closed"
        }
    }
})

portfolioModule.filter('percentageReturns', ['$filter', function ($filter) {
  return function (input, decimals) {
    return $filter('number')((input-1) * 100, decimals) + '%';
  };
}])