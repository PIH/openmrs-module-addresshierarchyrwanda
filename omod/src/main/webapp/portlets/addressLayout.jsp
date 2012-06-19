<%@ include file="/WEB-INF/template/include.jsp"%>

<c:if test="${model.size == 'columnHeaders'}">
	<th>Country</th>
	<th>Province</th>
	<th>District</th>
	<th>Sector</th>
	<th>Cell</th>
	<th>Umudugudu</th>
</c:if>

<c:if test="${model.size == 'inOneRow'}">
	<tr>
		<td>${address.country}</td>
		<td>${address.stateProvince}</td>
		<td>${address.countyDistrict}</td>
		<td>${address.cityVillage}</td>
		<td>${address.neighborhoodCell}</td>
		<td>${address.address1}</td>
	</tr>
</c:if>

<c:if test="${model.size == 'full'}">
	<!-- these break the patient dasbhoard in inOneRow mode -->
	<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js" />
	<openmrs:htmlInclude file="/moduleResources/addresshierarchyrwanda/AddressHierarchy.js" />
	<table class="tableClass">
		<tbody>
			<tr>
				<td>Country</td>
				<td><spring:bind path="country">
					<input type="text" name="${status.expression}"
						value="${status.value}" id="cnt_${addressIndex}"
						class="countrySaveClass" />
				</spring:bind></td>
				<td><select name="countryselect" class="countryClass">
				</select></td>
			</tr>
			<tr>
				<td>Province</td>
				<td><spring:bind path="stateProvince">
					<input type="text" name="${status.expression}"
						value="${status.value}" id="sp_${addressIndex}"
						class="provinceSaveClass" />
				</spring:bind></td>
				<td><select name="stateProvinceselect" class="provinceClass" /></td>
			</tr>
			<tr>
				<td>District</td>
				<td><spring:bind path="countyDistrict">
					<input type="text" name="${status.expression}"
						value="${status.value}" id="cd_${addressIndex}"
						class="districtSaveClass" />
				</spring:bind></td>
				<td><select name="countryDistrictselect" class="districtClass" /></td>
			</tr>
			<tr>
				<td>Sector</td>
				<td><spring:bind path="cityVillage">
					<input type="text" name="${status.expression}"
						value="${status.value}" class="sectorSaveClass" />
				</spring:bind></td>
				<td><select name="cityVillageselect" class="sectorClass" /></td>
			</tr>
			<tr>
				<td>Cell</td>
				<spring:bind path="neighborhoodCell">
					<td><input type="text" name="${status.expression}"
						value="${status.value}" id="nc_${addressIndex}"
						class="cellSaveClass" /></td>
				</spring:bind>
				<td><select name="neighborhoodCellselect" class="cellClass" /></td>
			</tr>
			<tr>
				<td>Umudugudu</td>
				<td><spring:bind path="address1">
					<input type="text" name="${status.expression}"
						value="${status.value}" class="address1SaveClass" />
				</spring:bind></td>
				<td><select name="address1select" class="address1Class" /></td>
			</tr>
			<tr>
				<td>Structured:</td>
				<td><span class="isstructured" id="structured_${addressIndex}">--</span></td>
				<td />
			</tr>
			<c:if test="${model.layoutShowExtended == 'true'}">
				<tr>
					<td>Voided:</td>
					<td><spring:bind path="voided">
						<input type="hidden" name="_${status.expression}" />
						<input class="voided" type="checkbox"
							<c:if test="${status.value == true}">checked="checked"</c:if>
							name="${status.expression}" />
					</spring:bind></td>
				</tr>
			</c:if>
		</tbody>
	</table>
	<c:if test="${model.layoutShowExtended == 'true'}">
		<table class="voidedReasonTable">
			<tbody>

				<tr
					style="<spring:bind path="voided"><c:if test="${status.value == false}">display: none;</c:if></spring:bind>"
					class="voidedReasonRowClass">
					<spring:bind path="voidReason">
						<td colspan="4">Voided Reason:</td>
						<td><input type="text" name="${status.expression}"
							value="${status.value}" size="43" /> <c:if
							test="${status.errorMessage != ''}">
							<span class="error">${status.errorMessage}</span>
						</c:if></td>
					</spring:bind>
				</tr>

			</tbody>
		</table>
	</c:if>
	<div id="ah${addressIndex}"></div>
</c:if>
