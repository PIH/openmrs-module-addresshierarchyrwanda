<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script>
var typeNames = new Array();
var typeIds = new Array();
<c:forEach var="type" items="${typeOptions}">
	typeNames.push("<c:out value="${type.value}"/>");
	typeIds.push(<c:out value="${type.key}"/>);
</c:forEach>

</script>
</head>
<body>
<h3>Please Upload a File Here:</h3>
<form method="post"
	action="${pageContext.request.contextPath}/module/addresshierarchyrwanda/fileUpload.form"
	enctype="multipart/form-data"><select name="typeId">
	<c:forEach var="type" items="${typeOptions}">
		<option value="<c:out value="${type.key}"/>"><c:out
			value="${type.value}" /></option>
	</c:forEach>
</select> <input type="file" name="file" /> <input type="submit" value="Upload!">
</form>
</body>
</html>