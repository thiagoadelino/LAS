    // Ionic Starter App

    // angular.module is a global place for creating, registering and retrieving Angular modules
    // 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
    // the 2nd parameter is an array of 'requires'
    var app = angular.module('starter', ['ionic']);

    app.run(function($ionicPlatform) {
      $ionicPlatform.ready(function() {
        if(window.cordova && window.cordova.plugins.Keyboard) {
          // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
          // for form inputs)
          cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);

          // Don't remove this line unless you know what you are doing. It stops the viewport
          // from snapping when text inputs are focused. Ionic handles this internally for
          // a much nicer keyboard experience.
          cordova.plugins.Keyboard.disableScroll(true);
        }
        if(window.StatusBar) {
          StatusBar.styleDefault();
        }
      });
    })

    app.factory('plantasService', function($http) {
      return {
        getPlantas: function(){
          return $http.get('js/db.json').then(function(response){
            console.log("Servico chamado");
            return response.data;
          });
        }
      }
    });

    app.controller("MainCtrl", function($scope, plantasService){
      console.log("Main controller says hello");

      plantasService.getPlantas().then(function(plantas){
          $scope.plantas = plantas;
      });
    });

    app.controller("DetalheCtrl", function($scope, plantasService){
      console.log("Detalhe Controller says: Hello World");
      
    });

    app.config(function($stateProvider, $urlRouterProvider){
        $stateProvider
          .state('main', {
              url: "/main",
              templateUrl: "templates/main.html",
              controller: 'MainCtrl'
          });
        $stateProvider
          .state('view', {
              url: "/view",
              templateUrl: "templates/view.html",
              controller: "DetalheCtrl"
          });
        $urlRouterProvider
          .otherwise('/main');
    });
