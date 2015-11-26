<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
	<h2>CRUD DocumentObjet using Spring MVC and MongoDB.</h2>

		<form action="documentobjet/save" method="post">
			<input type="hidden" name="id">
			</br>
			<label for="name">Nom </label>
			<input type="text" id="name" name="name"/>
			</br>
			<label for="type">Type </label>
			<input type="text" id="type" name="type"/>
			</br>
			<label for="size">Size </label>
			<input type="text" id="size" name="size"/>
			</br>
			<label for="location">Location </label>
			<input type="text" id="location" name="location"/>
			</br>
			<label for="accessDate">Access Date </label>
			<input type="text" id="accessDate" name="accessDate"/>
			</br>
			<label for="modifyDate">Modify Date </label>
			<input type="text" id="modifyDate" name="modifyDate"/>
			</br>
			<input type="submit" value="Submit"/>
		</form>

	<table border="1">
		<c:forEach var="docObjet" items="${list}">
			<tr>
				<td>${docObjet.name}</td>
				<td>${docObjet.type}</td>
				<td>${docObjet.size}</td>
				<td>${docObjet.location}</td>
				<td>${docObjet.accessDate}</td>
				<td>${docObjet.modifyDate}</td>
				<td><input type="button" value="delete" onclick="window.location='documentobjet/delete?id=${docObjet.id}'"/></td>
				
			</tr>
		</c:forEach>
	</table>	
</body>
</html>