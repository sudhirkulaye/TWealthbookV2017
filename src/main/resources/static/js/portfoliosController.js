var portfolioModule = angular.module('portfolioApp', ['angular.filter', 'angularUtils.directives.dirPagination', 'chart.js']);

portfolioModule.controller('OtherController', function ($scope) {
        $scope.pageChangeHandler = function(num) {
        //console.log('going to page ' + num);
    };
});

portfolioModule.controller('portfolioController', function ($scope, $http, $filter, $locale) {

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
        $scope.portfolioValueNumber = $scope.portfolios[index].portfolioValue;
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
            setChartData(index);
        } else {
            url = "/getportfolioholdings/"+ $scope.portfolios[index]["clientId"]+"/"+$scope.portfolios[index]["portfolioId"];
            $http.get(urlBase + url).
                then(function(response){
                    if (response != undefined) {
                        $scope.holdingsArray[index][0] = response.data;
                        $scope.holdings = response.data;
                        setChartData(index);
                    } else {
                        $scope.holdingsArray[index] = [];
                        $scope.holdings = [];
                    }
                });
        }
    }

    function setChartData(index){
        var sortedHoldings = $filter('orderBy')($scope.holdings,'securityAssetClass');
        var map = $filter('groupBy')(sortedHoldings, 'securityAssetClass');
        $scope.assetClass = [];
        $scope.assetClassData = [];
        $locale.NUMBER_FORMATS.GROUP_SEP = '';
        for(var assetClass in map){
           $scope.assetClass.push(assetClass);
           $scope.assetClassData.push($filter('number')($scope.getMarketValue(map[assetClass]),0));
        }
        sortedHoldings = [];
        map = {};
        sortedHoldings = $filter('orderBy')($scope.holdings,['securityAssetClass','securityAssetSubClass']);
        map = $filter('groupBy')(sortedHoldings, 'securityAssetSubClass');
        $scope.assetSubClass = [];
        $scope.assetSubClassData = [];
        for(var assetSubClass in map){
           $scope.assetSubClass.push(map[assetSubClass][0].securityAssetClass + "-"+ assetSubClass);
           $scope.assetSubClassData.push($filter('number')($scope.getMarketValue(map[assetSubClass]),0));
        }

        $locale.NUMBER_FORMATS.GROUP_SEP = ',';
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

    $scope.searchHistoricalSecurity = function (historicalHolding) {
        if ($scope.searchHistoricalSecurityText == undefined) {
            return true;
        } else {
            if (historicalHolding.securityName.toLowerCase().indexOf($scope.searchHistoricalSecurityText.toLowerCase()) != -1 ) {
                return true;
            }
        }
        return false;
    }

    //$scope.sortColumnHolding = "['securityAssetClass', 'securityAssetSubClass', 'securityName', 'securityBuyDate']";
    $scope.sortColumnHolding = "securityAssetClass";
    $scope.reverseSortHolding = false;
    $scope.sortDataHolding = function (column) {
        $scope.reverseSortHolding = ($scope.sortColumnHolding == column) ? !$scope.reverseSortHolding : false;
        $scope.sortColumnHolding = column;
    }
    $scope.getSortClassHolding = function (column) {
        if ($scope.sortColumnHolding == column) {
            return $scope.reverseSortHolding ? 'fa fa-sort-up fa-fw' : 'fa fa-sort-down fa-fw'
        }
        return '';
    }

    $scope.sortColumnHistoricalHolding = "portfolioHistoricalHoldingsKey.securitySellDate";
    $scope.reverseSortHistoricalHolding = true;
    $scope.sortDataHistoricalHolding = function (column) {
        $scope.reverseSortHistoricalHolding = ($scope.sortColumnHistoricalHolding == column) ? !$scope.reverseSortHistoricalHolding : false;
        $scope.sortColumnHistoricalHolding = column;
    }
    $scope.getSortClassHistoricalHolding = function (column) {
        if ($scope.sortColumnHistoricalHolding == column) {
            return $scope.reverseSortHistoricalHolding ? 'fa fa-sort-up fa-fw' : 'fa fa-sort-down fa-fw'
        }
        return '';
    }

    $scope.getMarketValue = function(holdings) {
        return holdings
        .map(function(x) { return x.securityMarketValue; })
        .reduce(function(a, b) { return a + b; });
    }

    $scope.pageChangeHandler = function(num) {
        //console.log('page changed to ' + num);
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