var portfolioModule = angular.module('portfolioApp', ['angularUtils.directives.dirPagination']);

portfolioModule.controller('OtherController', function ($scope) {
        $scope.pageChangeHandler = function(num) {
        console.log('going to page ' + num);
    };
});

portfolioModule.controller('portfolioController', function ($scope, $http, $filter) {

	var urlBase="/api";
	$http.defaults.headers.post["Content-Type"] = "application/json";
	$scope.showDetails = showDetails;
	$scope.hideTabs = true;

	$scope.cashflowArray = [];

	$scope.holdingsArray = [];
    $scope.currentPage = 1;
    $scope.pageSize = 10;

	$scope.historicalHoldingsArray = [];

	findAllPortfolios();

    function findAllPortfolios() {
        getSetupDates();
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

    function getSetupDates(){
        var url = "/getsetupdates";
        $http.get(urlBase + url).
            then(function (response) {
                if (response != undefined) {
                    $scope.dateLastTradingDay = response.data["dateLastTradingDay"];
                } else {
                    $scope.dateLastTradingDay = undefined;
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

    function showDetails(index){
        $scope.hideTabs = false;
        setPortfolioSelection(index);
        getPortfolioCashflow(index);
        getPortfolioHoldings(index);
        getPortfolioHistoricalHoldings(index);
    }

    function getPortfolioCashflow(index){

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
                        $scope.cashflowArray[index] = [];
                        $scope.cashflows = [];
                    }
                });
        }
    }

    function getPortfolioHoldings(index){

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
                        $scope.holdingsArray[index] = [];
                        $scope.holdings = [];
                    }
                });
        }
    }

    function getPortfolioHistoricalHoldings(index){

        if ($scope.historicalHoldingsArray[index][0] != undefined) {
            $scope.historicalHoldings = $scope.historicalHoldingsArray[index][0];
        } else {
            url = "/getportfoliohistoricalholdings/"+ $scope.portfolios[index]["clientId"]+"/"+$scope.portfolios[index]["portfolioId"];
            $http.get(urlBase + url).
                then(function(response){
                    if (response != undefined) {
                        $scope.historicalHoldingsArray[index][0] = response.data;
                        $scope.historicalHoldings = response.data;
                    } else {
                        $scope.historicalHoldingsArray[index] = [];
                        $scope.historicalHoldings = [];
                    }
                });
        }
    }

    $scope.searchSecurity = function (holding) {
        if ($scope.searchSecurityText == undefined) {
            return true;
        } else {
            if (holding.securityName.toLowerCase().indexOf($scope.searchSecurityText.toLowerCase()) != -1 ) {
                return true;
            }
        }
        return false;
    }

    $scope.searchHistoricalSecurity = function (holding) {
            if ($scope.searchHistoricalSecurityText == undefined) {
                return true;
            } else {
                if (historicalHolding.securityName.toLowerCase().indexOf($scope.searchHistoricalSecurityText.toLowerCase()) != -1 ) {
                    return true;
                }
            }
            return false;
        }

    $scope.pageChangeHandler = function(num) {
        console.log('page changed to ' + num);
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