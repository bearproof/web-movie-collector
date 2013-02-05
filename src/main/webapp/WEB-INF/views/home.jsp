<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
    <head>
        <title>Spring Web MVC - Atmosphere Snippet</title>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    </head>
    
    <body data-search-url='<c:url value='/search'/>'>
		<input id='txtFromClient' value='Word from Client'/>
		<button id='btnSend'>Send</button>
		<button id='btnAutoSend'>Periodical Send</button>
		<button id='btnStop' disabled='disabled'>Stop</button>
		<textarea id="txaFromServer" style="display:block;" rows="25" cols="100"></textarea>
	
		<script src="${ctx}/resources/js/inheritance-1.0.min.js"></script>
		<script src="${ctx}/resources/js/jquery-1.8.3.min.js"></script>
		<script src="${ctx}/resources/js/jquery.atmosphere-1.0.min.js"></script>
 		<script src="${ctx}/resources/js/base.js"></script>
		<script src="${ctx}/resources/js/home.js"></script>
    </body>
</html>
