
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
	//jQuery.noConflict();
	var idOfLastClicked = "";

	function showLightBox() {
		tb_show('Add a Location',
				'#TB_inline?height=90&width=350&inlineId=lightshowdiv', true);
	}

	function showEditLightBox() {
		tb_show('Edit Location Name',
				'#TB_inline?height=90&width=350&inlineId=editlightshowdiv',
				true);
	}

	/**
	 * ahGetLocationTypes
	 */
	function getHierarchyTypes() {
		//aler("in hierarchy types");
		jQuery
				.getJSON(
						"${pageContext.request.contextPath}/module/addresshierarchyrwanda/ahGetLocationTypes.form",
						function(data) {
							$("#hierarchytypelist").children().remove();
							var option = $(document.createElement("option"))
									.val("-1").text("all types");
							$("#hierarchytypelist").append(option);
							jQuery.each(data.types, function(i, type) {
								var tempOption = $(
										document.createElement("option")).val(
										type.id).text(type.name);
								$("#hierarchytypelist").append(tempOption);
							});
						});
	}

	/**
	 *	Edits the name of a location
	 */
	function editLocationName(locationId, newName) {
		jQuery
				.getJSON(
						"${pageContext.request.contextPath}/module/addresshierarchyrwanda/ahEditLocationName.form",
						{
							newName :newName,
							locationId :locationId
						}, function(data) {

							jQuery.each(data.addresses, function(i, address) {
								$("#" + address.id).text(address.display);
							});
							closeLightBox();
						});
	}
	/**
	 * 	Search all locations in the hierarchy and display results
	 */
	function searchHierarchy(searchString, typeId) {
		jQuery
				.getJSON(
						"${pageContext.request.contextPath}/module/addresshierarchyrwanda/ahsearch.form",
						{
							searchString :searchString,
							typeId :typeId
						},
						function(data) {
							$("#hierarchylist").empty();
							var count = 0;

							jQuery
									.each(
											data.addresses,
											function(i, address) {
												count++;

												var outerLocationDiv = $(document
														.createElement("div"));
												outerLocationDiv
														.addClass("list-item-container");

												var locationElement = $(
														document
																.createElement("div"))
														.text(address.display);
												locationElement.attr("id",
														address.id);
												locationElement.css("width",
														"45%");
												locationElement.css("float",
														"left");
												locationElement.css("margin",
														"2px");

												var typeElement = $(
														document
																.createElement("div"))
														.text(address.type);
												typeElement.css("width", "25%");
												typeElement
														.css("float", "left");

												var editElement = $(document
														.createElement("img"));
												editElement
														.attr("src",
																"${pageContext.request.contextPath}/images/edit.gif");
												editElement
														.attr("height", "16");
												editElement.attr("width", "16");

												editElement.addClass("edit");
												editElement.hide();
												/**editElement.css("float", "right");
												editElement.css("width", "40%");
												editElement.css("text-align", "right");
												editElement.css("margin", "2px");
												 **/
												outerLocationDiv
														.append(locationElement);
												outerLocationDiv
														.append(typeElement);
												outerLocationDiv
														.append(editElement);

												$("#hierarchylist").append(
														outerLocationDiv);

												editElement.hover( function() {
													$(this).addClass(
															'edit-hover');
												}, function() {
													$(this).removeClass(
															'edit-hover');
												});
												locationElement
														.hover(
																function() {
																	$(this)
																			.addClass(
																					'pretty-hover');

																},
																function() {
																	$(this)
																			.removeClass(
																					'pretty-hover');
																});

												locationElement
														.click( function() {
															if (idOfLastClicked != "") {
																$(
																		"#"
																				+ idOfLastClicked)
																		.removeClass(
																				'pretty-selected');
																$(
																		"#"
																				+ idOfLastClicked)
																		.siblings(
																				".edit")
																		.hide(
																				"fast");
															}
															$(this)
																	.addClass(
																			'pretty-selected');
															$(this).siblings(
																	".edit")
																	.show(800);
															getChildren(address.id);
															$("#addchild")
																	.text(
																			"Add "
																					+ address.childType);
															$("#childlabel")
																	.text(
																			address.childType);
															// todo change child type names
															idOfLastClicked = address.id;
														});

												editElement.click( function() {
													$("#editlocationname").val(
															$("#" + address.id)
																	.text());
													$("#editlocationid").val(
															address.id);
													showEditLightBox();
												});
											});
							// no results were returned so let the user add a name
						});
	}

	function closeLightBox() {
		self.parent.tb_remove();
	}

	/**
	 *
	 *	Adds a child to the parent
	 */
	function addChild(parentId, locationName) {
		$
				.getJSON(
						"${pageContext.request.contextPath}/module/addresshierarchyrwanda/ahAddChild.form",
						{
							parentId :parentId,
							locationName :locationName
						}, function(data) {
							closeLightBox();
							getChildren(parentId);
							jQuery.each(data.addresses, function(i, address) {
								//
								});
						});
	}

	/**
	 *	Gets the children of the parent specified by parentId	
	 **/
	function getChildren(parentId) {
		jQuery
				.getJSON(
						"${pageContext.request.contextPath}/module/addresshierarchyrwanda/ahGetChildren.form",
						{
							parentId :parentId
						},

						function(data) {
							$("#childlist").empty();
							var count = 0;

							jQuery.each(data.addresses, function(i, address) {
								count++;
								var locationElement = $(
										document.createElement("p")).text(
										address.display);
								locationElement.attr("id", address.id);
								locationElement.css("width", "80%");
								locationElement.css("margin", "1px");
								$("#childlist").append(locationElement);

								locationElement.hover(

								function() {
									$(this).addClass('pretty-hover');
								}, function() {
									$(this).removeClass('pretty-hover');
								});

								locationElement.click( function() {

								});
							});
							// no results were returned so let the user add a name
					});
	}

	/**
	 *	Runs a few functions on document ready	
	 **/
	$(document).ready( function() {
		getHierarchyTypes();

		$("#hierarchysearchfield").keypress( function() {
			var searchValue = $(this).val();
			if (searchValue.length >= 3) {
				searchHierarchy(searchValue, $("#hierarchytypelist").val());
			} else if (searchValue.length == 0) {
				$("#hierarchylist").empty();
			}
		});

		$("#addchild").click( function() {

			showLightBox();

		});
		$("#addbuttonid").click( function() {
			addChild(idOfLastClicked, $("#addlocation").val());

		});
		// handles save name button click
			$("#editbuttonid").click(
					function() {
						editLocationName($("#editlocationid").val(), $(
								"#editlocationname").val());
					});
		});
