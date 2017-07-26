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
	$scope.historicalHoldingsArray = [];
	$scope.xiirReturnsArray = [];

    $scope.currentPage = 1;
    $scope.pageSize = 10;

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
                    $scope.xiirReturnsArray = new Array($scope.portfolios.length, 1);
                    for (var i=0; i<$scope.portfolios.length; i++){
                        $scope.cashflowArray[i] = [];
                        $scope.holdingsArray[i] = [];
                        $scope.historicalHoldingsArray[i] = [];
                        $scope.xiirReturnsArray[i] = [];
                    }
                } else {
                    $scope.portfolios = [];
                }
            });
    }

    function getSetupDates(){
        if ($scope.dateLastTradingDay == undefined) {
            var url = "/getsetupdates";
            $http.get(urlBase + url).
                then(function (response) {
                    if (response != undefined) {
                        $scope.dateLastTradingDay = response.data["dateToday"];
                    } else {
                        $scope.dateLastTradingDay = undefined;
                    }
                });
        }
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
        getPortfolioXIRRReturns(index);
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
                        $scope.holdings = $filter('orderBy')($scope.holdings,['securityAssetClassId', 'securityAssetSubClassId', 'securityName', 'securityBuyDate']);
                        setChartData(index);
                    } else {
                        $scope.holdingsArray[index] = [];
                        $scope.holdings = [];
                    }
                });
        }
    }

    function setChartData(index){
        var sortedHoldings = $filter('orderBy')($scope.holdings,'securityAssetClassId');
        var map = $filter('groupBy')(sortedHoldings, 'securityAssetClassId');
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
        sortedHoldings = [];
        map = {};
        sortedHoldings = $filter('orderBy')($scope.holdings,['securitySectorName','securityIndustryName']);
        map = $filter('groupBy')(sortedHoldings, 'securityIndustryName');
        $scope.sectorIndustry = [];
        $scope.sectorIndustryData = [];
        for(var sectorIndustry in map){
           $scope.sectorIndustry.push(map[sectorIndustry][0].securitySectorName + "-"+ sectorIndustry);
           $scope.sectorIndustryData.push($filter('number')($scope.getMarketValue(map[sectorIndustry]),0));
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

    function getPortfolioXIRRReturns(index){
        if ($scope.xiirReturnsArray[index][0] != undefined) {
            $scope.xiirReturns = $scope.xiirReturnsArray[index][0];
        } else {
            url = "/getportfolioxirrreturns/"+ $scope.portfolios[index]["clientId"]+"/"+$scope.portfolios[index]["portfolioId"];
            $http.get(urlBase + url).
                then(function(response){
                    if (response != undefined) {
                        $scope.xiirReturnsArray[index][0] = response.data;
                        $scope.xiirReturns = response.data;
                    } else {
                        $scope.xiirReturnsArray[index] = [];
                        $scope.xiirReturns = [];
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

    //$scope.sortColumnHolding = "['securityAssetClassId', 'securityAssetSubClassId', 'securityName', 'securityBuyDate']";
    //$scope.sortColumnHolding = "securityAssetClassId";
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

portfolioModule.filter("formatAnnualizedReturns", ['$filter', function ($filter) {
return function (returns, decimals) {
        if (returns < 9) {
            return $filter('number')((returns-1) * 100, decimals) + '%';
        } else {
            return "> 100%"
        }
    };
}])

portfolioModule.filter('percentageReturns', ['$filter', function ($filter) {
  return function (returns, decimals) {
    return $filter('number')((returns-1) * 100, decimals) + '%';
  };
}])

portfolioModule.filter("securityAssetClass", function () {
    return function (securityAssetClass) {
        switch (portfolioActiveStatus) {
            case 10: return "Cash";
            case 20: return "Fixed Income";
            case 30: return "Fixed Inc and Equity";
            case 40: return "Equity";
            case 50: return "Commodity";
            case 60: return "Real Estate";
            case 70: return "Alternative Investments";
        }
    }
})

portfolioModule.filter("securityAssetSubClass", function () {
    return function (securityAssetSubClass) {
        switch (securityAssetSubClass) {
            case 101010: return "Cash";
            case 101020: return "Cash";
            case 201010: return "Fixed Income - Short Term";
            case 201020: return "Fixed Income - Short Term";
            case 202010: return "Fixed Income - Mid Term";
            case 202020: return "Fixed Income - Mid Term";
            case 203010: return "Fixed Income - Long Term";
            case 201020: return "Fixed Income - Long Term";
            case 201030: return "Fixed Income - Long Term";
            case 201040: return "Fixed Income - Long Term";
            case 201050: return "Fixed Income - Long Term";
            case 301010: return "Fixed Income And Equity";
            case 301020: return "Fixed Income And Equity";
            case 301030: return "Fixed Income And Equity";
            case 401010: return "Equity - Diversified - Large Cap";
            case 401020: return "Equity - Diversified - Large Cap";
            case 401030: return "Equity - Diversified - Large Cap";
            case 401040: return "Equity - Diversified - Large Cap";
            case 401050: return "Equity - Diversified - Large Cap";
            case 401060: return "Equity - Diversified - Large Cap";
            case 401070: return "Equity - Diversified - Large Cap";
            case 401080: return "Equity - Diversified - Large Cap";
            case 402010: return "Equity - Diversified - Mid Cap";
            case 402020: return "Equity - Diversified - Mid Cap";
            case 402030: return "Equity - Diversified - Mid Cap";
            case 402040: return "Equity - Diversified - Mid Cap";
            case 403010: return "Equity - Diversified - Multi Cap";
            case 403020: return "Equity - Diversified - Multi Cap";
            case 403030: return "Equity - Diversified - Multi Cap";
            case 403040: return "Equity - Diversified - Multi Cap";
            case 404010: return "Equity - Diversified - Small Cap";
            case 404020: return "Equity - Diversified - Small Cap";
            case 404030: return "Equity - Diversified - Small Cap";
            case 404040: return "Equity - Diversified - Small Cap";
            case 405010: return "Foreign Equity - Developed Market";
            case 405020: return "Foreign Equity - Developed Market";
            case 405030: return "Foreign Equity - Emerging Market";
            case 405040: return "Foreign Equity - Emerging Market";
            case 406010: return "Individual Equity";
            case 406020: return "Individual Equity";
            case 406030: return "Individual Equity";
            case 406040: return "Individual Equity";
            case 501010: return "Diversified Commodity";
            case 502010: return "Individual Commodity";
            case 502020: return "Individual Commodity";
            case 502030: return "Individual Commodity";
            case 601010: return "Real Estate Direct";
            case 601020: return "Real Estate Direct";
            case 601030: return "Real Estate Direct";
            case 601040: return "Real Estate Direct";
            case 602010: return "Real Estate Indirect";
            case 701010: return "Alternative Investments";
            case 701020: return "Alternative Investments";
            case 701030: return "Alternative Investments";
            case 701040: return "Alternative Investments";
            case 701050: return "Alternative Investments";
            case 701060: return "Alternative Investments";
            case 701070: return "Alternative Investments";
        }
    }
})