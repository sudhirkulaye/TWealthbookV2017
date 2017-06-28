var portfolioModule = angular.module('portfolioApp', [ ]);


portfolioModule.controller('portfolioController', function ($scope,$http) {

	var urlBase="";
	$http.defaults.headers.post["Content-Type"] = "application/json";

    function findAllPortfolios() {
        //get all tasks and display initially
        $http.get(urlBase + '/api/getallportfolios').
            then(function (response) {
                if (response != undefined) {
                    $scope.portfolios = response.data;
                } else {
                    $scope.portfolios = [];
                }
            });
    }

    findAllPortfolios();

});