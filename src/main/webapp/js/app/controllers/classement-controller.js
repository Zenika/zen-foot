/**
 * Created by raphael on 20/05/14.
 */
var zenfootModule=angular.module('zenFoot.app');

zenfootModule.controller('ClassementCtrl',['$scope',function($scope){

    var classementFunc=function(){
        var ranking= [
            {email:"raphael.martignoni@zenika.com", nom:"Martignoni",prenom:"Raphaël",points:40},
            {email:"bertrand.bouchard@zenika.com", nom:"Bouchard",prenom:"Bertrand",points:140},
            {email:"jean-claude.duss@zenika.com", nom:"Duss",prenom:"Jean-Claude",points:44},
            {email:"richard.virenque@zenika.com", nom:"Virenque",prenom:"Richard",points:45},
            {email:"olivier.martinez@zenika.com", nom:"Martinez",prenom:"Olivier",points:50},
            {email:"mira.sorvino@zenika.com", nom:"Sorvino",prenom:"Mira",points:90},
            {email:"kate.winslet@zenika.com", nom:"Winslet",prenom:"Kate",points:60},
            {email:"leonardo.dicaprio@zenika.com", nom:"Di-Caprio",prenom:"Leonardo",points:52},
            {email:"russel.crowe@zenika.com", nom:"Crowe",prenom:"Russell",points:49},
            {email:"andy.mac-dowell@zenika.com", nom:"Mac-Dowell",prenom:"Andy",points:83},
            {email:"bart.simson@zenika.com", nom:"Murray",prenom:"Bill",points:51},
            {email:"harold.ramis@zenika.com", nom:"Ramis",prenom:"Harold",points:69},
            {email:"sophie.marceau@zenika.com", nom:"Marceau",prenom:"Sophie",points:78}
        ]
         var rankingSorted= _.sortBy(ranking,function(peopleRanking){
         return -peopleRanking.points;
         })

        for(var x in rankingSorted){
            rankingSorted[x].classement=parseInt(x)+1;
        }
        return rankingSorted;
    }
    $scope.classement= classementFunc()




    /**
     * The ranking is obtained, once the list is ordered by descending points, by the index of the entity
     * @param row
     * @returns {*}
     */
    $scope.getClassement=function(row){return row+1;}

    var classementTemplate = '<div>{{getClassement(row.rowIndex)}}</div>'
    var parieurTemplate = '<div>{{row.entity.prenom}} {{row.entity.nom}}</div>'


    $scope.totalServerItems=0;
    $scope.pagingOptions = {
        pageSizes: [10,20],
        pageSize: 10,
        currentPage: 1
    };

    $scope.gridOptions={
        data:'pageData',
        columnDefs: [
            {field:'classement',displayName:'classement'},
            {displayName:'parieur',cellTemplate:parieurTemplate},
            {field:'points',displayName:'points'}
        ],
        enablePaging: true,
        showFooter:true,
        totalServerItems:'totalServerItems',
        pagingOptions: $scope.pagingOptions
    }




    $scope.setPagingData=function(data,page,pageSize){
        setTimeout(function(){

            $scope.totalServerItems=data.length;
        var pageData=data.slice((page-1)*pageSize,page*pageSize)
        $scope.pageData=pageData;
        var bool = scopeApplied=false;
            $scope.$apply()
        },100)

    }

    $scope.setPagingData($scope.classement,$scope.pagingOptions.currentPage,$scope.pagingOptions.pageSize);

    var watchAction=function(){
        $scope.setPagingData($scope.classement,$scope.pagingOptions.currentPage,$scope.pagingOptions.pageSize);
    }

    $scope.$watch('pagingOptions.currentPage',function(newVal,oldVal){
        if(newVal!=oldVal){
            watchAction()
        }
    })

    $scope.$watch('pagingOptions.pageSize',function(newVal,oldVal){
        if(newVal!=oldVal){
            watchAction()
        }
    })
}])