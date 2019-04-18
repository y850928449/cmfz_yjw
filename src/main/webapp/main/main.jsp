<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>持名法州主页</title>
    <link rel="stylesheet" type="text/css" href="../themes/black/easyui.css">
    <link rel="stylesheet" type="text/css" href="../themes/IconExtension.css">
    <link rel="stylesheet" type="text/css" href="../themes/icon.css">
    <script type="text/javascript" src="../js/jquery.min.js"></script>
    <script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../js/datagrid-detailview.js"></script>
    <script type="text/javascript" src="../js/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="../js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">
        <!--菜单处理-->
    </script>
    <script>
        $(function () {
            $.ajax({
                type: "get",
                url: "${pageContext.request.contextPath}/menu/selectAll",
                dataType: "JSON",
                success: function (data) {
                    console.log(data);
                    //第一个参数是要遍历的集合对象，第二个是function
                    //第一个参数是遍历的下标，第二个是遍历的对象
                    $.each(data, function (index1, first) {
                        var c = "<div align='center'>";
                        if (first.parent_id == null) {
                            $.each(first.list, function (index2, second) {
                                var a = JSON.stringify(second);
                                c += "<p><a class='easyui-linkbutton' onclick='addTabs(" + a + ")'>" + second.title + "</a></p>";
                            })
                            c += "</div>"
                            $("#aa").accordion("add", {
                                title: first.title,
                                iconCls: first.icon_cls,
                                content: c,
                                selected: true
                            })
                        }
                    })
                }
            })
        })

        function addTabs(second) {
            var isExists = $('#tt').tabs('exists', second.title);
            if (!isExists) {
                $('#tt').tabs('add', {
                    title: second.title,
                    href: '${pageContext.request.contextPath}' + second.jsp_url,
                    iconCls: second.icon_cls,
                    closable: true,
                    tools: [{
                        iconCls: 'icon-mini-refresh',
                        handler: function () {
                            alert('refresh');
                        }
                    }]
                });
            } else {
                $('#tt').tabs('select', second.title);
            }

        }

    </script>
</head>
<body class="easyui-layout">
<div data-options="region:'north',split:true" style="height:60px;background-color:  #5C160C">
    <div style="font-size: 24px;color: #FAF7F7;font-family: 楷体;font-weight: 900;width: 500px;float:left;padding-left: 20px;padding-top: 10px">
        持名法州后台管理系统
    </div>
    <div style="font-size: 16px;color: #FAF7F7;font-family: 楷体;width: 300px;float:right;padding-top:15px">欢迎您:xxxxx
        &nbsp;<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改密码</a>&nbsp;&nbsp;<a href="#"
                                                                                                              class="easyui-linkbutton"
                                                                                                              data-options="iconCls:'icon-01'">退出系统</a>
    </div>
</div>
<div data-options="region:'south',split:true" style="height: 40px;background: #5C160C">
    <div style="text-align: center;font-size:15px; color: #FAF7F7;font-family: 楷体">&copy;百知教育 htf@zparkhr.com.cn</div>
</div>

<div data-options="region:'west',title:'导航菜单',split:true,iconCls:'icon-reload'" style="width:220px;">
    <div id="aa" class="easyui-accordion" data-options="fit:true">
        <%--<c:forEach items="${requestScope.list}" var="list">
            <c:if test="${list.parent_id==null}">
        <div data-options="title:'${list.title}',iconCls:'${list.icon_cls}'" a1="${list.title}">
            <c:forEach items="${list.list}" var="list1">
                <ul onclick="look()">${list1.title}</ul>
            </c:forEach>
        </div>
            </c:if>
        </c:forEach>--%>
    </div>
</div>
<div data-options="region:'center'">
    <div id="tt" class="easyui-tabs" data-options="fit:true,narrow:true,pill:true">
        <div title="主页" data-options="iconCls:'icon-neighbourhood',"
             style="background-image:url(${pageContext.request.contextPath}/main/image/shouye.jpg);background-repeat: no-repeat;background-size:100% 100%;"></div>
    </div>
    <div id="tab" class="easyui-tabbs" data-options="fit:true,narrow:true,pill:true"></div>
</div>
</body>
</html>
