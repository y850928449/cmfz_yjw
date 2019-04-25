<%@ page contentType="text/html; UTF-8" isELIgnored="false" language="java" pageEncoding="UTF-8" %>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="active_table" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    var goEasy = new GoEasy({
        appkey: 'BC-0e7dba6c10f440a2b5cb91a1244fc0a0'
    })
    goEasy.subscribe({
        channel: 'cmfz',
        onMessage: function (message) {
            var i = JSON.parse(message.content)
            var nowChart = echarts.init(document.getElementById('active_table'));
            var now = {
                title: {
                    text: '用户活跃度统计图'
                },
                tooltip: {},
                legend: {
                    data: ['注册人数']
                },
                xAxis: {
                    data: i.intervals
                },
                yAxis: {},
                series: [{
                    name: '注册人数',
                    type: 'bar',
                    data: i.counts
                }]
            };
            // 使用刚指定的配置项和数据显示图表。
            nowChart.setOption(now);
        }
    });
    $.ajax({
        url: '${pageContext.request.contextPath}/user/selectCount',
        dataType: 'JSON',
        success: function (data) {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('active_table'));

            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '用户活跃度统计图'
                },
                tooltip: {},
                legend: {
                    data: ['注册人数']
                },
                xAxis: {
                    data: data.intervals
                },
                yAxis: {},
                series: [{
                    name: '注册人数',
                    type: 'bar',
                    data: data.counts
                }]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }
    })

</script>
<%--<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>

<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="statistics_main" style="width: 600px;height: 400px;;margin-top: 30px;margin-left: 30px"></div>

<script type="text/javascript">
    var goEasy = new GoEasy({
        appkey: 'BC-0e7dba6c10f440a2b5cb91a1244fc0a0'
    });
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('statistics_main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '持名法州App活跃用户'
        },
        tooltip: {},
        legend: {
            data:['用户数量']
        },
        xAxis: {
            data: []
        },
        yAxis: {},
        series: [{
            name: '数量',
            type: 'bar',
            data: []
        }]
    };

    myChart.setOption(option);

    /*
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("intervals",new String[]{"7天","15天"});
    map.put("counts",new int[]{5,10});'
    return map;

    [{"intervals":["7天","15天"]},{}]


    */
    // 异步加载统计信息
    /*var goEasy = new GoEasy({
        appkey: "BC-fdbdb55257834c07bb017622f587572f"
    });*/
    $.post("${pageContext.request.contextPath }/user/selectCount",function(data){
        /*goeast*/
        goEasy.subscribe({
            channel:'cmfz',
            onMessage: function(message){
                var s=JSON.parse(message.content);
                console.log(s)
                    myChart.setOption({
                        xAxis: {
                            data: s.intervals
                        },
                        series: [{
                            // 根据名字对应到相应的系列
                            name: '活跃用户',
                            data: s.counts
                        }]
                    });
            }
        });
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption({
            xAxis: {
                data: data.intervals
            },
            series: [{
                // 根据名字对应到相应的系列
                name: '活跃用户',
                data: data.counts
            }]
        });
    },"json");


</script>--%>
