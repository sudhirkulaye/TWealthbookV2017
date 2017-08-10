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
	$scope.irrArray = [];
	$scope.twrrArray = [];

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
                    $scope.irrArray = new Array($scope.portfolios.length, 1);
                    $scope.twrrArray = new Array($scope.portfolios.length, 1);
                    for (var i=0; i<$scope.portfolios.length; i++){
                        $scope.cashflowArray[i] = [];
                        $scope.holdingsArray[i] = [];
                        $scope.historicalHoldingsArray[i] = [];
                        $scope.irrArray[i] = [];
                        $scope.twrrArray[i] = [];
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
        //$scope.portfolioBenchmarkType = $filter('benchmarkType')($scope.portfolios[index].portfolioBenchmarkType);
        $scope.portfolioBenchmarkDescription = $scope.portfolios[index].portfolioBenchmarkDescription;
        $scope.portfolioValue = $filter('currency')($scope.portfolios[index].portfolioValue, "₹", 0);
        $scope.portfolioValueNumber = $scope.portfolios[index].portfolioValue;
    }

    function showDetails(index){
        $scope.hideTabs = false;
        setPortfolioSelection(index);
        getPortfolioCashflow(index);
        getPortfolioHoldings(index);
        getPortfolioHistoricalHoldings(index);
        getPortfolioInternalRateOfReturns(index);
        getPortfolioTimeWeightedRateOfReturns(index);
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
        for(var assetClassKey in map){
           $scope.assetClass.push($filter('securityAssetClass')(parseInt(assetClassKey)));
           $scope.assetClassData.push($filter('number')($scope.getMarketValue(map[assetClassKey]),0));
        }
        sortedHoldings = [];
        map = {};
        sortedHoldings = $filter('orderBy')($scope.holdings,['securityAssetClassId','securityAssetSubClassId']);
        map = $filter('groupBy')(sortedHoldings, 'securityAssetSubClassId');
        $scope.assetSubClass = [];
        $scope.assetSubClassData = [];
        for(var assetSubClassKey in map){
           $scope.assetSubClass.push($filter('securityAssetClass')(map[assetSubClassKey][0].securityAssetClassId) + "-"+ $filter('securityAssetClass')(assetSubClassKey));
           $scope.assetSubClassData.push($filter('number')($scope.getMarketValue(map[assetSubClassKey]),0));
        }
        sortedHoldings = [];
        map = {};
        sortedHoldings = $filter('orderBy')($scope.holdings,['securitySectorName','securityIndustryName']);
        sortedHoldings = $filter('filter')(sortedHoldings, { securityAssetClassId: "40" });
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

    function getPortfolioInternalRateOfReturns(index){
        if ($scope.irrArray[index][0] != undefined) {
            $scope.irr = $scope.irrArray[index][0];
        } else {
            url = "/getportfoliointernalrateofreturns/"+ $scope.portfolios[index]["clientId"]+"/"+$scope.portfolios[index]["portfolioId"];
            $http.get(urlBase + url).
                then(function(response){
                    if (response != undefined) {
                        $scope.irrArray[index][0] = response.data;
                        $scope.irr = response.data;
                    } else {
                        $scope.irrArray[index] = [];
                        $scope.irr = [];
                    }
                });
        }
    }

    function getPortfolioTimeWeightedRateOfReturns(index){
         if ($scope.twrrArray[index][0] != undefined) {
                    $scope.twrr = $scope.twrrArray[index][0];
         } else {
            url = "/getportfoliotimeweightedrateofreturns/"+ $scope.portfolios[index]["clientId"]+"/"+$scope.portfolios[index]["portfolioId"];
            $http.get(urlBase + url).
                then(function(response){
                    if (response != undefined) {
                        $scope.twrrArray[index][0] = response.data;
                        $scope.twrr = response.data;
                    } else {
                        $scope.twrrArray[index] = [];
                        $scope.twrr = [];
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
    //console.log(returns);
    if (returns == 0 || returns == undefined)  return "0%";
    return $filter('number')((returns-1) * 100, decimals) + '%';
  };
}])

portfolioModule.filter("securityAssetClass", function () {
    return function (securityAssetClassId) {
        securityAssetClassId = parseInt(securityAssetClassId);
        switch (securityAssetClassId) {
            case 10: return "Cash";
            case 20: return "Fixed Inc";
            case 30: return "Fixed Inc & Equity";
            case 40: return "Equity";
            case 50: return "Commodity";
            case 60: return "Real Estate";
            case 70: return "Alternative Investments";
            case 101010: return "Without Interest";
            case 101020: return "Bank Account";
            case 201010: return "Short Term FD(<6M)";
            case 201020: return "Short Term Debt MF";
            case 202010: return "Mid Term FD(6M-3Y)"
            case 202020: return "Mid Term Debt MF";
            case 203010: return "Long Term FD(>3Yrs)";
            case 203020: return "Long Term PPF-NSE";
            case 203030: return "Long Term Bonds/Pref Stocks";
            case 203040: return "Long Term Debt MF";
            case 203050: return "Long Term Insurance";
            case 301010: return "Debt Oriented Hybrid MF";
            case 301020: return "Equity Oriented Hybrid MF";
            case 301030: return "ULIP-Pension Funds";
            case 401010: return "Diversified LargeCap - ETF Nifty";
            case 401020: return "Diversified LargeCap - ETF Bank";
            case 401030: return "Diversified LargeCap - ETF Junior";
            case 401040: return "Diversified LargeCap - ETF Other";
            case 401050: return "Diversified LargeCap - MF Value";
            case 401060: return "Diversified LargeCap - MF Growth";
            case 401070: return "Diversified LargeCap - MF";
            case 401080: return "Diversified LargeCap - MF ELSS";
            case 402010: return "Diversified MidCap - ETF";
            case 402020: return "Diversified MidCap - MF Value";
            case 402030: return "Diversified MidCap - MF Growth";
            case 402040: return "Diversified MidCap - MF";
            case 403010: return "Diversified MultiCap - ETF";
            case 403020: return "Diversified MultiCap - MF Value";
            case 403030: return "Diversified MultiCap - MF Growth";
            case 403040: return "Diversified MultiCap - MF";
            case 404010: return "Diversified SmallCap - ETF";
            case 404020: return "Diversified SmallCap - MF Value";
            case 404030: return "Diversified SmallCap - MF Growth";
            case 404040: return "Diversified SmallCap - MF";
            case 405010: return "Foreign Developed Market - Diversified LargeCap";
            case 405020: return "Foreign Developed Market - Diversified SmallCap";
            case 405030: return "Foreign Emerging  Market - Diversified LargeCap";
            case 405040: return "Foreign Emerging  Market - Diversified SmallCap";
            case 406010: return "Individual - Large Cap";
            case 406020: return "Individual - Mid Cap";
            case 406030: return "Individual - Small Cap";
            case 406040: return "Individual - Micro Cap";
            case 501010: return "Diversified Commodity MF";
            case 502010: return "Individual - Gold";
            case 502020: return "Individual - Silver";
            case 502030: return "Individual - Other Commodity";
            case 601010: return "Direct - Residential Rental";
            case 601020: return "Direct - Commercial Rental";
            case 601030: return "Direct - Agricultural Land";
            case 601040: return "Direct - Non-Agricultural Land";
            case 602010: return "Indirect - REIT MF";
            case 701010: return "Hedge Fund or PMS";
            case 701020: return "Trading Portfolio, Managed Futures";
            case 701030: return "Private Equity";
            case 701040: return "Venture Capital";
            case 701050: return "Distressed Securities";
            case 701060: return "Artifacts Or Coins";
            case 701070: return "Any other Investment";

        }
    }
})
