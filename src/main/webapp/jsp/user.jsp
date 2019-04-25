<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
<script>
    /*goeast*/
    /*function addPublish(){
        $.ajax({
            url:"$ {pageContext.request.contextPath}/user/selectCount",
            success:function (data) {
                goEasy.publish({
                channel:'cmfz',
                massage:date
            });
            }
        })

    }*/
    $(function () {
        $("#dg_user").edatagrid({
            url: '${pageContext.request.contextPath}/user/selectAll',
            updateUrl: '${pageContext.request.contextPath}/user/update',
            destroyUrl: '${pageContext.request.contextPath}/user/delete',
            saveUrl: '${pageContext.request.contextPath}/user/update',
            fit: true,
            fitColumns: true,
            pagination: true,
            pageSize: 2,
            pageList: [2, 4, 6, 8, 10],
            columns: [[
                {field: 'name', title: '姓名', width: 100},
                {field: 'province', title: '省份', width: 100},
                {field: 'city', title: '城市', width: 100},
                {field: 'sex', title: '性别', width: 100},
                {field: 'phone', title: '电话', width: 100},
                {field: 'sign', title: '个签', width: 100},
                {
                    field: 'status', title: '状态', width: 100, editor: {
                        type: 'text',
                        options: {required: true}
                    }
                },
                {field: 'time', title: '添加时间', width: 100}
            ]],
            toolbar: [{
                iconCls: 'icon-add',
                text: '添加',
                handler: function () {
                    $("#dd_user").dialog("open")
                }
            }, '-', {
                iconCls: 'icon-edit',
                text: '修改',
                handler: function () {
                    var selectedRow = $("#dg_user").datagrid("getSelected");
                    if (selectedRow == null) {
                        $.messager.alert('提示消息', '请选择要修改项');
                        return;
                    }
                    $("#dd_update").dialog('open');
                    //带上原数据
                    $("#ff_update").form('load', selectedRow)
                    //alert('帮助按钮')
                }
            }, '-', {
                iconCls: 'icon-delete',
                text: '删除',
                handler: function () {
                    $('#dg_user').edatagrid('destroyRow');
                }
            }, '-', {
                iconCls: 'icon-save',
                text: '保存',
                handler: function () {/*alert('保存按钮')*/
                    $("#dg_user").edatagrid("saveRow");
                }
            }/*, '-', {
                    iconCls: 'icon-save',
                    text: '导出表格',
                    handler: function () {/!*alert('保存按钮')*!/
                        $.ajax({
                            url:"${pageContext.request.contextPath}/user/xsl"
                        })
                    }
                }*/],
            view: detailview,
            detailFormatter: function (rowIndex, rowData) {
                return '<table><tr>' +
                    '<td rowspan=2 style="border:0"><img src="${pageContext.request.contextPath}/main/' + rowData.pic + '" style="height:50px;"></td>' +
                    '<td style="border:0">' +
                    '<p>time: ' + rowData.time + '</p>' /*+
                    '<p>name: ' + rowData.title + '</p>' +
                    '<p>Status: ' + rowData.status + '</p>'*/ +
                    '</td>' +
                    '</tr></table>';
            }
        });
    })

    function addUser() {
        $("#ff").form("submit", {
            url: "${pageContext.request.contextPath}/user/insert",
            success: function (data) {
                console.log(data);
                data = JSON.parse(data);
                console.log(data);
                if (data.return) {
                    alert("添加成功");
                    $("#dd_user").dialog("close");
                    $("#dg_user").datagrid("reload");
                } else {
                    alert("添加失败");
                }
            }
        })
    }

    function noUser() {
        $("#dd_user").dialog("close")
    }

    function updateBanner() {
        $("#ff_update").form("submit", {
            url: "${pageContext.request.contextPath}/user/update",
            success: function (data) {
                console.log(data)
                data = JSON.parse(data);
                if (data.return) {
                    alert("修改成功，请确认")
                    $("#dd_update").dialog("close");
                    $("#dg_user").datagrid("reload");
                } else {
                    alert("修改失败，请确认")
                }
            }
        })
    }

    function noUpdateBanner() {
        $("#dd_update").dialog("close")
    }
</script>
<table id="dg_user"></table>

<div id="dd_user" class="easyui-dialog" title="添加" collapsible=true, style="width:400px;height:200px;" data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    addUser();
				}
			},{
				text:'关闭',
				handler:function(){
				   noUser();
				}
			}]">


    <form id="ff" method="post" enctype="multipart/form-data">
        <div>
            <label for="name">名称:</label>
            <input id="name" type="text" name="name" data-options="required:true"/>
        </div>
        <div>
            <label for="phone">电话:</label>
            <input id="phone" type="text" name="phone" data-options="required:true"/>
        </div>
        <div>
            <label for="province">省份:</label>
            <input id="province" type="text" name="province" data-options="required:true"/>
        </div>
        <div>
            <label for="city">城市:</label>
            <input id="city" type="text" name="city" data-options="required:true"/>
        </div>
        <div>
            <label for="sign">个签:</label>
            <input id="sign" type="text" name="sign" data-options="required:true"/>
        </div>
        <div>
            <label for="file">图片:</label>
            <input id="file" class="easyui-filebox" name="file" style="width:300px">
        </div>
        性别<input type="radio" value="1" name="sex">男
        <input type="radio" value="0" name="sex">女<br>
        状态<input type="radio" value="on" name="status">使用
        <input type="radio" value="off" name="status">禁用

    </form>

</div>

<div id="dd_update" class="easyui-dialog" title="修改" collapsible=true, style="width:400px;height:200px;" data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    updateBanner();
				}
			},{
				text:'关闭',
				handler:function(){
				   noUpdateBanner();
				}
			}]">


    <form id="ff_update" method="post" enctype="multipart/form-data">
        <div>
            <input id="id1" type="hidden" name="id" data-options="required:true"/>
        </div>
        <div>
            <label for="name">名称:</label>
            <input id="name1" type="text" name="name" data-options="required:true"/>
        </div>
        <div>
            <label for="phone">电话:</label>
            <input id="phone1" type="text" name="phone" data-options="required:true"/>
        </div>
        <div>
            <label for="province">省份:</label>
            <input id="province1" type="text" name="province" data-options="required:true"/>
        </div>
        <div>
            <label for="city">城市:</label>
            <input id="city1" type="text" name="city" data-options="required:true"/>
        </div>
        <div>
            <label for="sign">个签:</label>
            <input id="sign1" type="text" name="sign" data-options="required:true"/>
        </div>
        <div>
            <label for="file">图片:</label>
            <input id="file1" class="easyui-filebox" name="file" style="width:300px">
        </div>
        性别<input type="radio" value="1" name="sex">男
        <input type="radio" value="0" name="sex">女<br>
        状态<input type="radio" value="on" name="status">使用
        <input type="radio" value="off" name="status">禁用
    </form>

</div>

