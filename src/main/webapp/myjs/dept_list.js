var vm = new Vue({
    el:'#deptdiv',
    data:{
        deptlist:[],
        pageNum:1,
        pageSize:3,
        page:{},
        searchEntity: {},
        plist:[],
        postids:[],
        entity:{}
    },
    methods:{
        getDeptListConn:function () {
            var _this = this;
            axios.post("../dept/getDeptListConn.do?pageNum="+_this.pageNum + "&pageSize=" + _this.pageSize , _this.searchEntity).then(function (response) {
                _this.pageNum = response.data.currentPage;
                _this.page = response.data;
                _this.pageSize = response.data.pageSize;
                _this.deptlist = response.data.list;
            });
        },
        pageing:function (pageNum) {
            this.pageNum = pageNum;
            this.getDeptListConn();
        },
        toDeptPost:function (id) {
            var _this = this;
            document.getElementById("deptpostdiv").style.display="block";
            axios.get('../dept/getDeptById.do?id='+id).then(function (response) {
                _this.entity = response.data;
                _this.postids = response.data.postids;
                _this.plist = response.data.plist;
            });
        },
        saveDeptPost:function () {
            var _this = this;
            axios.post('../dept/saveDeptPost.do?id='+_this.entity.id,_this.postids).then(function (response) {
                if (response.data.flag){
                    alert(response.data.msg);
                    _this.getDeptListConn();
                    document.getElementById("deptpostdiv").style.display="none";
                }else {
                    alert(response.data.msg);
                }
            });
        }
    }
});
vm.getDeptListConn();