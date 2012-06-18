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
<title>Initialize Hierarchy</title>
<script>
	var countInterval;
	
	function getAddressHierarchyCount() {
		jQuery.getJSON("${pageContext.request.contextPath}/module/addresshierarchyrwanda/ahGetAddressHierarchyCount.form",
						function(data) {
							jQuery.each(data.values, function(i, value) {
								$("#valueid").text(value.value);
								$("#progressbar").css("width",(value.value/17526)*100+"%");
							});
						});
	}

	function getInitialCount() {
		jQuery.getJSON(
				"${pageContext.request.contextPath}/module/addresshierarchyrwanda/ahGetAddressHierarchyCount.form",
				function(data) {
					jQuery.each(data.values, function(i, value) {
						if(value.value == 17526){
							$("#progressbarparent").hide();
						}
						$("#valueid").text(value.value);
					});
				});
	}
	function initializeSystem() {
		jQuery.getJSON(
				"${pageContext.request.contextPath}/module/addresshierarchyrwanda/ahDeleteTables.form",
				function(data) {
					jQuery.getJSON(
							"${pageContext.request.contextPath}/module/addresshierarchyrwanda/ahInitializeSystem.form",
							function(data) {
								//jQuery.each(data.types, function(i, type) {
								//});
								clearInterval(countInterval);
								$("#valueid").text("finished!");
								$("#progressbar").css("width",("100%"));
					            $("#startid").removeAttr("disabled");

							});
						countInterval = setInterval(getAddressHierarchyCount, 2000);
				});
	}
	
	$(document).ready( function() {
		$("#progressbarparent").hide();
		getInitialCount();
		
		$("#startid").click( function() {
            $("#startid").attr("disabled", "disabled");
			initializeSystem();
			$("#progressbarparent").show();
		});

	});
	
</script>
</head>
<b class="boxHeader"> Initialize the Address Hierarchy </b>
<div class="box">
<p>Click the button to load the address hierarchy structure for
Rwanda:&nbsp; <input type="button" id="startid" value="Load Hierarchy" /></p>
<br />
<br />

<p style="margin: auto">The address hierarchy size is:&nbsp;<span
	id="valueid"> </span></p>
<br />
<br />

<div id="progressbarparent"
	style="border-style: solid; border-color: navy; width: 100%; background-color: white; height: 20px">
<div id="progressbar"
	style="margin: 0px; padding: 0px; width: 0%; height: 100%; background-color: navy;"></div>
</div>

</div>

</html>