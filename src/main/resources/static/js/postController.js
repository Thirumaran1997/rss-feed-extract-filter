'use strict';
App.controller('postController', ['$scope', '$rootScope', 'postService','$http',
		function($scope, $rootScope, postService, $http) {

			$scope.city = {};
			$scope.city.name = "chennai";
			$scope.city.searchquery = "";
			$scope.showLogin = true;
			$scope.showDetails = false;
			$scope.recordDetails = [];
			$scope.loading = false;
        	
			$scope.filterData = function() {
				$scope.loading = true;
				postService.filterData($scope.city).then(
						function(data) {
							$scope.recordDetails = data;
							$scope.showLogin = false;
							$scope.showDetails = true;
							$scope.loading = false;
						});
			};
			
			$scope.postData = function() {
				$scope.loading = true;
				postService.postData($scope.city).then(
						function(data) {
							$scope.loading = false;
							$scope.showLogin = false;
							$scope.filterData($scope.city);
						});
			};
			
		} ]);