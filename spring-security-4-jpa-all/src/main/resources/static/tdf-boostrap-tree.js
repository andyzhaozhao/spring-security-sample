
//重构节点方法 菜单对象转换 数组
   function walk(nodes, data) {
          if (!nodes) { return; }
          $.each(nodes, function (id, node) {
            var obj = {
              id: node.id,
              text: node.menuName
            };
            if (node.children.length > 0) {
            	//说明是父节点
              obj.nodes = [];
              walk(node.children, obj.nodes);
            }
            data.push(obj);
          });
          
          return data;
   };	
   
 //重构节点方法 菜单对象转换 数组
   function walkDataItem(nodes, data) {
          if (!nodes) { return; }
          $.each(nodes, function (id, node) {
            var obj = {
              id: node.id,
              text: node.codeName
            };
            if (node.children.length > 0) {
            	//说明是父节点
              obj.nodes = [];
              walkDataItem(node.children, obj.nodes);
            }
            if(node.flag=="1"){
            	data.push(obj);
            }
          });
          
          return data;
   };	   
   //重构节点方法 菜单对象转换 数组
   function walkDept(nodes, data) {
          if (!nodes) { return; }
          $.each(nodes, function (id, node) {
            var obj = {
              id: node.id,
              text: node.deptName
            };
            if (node.children.length > 0) {
            	//说明是父节点
              obj.nodes = [];
              walkDept(node.children, obj.nodes);
            }
            if(node.deptState=="1"){
            	data.push(obj);
            }
          });
          
          return data;
   };		
/* 初始化栏目树*/
	function treeViewInit(id,data) {
			$('#'+id)
					.treeview(
							{
								data : data, // data is not optional
								levels : 3,
								multiSelect : false,
								onNodeSelected : function(event, data) {
								//	console.log(data);
									//子节点删除	
									if (data.nodes === undefined
											|| data.nodes === null) {
										// $("#FlatTree3").treeview("deleteNode", [data.nodeId, { silent: true } ]); 

									} else {
										//alert("我是父节点");
										//return;
									}
									//加载信息列表
									var id = data.id;
									var name =data.text;
									 //加载选中项
								    //alert(id+"<---------->"+name);
									//选中的节点 
									onCheckParentValue(id, name);
								},
								onNodeUnselected : function(event, data) {
									var id = data.id;
									var name =data.text;
									resetParentValue();
								}	

							});
			
			
		}	
	
	
$.fn.serializeObject = function () {
            var obj = {};
            var count = 0;
            $.each(this.serializeArray(), function (i, o) {
                var n = o.name, v = o.value;
                count++;
                obj[n] = obj[n] === undefined ? v
                : $.isArray(obj[n]) ? obj[n].concat(v)
                : [obj[n], v];
            });
            return obj;
        };
    	//为翻页保留checkbox选中准备
    	var overAllIds = new Array();  //全局数组
    	function examine(type,datas){       
    	    if(type.indexOf('uncheck')==-1){   
    	        $.each(datas,function(i,v){
    	           // 添加时，判断一行或多行的 id 是否已经在数组里 不存则添加　
    		　　　　　overAllIds.indexOf(v.id) == -1 ? overAllIds.push(v.id) : -1;
    			});
    	    }else{ 
    	        $.each(datas,function(i,v){
    	            overAllIds.splice(overAllIds.indexOf(v.id),1);    //删除取消选中行
    	        });
    	    }
    	    //console.log(overAllIds);
    	}
     