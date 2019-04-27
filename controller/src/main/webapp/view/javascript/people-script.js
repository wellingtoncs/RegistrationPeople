var app = angular.module("RegistrationPeople", ['angular-loading-bar']);
app.controller('PeopleController', function($scope, $http){
	
	var peopleService = "/controller/rest/pessoas";
	var saveService = "/controller/rest/pessoa/save";
	var peopleDetailsService = "/controller/rest/pessoa/";
	var deleteService = "/controller/rest/pessoa/remove/";
	
	$scope.statusMsg = " Suas mensagens serão exibidas aqui.";
	
	//ajax get all
	$http.get(peopleService)
		.success(function (data, status, headers, config){
			$scope.peopleList = data;
		})
		.error(function (data, status, headers, config){
			$scope.statusMsg = "Problemas ao carregar a lista de pessoas.";
			$scope.style = "alert-danger";
		});
	
	//ajax get details 
	$scope.peopleDetails = function(id){
		$http.get(peopleDetailsService + id)
		.success(function (data, status, headers, config){
			$scope.people = data;
		})
		.error(function (data, status, headers, config){
			$scope.statusMsg = "Probemas ao carregar os detalhes do cadastro.";
			$scope.style = "alert-danger";
			return;
		});
		
		$scope.statusMsg = "Detalhes carregado com sucesso!";
		$scope.style = "alert-success";
	};
	
	//save.
	$scope.savePeople = function(people){
		var index = -1;
		
		//if updating people
		if(people.id != null){
			for(var i = 0; i < $scope.peopleList.length; i++){
				if($scope.peopleList[i].id == people.id){
					index = i;
					break;
				}
			}
		} 
		
		$http.post(saveService, people)
			.success(function (data, status, headers, config){
				if(index < 0){ //add list
					$scope.peopleList.push(data);
				} else { //updated list
					$scope.peopleList[index] = data;			
				}
				
				$scope.newPeople();
				$scope.statusMsg = "Novo cadastro adicionado com sucesso!";
				$scope.style = "alert-success";
			})
			.error(function (data, status, headers, config){
				$scope.statusMsg = "Houve problema ao adicionar cadastro.";
				$scope.style = "alert-danger";
				return;
			});
		
	};
	
	//clean
	$scope.newPeople = function(){
		$scope.people = new Object();
		$scope.statusMsg = "Tudo limpo, pode começar a cadastrar um novo registro.";
		$scope.style = "alert-success";
	}
	
	//remove 
	$scope.removePeople = function(id){
		var index = -1;		
		//if updating people
		if(id != null){
			for(var i = 0; i < $scope.peopleList.length; i++){
				if($scope.peopleList[i].id == id){
					index = i;
					break;
				}
			}
		}		
		$http.get(deleteService + id)
			.success(function (data, status, headers, config){
				$scope.statusMsg = "Cadastro excluído com sucesso!";
				$scope.style = "alert-success";
				$scope.people = new Object();
				if(index >= 0){
					$scope.peopleList.splice(index, 1);
				}
			})
			.error(function (data, status, headers, config){
				$scope.statusMsg = "Probemas ao excluir cadastro.";
				$scope.style = "alert-danger";
				return;
			});
		
		$scope.reset();
	}
});