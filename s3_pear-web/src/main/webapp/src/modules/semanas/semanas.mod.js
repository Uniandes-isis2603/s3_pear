(function (ng) {

	var mod = ng.module("semanaModule", ['ui.router']);

	mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

		$urlRouterProvider.otherwise("/dietas");

		$stateProvider
			.state('semanas', {
				url: "/semanas",
				templateUrl: "src/modules/semanas/semanas.html",
				controller: 'semanasController'
            
			}).state('semanas.detail', {
				url: "/semanas/detail",
				templateUrl: "src/modules/dietas/semana_detail.html",
				controller: 'semanasController'
			}).state('post_semana', {
                        url: '/dietas/semana',
                        views: {
                            'post': {
                                templateUrl: 'src/modules/semana/semana_add.html',
                                controller: 'semanasController'
                            }
                        }
                    })
    }]);
})(window.angular);


(function (ng) {

	var mod = ng.module("semanaModule");
	mod.constant("semanaContext", "api/semanas");
        
        mod.filter('range', function () {
        return function (input, total) {
            total = parseInt(total);
            for (var i = 0; i < total; i++)
                input.push(i);
            return input;
        };
    });


	mod.controller('semanasController', ['$scope', '$http', 'semanaContext','$state',

        function ($scope, $http, semanaContext, $state) {
            
            //semanaContext
           
           $http.get( 'http://localhost:8080/s3_pear-web/api/semanas').then(function (response){
				$scope.semanas = response.data;
			}).then(function (response) {
                console.log('FAILED GET SEMANAS' + response);
            })
            ;
            
                 if ($state.params.id !== null && $state.params.id !== undefined) {
                $scope.id_semana = $state.params.id;

                $http.get('http://localhost:8080/s3_pear-web/api/semanas/' + $state.params.id).then(function (response) {
                    $scope.semana = response.data;
                });
            }
            
              $scope.enviar_semana = function (){
                let data = {
                    seEnvia: $scope.seEnvia,
                    recomendacion: $scope.recomendacion,
                    fecha: $scope.fecha
                };
                
                console.log(data);
                
              $http.post('http://localhost:8080/s3_pear-web/api/semanas', data).then(function (response) {
                    $scope.post_data = response.data;
                    $state.go('facturas', {}, {reload: true});
                });
            };
            
            $scope.eliminar_semana = function (id) {

                console.log(id + " < Se va a eliminar la semana");

               
                $http.delete('http://localhost:8080/s3_pear-web/api/semanas/' + id).then(function (response) {
                    $scope.delete_data = response.data;
                    $state.reload();
                });
            };
            
             $scope.actualizar_semana = function () {
                console.log($scope.id_dieta + " < Se va a actualizar la semana.");

                let data = {
                    name: $scope.new_nombre,
                    descripcion: $scope.new_descripcion,
                    objetivo: $scope.new_objetivo,
                    stars: $scope.new_stars,
                    imagen: $scope.new_imagen
                };
                
                //http://localhost:8080/s3_pear-web/api/dietas/3
                $http.put('http://localhost:8080/s3_pear-web/api/semanas/' + $scope.id_semana, data).then(function (response) {
                    $scope.put_data = response.data;
                    $state.go($state.current, {}, {reload: true});
                    $state.go('semanas', {}, {reload: true});
                });
            };
        }
    ]);
})(window.angular);

