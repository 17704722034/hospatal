var vm = new Vue({
    el:'#userdiv',
    data:{
        userlist:[],
        deptids:[],
        dlist:[{postids:[]}],
        entity:{},
        userbean:{},
        pageNum:1,
        pageSize:3,
        page:{}
    },
    methods:{
        getUserList:function () {
            var _this = this;
            axios.get("../user/getUserList.do").then(function (response) {
                _this.userlist = response.data;
            });
        },
        toUserDept:function (id) {
            var _this = this;
            _this.id = id;
            //查出用户已经拥有的部门id和全部部门
            axios.get('../user/getUserDeptById.do?id='+id).then(function (response) {
                _this.entity = response.data;
                _this.dlist = response.data.dlist;
                _this.deptids = response.data.deptids;
                document.getElementById("userdeptdiv").style.display="block";
            });
        },
        saveUserDept:function () {
            var _this = this;
            axios.post("../user/saveUserDept.do?id="+_this.entity.id,_this.deptids).then(function (response) {
                if(response.data.flag){
                    alert(response.data.msg);
                    document.getElementById("userdeptdiv").style.display="none";
                    _this.getUserList();
                }else{
                    alert(response.data.msg)
                }
            })
        },
        getUserListConn:function () {
            var _this = this;
            axios.post("../user/getUserListConn.do?pageNum="+_this.pageNum+"&pageSize="+_this.pageSize,_this.userbean).then(function (response) {
                _this.userlist = response.data.list;
                _this.pageNum = response.data.currentPage;
                _this.pageSize = response.data.pageSize;
                _this.page = response.data;
            });
        },
        pageing:function (pageNum) {
            this.pageNum = pageNum;
            this.getUserListConn();
        },
        toUserPost:function (id) {
            var _this = this;
            axios.get('../user/getUserInfo.do?id='+id).then(function (response) {
                _this.entity = response.data;
                _this.dlist = response.data.dlist;
                document.getElementById("userpostdiv").style.display="block";
            });
        },
        saveUserPost:function () {
            this.entity.dlist = this.dlist;
            var _this = this;
            axios.post("../user/saveUserPost.do",_this.entity).then(function (response) {
                if (response.data.flag){
                    alert(response.data.msg);
                    document.getElementById("userpostdiv").style.display="none";
                }else {
                    alert(response.data/msg);
                }
            });
        }
    }
});
vm.getUserListConn();
