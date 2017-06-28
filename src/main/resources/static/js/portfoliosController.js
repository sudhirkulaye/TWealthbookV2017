(function(){
    'use strict';
    angular
        .module('twealthbookModule')
        .controller('portfoliosController',portfoliosController);

    portfoliosController.$inject = ['$http'];

    function portfoliosController($http){
        var vm = this;
        vm.portfoliosMap = {};
        vm.getAllPortfolios = getAllPortfolios;

        init();

        function init(){
            getAllPortfolios();
        }

        function getAllPortfolios(){
            var url = "/api/getAllPortfolios/";
            var portfoliosPromise = $http.get(url);
            portfoliosPromise.then(function(response){
                vm.portfoliosMap = response.data;
            });
        }

    }
})();