</script>
</head>
<body>
<h3>Search For Locations:</h3>

<div style="float: left; width: 50%" id="parenthierarchylist">

<div class="header">
<p style="margin: 4px; float: left">Search:&nbsp;<input type="text"
	id="hierarchysearchfield" /></p>
<p style="margin-left: 6px; margin-top: 4px; float: left">Type:&nbsp;<select
	id="hierarchytypelist"></select></p>

</div>
<div class="list" id="hierarchylist"></div>

</div>


<div style="float: left; width: 25%;" id="parentchildlist">

<div class="header">
<p id="childlabel" style="float: left; width: 50%; margin: 0px;">Children</p>
<p id="addchild" align="right"
	style="float: right; width: 50%; cursor: pointer; margin: 0px;">
Add Child</p>
</div>

<div class="list" id="childlist"></div>

</div>

<div id="lightshowdiv" style="display: none;">
<div>Location Name:&nbsp;<input type="text" id="addlocation" />&nbsp;<span
	id="addlocationaction"> </span></div>

<input style="float: right" type="button" id="addbuttonid" value="Ok" />
</div>

<div id="editlightshowdiv" style="display: none;"><input
	type="hidden" id="editlocationid" /> <br />
<div style="float: left">New Name:&nbsp;<input type="text"
	id="editlocationname" />&nbsp; <spanid="editlocationaction"></span>
</div>
&nbsp;&nbsp; <!--  <input align="right" type="button" id="cancelbuttonid" value="Cancel" />&nbsp;-->
<input style="float: right" type="button" id="editbuttonid" value="Ok" />
</div>



</body>
</html>