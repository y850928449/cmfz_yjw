<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<script>
    $(function () {
        var tb = [{
            iconCls: 'icon-tip',
            text: '专辑详情',
            handler: function () {
                var selectedRow = $("#dg_album").treegrid("getSelected");
                if (selectedRow == null || selectedRow.size != null) {
                    $.messager.alert('提示消息', '请选择要查看的专辑');
                    return;
                }
                $("#dd_album1").dialog('open');
                //带上原数据
                $("#ff_album1").form('load', selectedRow)
                $("#pic").prop("src", "${pageContext.request.contextPath}/main/" + selectedRow.pic)
            }
        }, '-', {
            iconCls: 'icon-save',
            text: '添加专辑',
            handler: function () {
                $('#dd_album').dialog('open');
            }
        }, '-', {
            iconCls: 'icon-save',
            text: '添加章节',
            handler: function () {
                var row = $("#dg_album").treegrid("getSelected");
                if (row == null || row.size == null) {
                    $.messager.alert('提示消息', '请选择一个专辑进行添加');
                    return;
                }
                $("#dd_chapter").dialog("open");
                $("#albumId").val(row.id);
            }
        }, '-', {
            iconCls: 'icon-undo',
            text: '下载音频',
            handler: function () {
                var row = $("#dg_album").treegrid("getSelected");
                if (row == null || row.size == null) {
                    $.messager.alert('警告！', '请选择章节进行下载');
                    return;
                }
                location.href = "${pageContext.request.contextPath}/chapter/down?path=" + row.path + "&title=" + row.title
            }
        }
            , '-', {
                iconCls: 'icon-tip',
                text: '千千阙歌',
                handler: function () {
                    var row = $("#dg_album").treegrid("getSelected");
                    if (row == null || row.size == null) {
                        $.messager.alert('警告！', '请选择你想听的章节');
                        return;
                    }
                    $("#music").dialog("open");
                    $("#audio").prop("src", "${pageContext.request.contextPath}" + row.path)
                }
            }];
        /*展示*/
        $('#dg_album').treegrid({
            method: 'get',
            url: '${pageContext.request.contextPath}/album/selectAll',
            insertUrl: '${pageContext.request.contextPath}/album/insert',
            idField: 'id',//用来标识标识树节点   主干树与分支树节点  ID不能有相同  并且使用相同的字段  否则ID冲突
            treeField: 'title',//用来定义树节点   树形表格上要展示的信息   注意使用相同的字段 用来展示对应节点名称
            toolbar: tb,
            fit: true,
            fitColumns: true,
            columns: [[
                {field: 'title', title: '专辑名', width: 180},
                {field: 'size', title: '章节大小', width: 80},
                {field: 'duration', title: '章节时长', width: 80}
            ]]
        });
    })

    /*添加专辑*/
    function add_album() {
        $("#ff_album").form("submit", {
            url: "${pageContext.request.contextPath}/album/insert",
            success: function (data) {
                console.log(data);
                data = JSON.parse(data);
                console.log(data);
                if (data.return) {
                    alert("添加成功");
                    $("#dd_album").dialog("close");
                    $("#dg_album").datagrid("reload");
                } else {
                    alert("添加失败");
                }
            }
        })
    }

    /*添加章节*/
    function insertChapter() {
        $("#ff_chapter").form("submit", {
            url: "${pageContext.request.contextPath}/chapter/insert",
            success: function (data) {
                console.log(data);
                data = JSON.parse(data);
                console.log(data);
                if (data.return) {
                    alert("添加成功");
                    $("#dd_chapter").dialog("close");
                    $("#dg_album").treegrid("reload");
                } else {
                    alert("添加失败");
                }
            }
        })
    }

    /*关闭添加专辑*/
    function no_album() {
        $("#dd_album").dialog("close")
    }

    /*关闭查看专辑*/
    function no_album1() {
        $("#dd_album1").dialog("close")
    }

    /*关闭添加章节*/
    function noChapter() {
        $("#dd_chapter").dialog("close")
    }

    function noMusic() {
        $("#music").dialog("close");
    }
</script>
<%--添加专辑--%>
<table id="dg_album" style="width:600px;height:400px"></table>

<div id="dd_album" class="easyui-dialog" title="添加" collapsible=true, style="width:400px;height:200px;" data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    add_album();
				}
			},{
				text:'关闭',
				handler:function(){
				   no_album();
				}
			}]">


    <form id="ff_album" method="post" enctype="multipart/form-data">
        <div>
            <label for="title">名称:</label>
            <input id="title" type="text" name="title" data-options="required:true"/>
        </div>
        <div>
            <label for="file">图片:</label>
            <input id="file" class="easyui-filebox" name="file" style="width:300px">
        </div>
        <div>
            <label for="score">星级:</label>
            <input id="score" type="text" name="score" data-options="required:true"/>
        </div>
        <div>
            <label for="author">作者:</label>
            <input id="author" type="text" name="author" data-options="required:true"/>
        </div>
        <div>
            <label for="boardcast">CV:</label>
            <input id="boardcast" type="text" name="boardcast" data-options="required:true"/>
        </div>
        <div>
            <label for="brief">详情:</label>
            <input id="brief" type="text" name="brief" data-options="required:true"/>
        </div>

    </form>

</div>
<%--查看专辑--%>
<div id="dd_album1" class="easyui-dialog" title="添加" collapsible=true, style="width:400px;height:200px;" data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'关闭',
				handler:function(){
				   no_album1();
				}
			}]">


    <form id="ff_album1" method="post" enctype="multipart/form-data">
        <div>
            <label for="title">名称:</label>
            <input id="title1" type="text" name="title" data-options="required:true"/>
        </div>
        <div>
            <label for="file">图片:</label>
            <img id="pic" src="">
        </div>
        <div>
            <label for="score">星级:</label>
            <input id="score1" type="text" name="score" data-options="required:true"/>
        </div>
        <div>
            <label for="author">作者:</label>
            <input id="author1" type="text" name="author" data-options="required:true"/>
        </div>
        <div>
            <label for="boardcast">CV:</label>
            <input id="boardcast1" type="text" name="boardcast" data-options="required:true"/>
        </div>
        <div>
            <label for="brief">详情:</label>
            <input id="brief1" type="text" name="brief" data-options="required:true"/>
        </div>

    </form>

</div>
<%--添加章节--%>
<div id="dd_chapter" class="easyui-dialog" title="添加章节" collapsible=true, style="width:400px;height:200px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    insertChapter();
				}
			},{
				text:'关闭',
				handler:function(){
				   noCharpter();
				}
			}]">


    <form id="ff_chapter" method="post" enctype="multipart/form-data">
        <div>
            <input id="id1" type="hidden" name="id" data-options="required:true"/>
        </div>
        <div>
            <label for="title">名称:</label>
            <input id="title2" type="text" name="title" data-options="required:true"/>
        </div>
        <div>
            <label for="file">音频:</label>
            <input id="file1" class="easyui-filebox" name="file" style="width:300px">
        </div>
        <div>
            <input type="hidden" name="albumId" id="albumId">
        </div>
    </form>

</div>


<div id="music" class="easyui-dialog" title="添加章节" collapsible=true, style="width:400px;height:200px;" data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'关闭',
				handler:function(){
				   noMusic();
				}
			}]">
    <audio src="" id="audio"></audio>

</div>

