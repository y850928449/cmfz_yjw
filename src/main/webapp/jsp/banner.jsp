<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    $(function () {
        $("#dg_banner").edatagrid({
            url: '${pageContext.request.contextPath}/banner/selectAll',
            updateUrl: '${pageContext.request.contextPath}/banner/update',
            destroyUrl: '${pageContext.request.contextPath}/banner/delete',
            saveUrl: '${pageContext.request.contextPath}/banner/update',
            fit: true,
            fitColumns: true,
            pagination: true,
            pageSize: 2,
            pageList: [2, 4, 6, 8, 10],
            columns: [[
                {field: 'title', title: '图片名', width: 100},
                {field: 'pic', title: '路径', width: 100},
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
                    $("#dd_banner").dialog("open")
                }
            }, '-', {
                iconCls: 'icon-edit',
                text: '修改',
                handler: function () {
                    var selectedRow = $("#dg_banner").datagrid("getSelected");
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
                    $('#dg_banner').edatagrid('destroyRow');
                    /*$('#dg_banner').edatagrid('load');*/
                }
            }, '-', {
                iconCls: 'icon-save',
                text: '保存',
                handler: function () {/*alert('保存按钮')*/
                    $("#dg_banner").edatagrid("saveRow");
                }
            }],
            view: detailview,
            detailFormatter: function (rowIndex, rowData) {
                return '<table><tr>' +
                    '<td rowspan=2 style="border:0"><img src="${pageContext.request.contextPath}/main/' + rowData.pic + '" style="height:50px;"></td>' +
                    '<td style="border:0">' +
                    '<p>time: ' + rowData.time + '</p>' +
                    '<p>name: ' + rowData.title + '</p>' +
                    '<p>Status: ' + rowData.status + '</p>' +
                    '</td>' +
                    '</tr></table>';
            }
        });
    })

    function addBanner() {
        $("#ff").form("submit", {
            url: "${pageContext.request.contextPath}/banner/insert",
            success: function (data) {
                console.log(data);
                data = JSON.parse(data);
                console.log(data);
                if (data.return) {
                    alert("添加成功");
                    $("#dd_banner").dialog("close");
                    $("#dg_banner").datagrid("reload");
                } else {
                    alert("添加失败");
                }
            }
        })
    }

    function noBanner() {
        $("#dd_banner").dialog("close")
    }

    function updateBanner() {
        $("#ff_update").form("submit", {
            url: "${pageContext.request.contextPath}/banner/update",
            success: function (data) {
                console.log(data)
                data = JSON.parse(data);
                if (data.return) {
                    alert("修改成功，请确认")
                    $("#dd_update").dialog("close");
                    $("#dg_banner").datagrid("reload");
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
<table id="dg_banner"></table>

<div id="dd_banner" class="easyui-dialog" title="添加" collapsible=true, style="width:400px;height:200px;" data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    addBanner();
				}
			},{
				text:'关闭',
				handler:function(){
				   noBanner();
				}
			}]">


    <form id="ff" method="post" enctype="multipart/form-data">
        <div>
            <label for="title">名称:</label>
            <input id="title" type="text" name="title" data-options="required:true"/>
        </div>
        <div>
            <label for="file">图片:</label>
            <input id="file" class="easyui-filebox" name="file" style="width:300px">
        </div>
        状态<input type="radio" value="1" name="status">展示
        <input type="radio" value="0" name="status">不展示

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
            <label for="title">名称:</label>
            <input id="title1" type="text" name="title" data-options="required:true"/>
        </div>
        <div>
            <label for="file">图片:</label>
            <input id="file1" class="easyui-filebox" name="file" style="width:300px">
        </div>
        状态<input type="radio" value="1" name="status">展示
        <input type="radio" value="0" name="status">不展示
    </form>

</div>


<%--
<div id="dd_banner" class="easyui-dialog" title="My Dialog" style="width:400px;height:200px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    addBanner();
				}
			},{
				text:'关闭',
				handler:function(){
				    noBanner();
				}
			}]">

    <form id="ff" method="post" enctype="multipart/form-data">
        <div>
            <label for="name">图片名：</label>
            <input id="name" class="easyui-validatebox" type="text" name="title" data-options="required:true"/>
        </div>
        <div>
            <label >状态:</label>

            展示：<input id="status" class="easyui-validatebox" value="1" type="radio" name="status" data-options="validType:'required:true'"/>
            不展示：<input id="status1" class="easyui-validatebox" value="0" type="radio" name="status" data-options="validType:'required:true'"/>
        </div>
        <input class="easyui-filebox" style="width:150px">
        <div>
            <input id="id" class="easyui-validatebox" type="hidden" name="id" data-options="required:true"/>
        </div>
    </form>
</div>
--%>