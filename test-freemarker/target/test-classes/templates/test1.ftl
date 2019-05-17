<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf‐8">
    <title>Hello World!</title>
</head>
<body>
Hello ${name}!
<br/>
遍历数据模型中的list学生信息（数据模型中的名称为stus）
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
        <td>出生日期</td>
    </tr>
    <#if stus??>
        <#list stus as stu>
            <tr>
                <td>${stu_index + 1}</td>
                <td <#if stu.name == '小明'> style="background: red;"</#if>>${stu.name}</td>
                <td>${stu.age}</td>
                <td <#if stu.money gt 300> style="background: cornflowerblue"</#if>>${stu.money}</td>
                <td>${stu.birthday?string("YYYY年MM月")}</td>
            </tr>
        </#list>
        <br/>
        学生个数：${stus?size}
    </#if>
</table>
<br/>
遍历数据模型中的map数据,第一种方法：在中括号中填map的key；第二种方法，在map后直接加".key"
<br/>
输出stu1的学生信息：<br/>
<#--<#if stuMap?? && stuMap.stu1??>-->
    姓名：${(stuMap['stu1'].name)!''}<br/>
    年龄：${(stuMap['stu1'].age)!''}<br/>
    输出stu1的学生信息：<br/>
    姓名：${(stuMap.stu1.name)!''}<br/>
    年龄：${(stuMap.stu1.age)!''}<br/>
<#--</#if>-->
遍历map中的key.stuMap?Keys就是key列表（是一个list）<br/>
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
    <#--<td>出生日期</td>-->
    </tr>
<#list  stuMap?keys as k>
    <tr>
        <td>${k_index + 1}</td>
        <td>${stuMap[k].name}</td>
        <td>${stuMap[k].age}</td>
        <td>${stuMap[k].money}</td>
    <#--<td>${stu.birthday}</td>-->
    </tr>
</#list>
</table>
${point?c}<br/>
<#assign text="{'bank':'工商银行','account':'10101920201920212'}" />
<#assign data=text?eval />
开户行：${data.bank} 账号：${data.account}
</body>
</html>