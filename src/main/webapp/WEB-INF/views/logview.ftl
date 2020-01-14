<#if messagelogs?has_content>
    <table>
        <tr>
            <th>Date Time</th>
            <th>Message</th>
        </tr>
        <#list messagelogs as messagelog>
            <tr>
                <td>${messagelog.time}</td>
                <td>${messagelog.message}</td>
            </tr>
        </#list>
    </table>
</#if>