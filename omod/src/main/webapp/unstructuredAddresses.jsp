
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:htmlInclude
	file="/moduleResources/addresshierarchyrwanda/jquery-1.3.2.min.js" />

<openmrs:htmlInclude
	file="/moduleResources/addresshierarchyrwanda/thickbox.js" />
<openmrs:htmlInclude
	file="/moduleResources/addresshierarchyrwanda/thickbox.css" />
<openmrs:htmlInclude
	file="/moduleResources/addresshierarchyrwanda/dataentrystyle.css" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script>
	var countInterval;
	var currentPage = 1;
	var locations;
	var statstable;

	function getEditImageElement(personId) {
		var editImage = $(document.createElement("img")).attr("src", openmrsContextPath + "/images/edit.gif");
		var linkElement = $(document.createElement("a"))

		.attr("href", openmrsContextPath + "/admin/patients/patient.form?patientId=" + personId).append(editImage);
		linkElement.css("cursor", "pointer");
		editImage.css("border", "medium none");
		return linkElement;
	}

	function getUnstructuredAddresses(page) {
		var count = 0;
		var locId = $(locations).val();
		jQuery.getJSON(
				"${pageContext.request.contextPath}/module/addresshierarchyrwanda/ahFindUnstructuredAddresses.form", {
					page : page,
					locId : locId
				}, function(data) {
					currentPage = page;
					$("#addresslist").empty();
					$("#currentPage").text(currentPage);
					jQuery.each(data.rows, function(i, row) {

						var editImageElement = getEditImageElement(row.columnIndex_0);

						var tableRow = $(document.createElement("tr")).append(
								$(document.createElement("td")).append(editImageElement)).append(
								$(document.createElement("td")).css("padding", "5px").text(row.columnIndex_1))
						//.append($(document.createElement("td")).text(row.columnIndex_1))
						.append($(document.createElement("td")).text(row.columnIndex_2)).append(
								$(document.createElement("td")).text(row.columnIndex_3)).append(
								$(document.createElement("td")).text(row.columnIndex_4)).append(
								$(document.createElement("td")).text(row.columnIndex_5)).append(
								$(document.createElement("td")).text(row.columnIndex_6)).append(
								$(document.createElement("td")).text(row.columnIndex_7));
						//.append($(document.createElement("td")).text(row.columnIndex_8));

						if ((count++) % 2 == 0) {
							tableRow.addClass("evenRow");
						} else {
							tableRow.addClass("oddRow");
						}

						$("#addresslist").append(tableRow);
					});
				});
	}

	function getLocations() {
		var count = 0;

		jQuery.getJSON("${pageContext.request.contextPath}/module/addresshierarchyrwanda/ahGetOMRSLocations.form", {

		}, function(data) {

			jQuery.each(data.locs, function(i, loc) {

				var option = $(document.createElement("option")).val(loc.id).text(loc.name);

				$(locations).append(option);
			});

			getUnstructuredAddresses(1);
			getLocationStats();
			
		});

	}

	function getLocationStats() {

		jQuery.getJSON("${pageContext.request.contextPath}/module/addresshierarchyrwanda/ahGetAddressBreakdown.form", {
			locId : $(locations).val()
		}, function(data) {

			$('#statstable ~ tr').remove();

			jQuery.each(data.rows, function(i, row) {
				var tr = $(document.createElement("tr"));
				var td_1 = $(document.createElement("td"));
				var td_2 = $(document.createElement("td"));

				$(td_1).append(row.columnIndex_0);
				$(td_1).attr('align', 'center');

				$(td_2).append(row.columnIndex_1);
				$(td_2).attr('align', 'center');

				$(tr).append(td_1).append(td_2);

				$(statstable).after(tr);

			});

		});
	}

	$(document).ready(function() {
		locations = $("#locations");
		statstable = $("#statstable");

		$(locations).change(function() {
			getUnstructuredAddresses(1);
			getLocationStats();
		}

		);

		$("#forwardButton").click(function() {
			getUnstructuredAddresses(++currentPage);

		});

		$("#backButton").click(function() {
			getUnstructuredAddresses(--currentPage);
		});

		getLocations();
		

	});
</script>
</head>


<b class="boxHeader"> Unstructured Addresses </b>
<div class="box" style="height: 75%; overflow: auto">
<table>
	<tbody>
		<tr>
			<td><img style="cursor: pointer" id="backButton"
				src="${pageContext.request.contextPath}/images/leftArrow.gif" /></td>
			<td><span id="currentPage"></span></td>
			<td><img style="cursor: pointer" id="forwardButton"
				src="${pageContext.request.contextPath}/images/rightArrow.gif" /></td>
			<td>&nbsp;&nbsp;</td>
			<td><select id='locations'></select></td>
		</tr>
	</tbody>
</table>

<table style="width: 95%" cellpadding="0" cellspacing="0">
	<thead style="font-weight: bold;">
		<tr>
			<td>Edit</td>
			<td>Identifier</td>
			<!--  <td>Family Name</td>
			<td>Given Name</td> -->
			<td>Health Center</td>
			<td>Province</td>
			<td>District</td>
			<td>Sector</td>
			<td>Cell</td>
			<td>Umudugudu</td>
		</tr>


	</thead>
	<tbody id="addresslist">

	</tbody>
</table>


</div>

<br />
<br />
<b class="boxHeader"> Sector Population Distribution</b>
<div class="box">
<table style="width: 25%">
	<tbody>
		<tr id="statstable">
			<th align="center" >Sector</th>
			<th align="center" >Count</th>
		</tr>


	</tbody>
</table>
</div>


</html>