/**
 * 
 */

zenFootDirectives=angular.module('zenFoot.directives',[]);

zenFootDirectives.directive('auto-height',function(){
	return function($scope,$element,$attributes){
		 $element.css('overflow', 'auto');

		    function changeHeight() {
		      var height = window.innerHeight - $attributes.autoHeight;
		      $element.height(height);
		    }

		    $(window).resize(changeHeight);
		    changeHeight();
	};
});



zenFootDirectives.directive('popup',function($http){
	
	$modal = $('<div class="modal hide fade" tabindex="-1"><div class="modal-body"></div></div>')
	$content = $modal.find('.modal-body');
	return function($scope,$element,$attributes){
		$element.click(function(){
			$http.get($attributes.popup).success(function(content){
				$content.html(content);
				$modal.modal('show');
			});
		});
	};
});

zenFootDirectives.directive('markdown',function($sce){
	return {restrict:'E',
			replace:true,
			template: '<div>' +
		      '<textarea ng-model="source"></textarea>' +
		      '<div>result : <div ng-bind-html="markdown"></div></div>' +
		      '</div>',
			scope:{
				source: '=ngModel'
			},
			link:function($scope,$element,$attributes){
				$scope.$watch('source',function(newValue){
					if(newValue){
						$scope.markdown=$sce.trustAsHtml(newValue);
					}
					else{
						$scope.markdown='';
					}
				});
			}
	};
});

zenFootDirectives.directive('coucou',function(){
	return {restrict:'E',
		replace:true,
		templateUrl: 'view/coucou.html'
	};
});


zenFootDirectives.directive('groupeDirective',function(){
    return {
        restrict:'E',
        replace:true,
        template:'view/groupe.html',
        scope:{
            groupe:'=groupe'
        }
    };
})

zenFootDirectives.directive('zenDisable',function(){
    return{
        scope:{
        },
        link:function(scope,element,attrs){
            scope.$watch(attrs.oldmatch,function(oldmatch){
                if(oldmatch) {
                    element.prop('disabled',true)
                }
            });
        }
    }
})


/*
zenFootDirectives.directive('zenToolTip',[function(){
    function link(scope,element,attrs){

    }

    return {

    }
}])
    */