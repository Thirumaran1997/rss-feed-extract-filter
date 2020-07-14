'use strict';

App.factory('postService', function($http, $q) {
    return {
       
    	filterData : function(record){
            var config = {
                headers : {
                    'Content-Type': 'application/json',
                    'cityname':record.name,
                    'searchquery':record.searchquery
                }
            }
            return $http.get('filterData',config)
                .then(
                    function(response){
                        return response.data.result;
                    },
                    function(errResponse) {
                        alert(errResponse.status + ':' + errResponse.statusText);
                        return $q.reject(errResponse);
                    });
        },
        
        postData : function(record){
            var config = {
                headers : {
                    'Content-Type': 'application/json',
                    'cityname':record.name
                }
            }
            return $http.post('postData',config,record)
                .then(
                    function(response){
                        return response.data.result;
                    },
                    function(errResponse) {
                        alert(errResponse.status + ':' + errResponse.statusText);
                        return $q.reject(errResponse);
                    });
        }
        
    };
});