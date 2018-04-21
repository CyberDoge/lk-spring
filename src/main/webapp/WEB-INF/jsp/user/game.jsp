<html>
<title>game</title>
<head>
    <meta charset="UTF-8">
    <link href="http://twitter.github.com/bootstrap/assets/css/bootstrap.css" rel="stylesheet">
    <link href="http://twitter.github.com/bootstrap/assets/css/bootstrap-responsive.css" rel="stylesheet">
    <%--<script type="text/javascript" src="/resources/static/js/jquery-1.9.1.min.js"></script>--%>
    <%--<script type="text/javascript" src="/resources/static/js/game.js"></script>--%>
    <script src="js/jquery-1.9.1.min.js"></script>
    <script src="js/game.js"></script>



</head>
<body>
<h1>snake game from: https://github.com/huydx/snake-js/</h1>

<table id="gameboard" align="center" width="300px" height="300px" border="1"></table>
<div class="control-group" align="center">
    <label class="control-label" for="input01"></label>
    <div class="controls">
        <button id="stop" class="btn btn-success" rel="tooltip" title="first tooltip">stop</button>
        <button id="reload" class="btn btn-success" rel="tooltip" title="first tooltip">reload</button>
        <button id="resume" class="btn btn-success" rel="tooltip" title="first tooltip">resume</button>
        <div id="score">0</div>
        <div id="result"></div>
    </div>
</div>
</body>
</html>