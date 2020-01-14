<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Robot Game</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <style type="text/css">
        html {
            font-family: Helvetica, Arial, sans-serif;
            -webkit-font-smoothing: antialiased;
        }
        table {
            border-collapse: collapse;
            border: 2px solid #999;
        }
        td, th {
            border: 1px solid #999;
            padding: 0.4rem;
        }
        th {
            padding: 0.6rem;
            font-size: large;
        }
        .form-style {
            margin: 10px;
            max-width: 400px;
            padding: 20px 12px 10px 20px;
            font: 13px "Lucida Sans Unicode", sans-serif;
        }
        .form-style li {
            padding: 0;
            display: block;
            list-style: none;
            margin: 10px 0 0 0;
        }
        .form-style label {
            margin: 0 0 3px 0;
            padding: 0;
            display:block;
            font-weight: bold;
        }
        .form-style input,
        .form-style select {
            box-sizing: border-box;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            border: 1px solid #BEBEBE;
            padding: 7px;
            margin: 0;
            -webkit-transition: all 0.30s ease-in-out;
            -moz-transition: all 0.30s ease-in-out;
            -o-transition: all 0.30s ease-in-out;
            outline: none;
        }
        .form-style input:focus,
        .form-style select:focus {
            -moz-box-shadow: 0 0 8px #88D5E9;
            -webkit-box-shadow: 0 0 8px #88D5E9;
            box-shadow: 0 0 8px #88D5E9;
            border: 1px solid #88D5E9;
        }
        .form-style .field-long,
        .form-style .field-select {
            width: 100%;
        }
        .form-style button {
            background: #0b76ef;
            padding: 8px 15px;
            border: none;
            color: #fff;
        }
        .form-style button:hover {
            background: #2f91ff;
            box-shadow:none;
            -moz-box-shadow:none;
            -webkit-box-shadow:none;
            cursor: pointer;
        }
        .cbx {
            position:absolute;
            margin:5px;
            -webkit-perspective: 20;
            perspective: 20;
            border: 2px solid #e8e8eb;
            background: #e8e8eb;
            border-radius: 4px;
            transform: translate3d(0, 0, 0);
            cursor: pointer;
            transition: all 0.6s ease;
        }
        .cbx:hover {
            border-color: #0b76ef;
        }
        .flip {
            display:block;
            transition: all 0.6s ease;
            transform-style: preserve-3d;
            position: relative;
            width: 20px;
            height: 20px;
        }
        #autorefresh {
            display:none;
        }
        #autorefresh:checked + .cbx {
            border-color: #0b76ef;
        }
        #autorefresh:checked + .cbx .flip {
            transform: rotateY(180deg);
        }
        .front,
        .back {
            backface-visibility:hidden;
            position:absolute;
            top: 0;
            left: 0;
            width: 20px;
            height: 20px;
            border-radius: 2px;
        }
        .front {
            background: #fff;
            z-index: 1;
        }
        .back {
            transform: rotateY(180deg);
            background: #0b76ef;
            text-align: center;
            color: #fff;
            line-height: 20px;
            box-shadow: 0 0 0 1px #0b76ef;
        }
        .back svg {
            margin-top: 3px;
            fill: none;
        }
        .back svg path {
            stroke: #fff;
            stroke-width: 2.5;
            stroke-linecap: round;
            stroke-linejoin: round;
        }
    </style>
</head>
<body>
<h1>Robot Game</h1>
<form id="task-form" name="task">
    <ul class="form-style">
        <li>
            <label for="robotType">Robot type:</label>
            <select id="robotType" name="robotType" class="field-select">
                <option value="Autobot">Autobot</option>
                <option value="NS5">NS5</option>
                <option value="R2D2">R2D2</option>
                <option value="T1000">T1000</option>
                <option value="Common" selected>Common</option>
            </select>
        </li>
        <li>
            <label for="estimate">Estimation:</label>
                <input id="estimate" class="field-long" title="Estimation" type="number" name="estimate" value="10">
        </li>
        <li>
            <label for="taskType">Task Type:</label>
                <select id="taskType" name="taskType" class="field-select">
                    <option value="Work" selected>Work</option>
                    <option value="Self Destroy">Self Destroy</option>
                </select>
        </li>
        <li>
            <button id="add-task" type="submit">Add task</button>
        </li>
        <li>
            <span>Auto Refresh (5sec)</span><br>
            <input id="autorefresh" type="checkbox" name="autorefresh" checked onchange="chkAutoRefresh()">
            <label class="cbx" for="autorefresh">
                <div class="flip">
                    <div class="front"></div>
                    <div class="back">
                        <svg width="16" height="14" viewBox="0 0 16 14">
                            <path d="M2 8.5L6 12.5L14 1.5"></path>
                        </svg>
                    </div>
                </div>
            </label>
        </li>
    </ul>
</form>
<div style="margin-top: 36px">
    <h3>Activity Log</h3>
    <div id="activity-log">
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
    </div>
</div>
<script type="text/javascript">
    let myVar = null;
    window.onload = function () {
        chkAutoRefresh();
    };
    function chkAutoRefresh() {
        if (document.getElementById('autorefresh').checked) {
            myVar = setInterval(function () {
                refreshLogViaAjax();
            }, 5000);
        } else {
            clearInterval(myVar);
        }
    }
    function refreshLogViaAjax() {
        $.ajax({
            type: "GET",
            url: "/logview",
            success: function (result) {
                if (result.length > 0) {
                    $("#activity-log").html(result);
                }
            },
            error: function (e) {
                console.log("ERROR");
                clearInterval(myVar)
            }
        });
    }
    jQuery(document).ready(function ($) {
        $("#add-task").click(function (event) {
            event.preventDefault();
            $("#add-task").prop("disabled", true);
            addTaskViaAjax();
        });
    });
    function addTaskViaAjax() {
        var robotType = $("#robotType").val();
        var estimate = $("#estimate").val();
        var taskType = $("#taskType").val();
        var task = {"robotType": robotType, "estimate": estimate, "taskType": taskType};
        $.ajax({
            type: "POST",
            url: "/api/task",
            contentType: "application/json",
            data: JSON.stringify(task),
            success: function (result) {
                console.log("SUCCESS: ", result);
                $("#add-task").prop("disabled", false);
            },
            error: function (e) {
                console.log("ERROR: ", e);
            },
            done: function (e) {
                console.log("DONE");
            }
        });
    }
</script>
</body>
</html>