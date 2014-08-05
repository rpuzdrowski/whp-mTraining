(function () {
    'use strict';

    /* Services */

    var services = angular.module("mtraining.services", []);

    services.service('fileUpload', function ($http) {
        this.uploadFileToUrl = function (multipartFile, uploadUrl, successCallback, errorCallback) {
            var fd = new FormData();
            fd.append('multipartFile', multipartFile);
            $http.post(uploadUrl, fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            }).success(function (data) {
                    successCallback(data)
                })
                .error(function (data) {
                    errorCallback(data)
                });
        }
    });

    services.factory('Course', function($resource) {
         return $resource('/motech-platform-server/module/mtraining/web-api/course/:id', { id: '@_id' }, {
          update: {
            method: 'PUT'
          }
        });
     });

}());
