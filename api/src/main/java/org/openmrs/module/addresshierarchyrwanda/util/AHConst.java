package org.openmrs.module.addresshierarchyrwanda.util;

public class AHConst {
	/**
	 * 
	 */
	
	
		
	// these queries seem to be fast
	public static final String CELL_UMU = "select x.state_province, x.county_district, x.city_village, x.address3, x.address1, pi.patient_id,pi.identifier, location.name from (select identifier,location_id, patient_id, patient_identifier_id from patient_identifier where preferred = 1) pi left join (select address1,state_province, county_district, city_village, neighborhood_cell, date_created,person_id,person_address_id from person_address pa left join address_hierarchy on pa.address1 = address_hierarchy.name inner join address_hierarchy ah2 on pa.neighborhood_cell = ah2.name and address_hierarchy.parent_id = ah2.address_hierarchy_id and ah2.type_id=(select location_attribute_type_id from address_hierarchy_type where name='Cell') where voided=0) x on pi.patient_id = x.person_id inner join location on location.location_id = pi.location_id where location.location_id = ? and x.person_id is null order by x.date_created desc";
	//public static final String CELL_UMU = "select x.person_id, pi.identifier, location.name,x.state_province, x.county_district, x.city_village,x.address3,x.address1 from (select identifier,location_id, patient_id, patient_identifier_id from patient_identifier where preferred = 1) pi inner join (select person_id,state_province, county_district, city_village,neighborhood_cell,address1 from person_address pa where pa.address1 not in (select name from address_hierarchy where parent_id in (select address_hierarchy_id from address_hierarchy where name = pa.neighborhood_cell) and type_id = (select location_attribute_type_id from address_hierarchy_type where name='Cell')) and pa.voided = 0 order by pa.date_created desc) x on pi.patient_id = x.person_id inner join location on location.location_id = pi.location_id and location.location_id = ?";
	public static final String SECTOR_CELL = "select person_id, person_address_id from person_address pa where pa.address3 not in (select name from address_hierarchy where parent_id in (select address_hierarchy_id from address_hierarchy where name = pa.city_village)) and pa.voided = 0 order by pa.date_created desc";
	public static final String DISTRICT_SECTOR = "select person_id, person_address_id from person_address pa where pa.city_village not in (select name from address_hierarchy where parent_id in (select address_hierarchy_id from address_hierarchy where name = pa.county_district)) and pa.voided = 0 order by pa.date_created desc";
	public static final String PROVINCE_DISTRICT = "select person_id, person_address_id from person_address pa where pa.county_district not in (select name from address_hierarchy where parent_id in (select address_hierarchy_id from address_hierarchy where name = pa.state_province)) and pa.voided = 0 order by pa.date_created desc";
	public static final String COUNTRY_PROVINCE = "select person_id, person_address_id from person_address pa where pa.state_province not in (select name from address_hierarchy where parent_id in (select address_hierarchy_id from address_hierarchy where name = pa.country)) and pa.voided = 0 order by pa.date_created desc";
	
	
	
	public static final String LOCATION_BREAKDOWN = "select pa.county_district,pa.city_village, count(*) from(select identifier,location_id, patient_id, patient_identifier_id from patient_identifier where preferred = 1)pi inner join location on location.location_id = pi.location_id and location.location_id = ? inner join (select country,state_province,county_district,city_village, person_id from person_address where voided = 0 and preferred = 1) pa on pi.patient_id = pa.person_id group by pa.country, pa.state_province, pa.county_district, pa.city_village";
	
	
	
	// these queries seem to not be so fast
	
//	public static final String BAD_COUNTRY_PROVINCE = "select paa.person_address_id, paa.person_id from person_address paa         where person_id not in (select  pa.person_id from (select person_address.person_address_id,person_address.person_id, person_address.country, person_address.state_province from person_address where voided = 0) pa inner join (select address_hierarchy.name, parent_id, type_id from address_hierarchy inner join address_hierarchy_type on type_id = location_attribute_type_id where address_hierarchy_type.name = 'Province') ah_p on pa.state_province = ah_p.name inner join (select address_hierarchy.name, address_hierarchy_id from address_hierarchy inner join address_hierarchy_type on type_id = location_attribute_type_id where address_hierarchy_type.name = 'Country') ah_c on pa.country = ah_c.name and ah_p.parent_id = ah_c.address_hierarchy_id) and paa.voided = 0 order by paa.date_created desc";
//	public static final String BAD_ADDRESS = "select paa.person_address_id, paa.person_id from person_address paa         left join (select pa.person_address_id from  (select person_address.voided, person_address.preferred, person_address.person_id, person_address.person_address_id, person_address.country, person_address.state_province, person_address.county_district, person_address.city_village, person_address.address3, person_address.address1 from person_address inner join (select patient_id, max(date_created) from patient_program group by patient_id) prog on person_address.person_id = prog.patient_id  where person_address.date_created > '2010-01-01' and person_address.date_created < '2010-02-01' and person_address.voided=0 and person_address.preferred=1) pa inner join (select address_hierarchy.name, parent_id, type_id, address_hierarchy_id from address_hierarchy inner join address_hierarchy_type on type_id = location_attribute_type_id where address_hierarchy_type.name = 'Umudugudu') ah_u on pa.address1 = ah_u.name inner join (select address_hierarchy.name, parent_id, type_id, address_hierarchy_id from address_hierarchy inner join address_hierarchy_type on type_id = location_attribute_type_id where address_hierarchy_type.name = 'Cell') ah_ce on pa.neighborhood_cell = ah_ce.name  and ah_u.parent_id = ah_ce.address_hierarchy_id inner join (select address_hierarchy.name, parent_id, type_id, address_hierarchy_id from address_hierarchy inner join address_hierarchy_type on type_id = location_attribute_type_id where address_hierarchy_type.name = 'Sector') ah_s on pa.city_village = ah_s.name and ah_ce.parent_id =  ah_s.address_hierarchy_id inner join(select address_hierarchy.name, parent_id, type_id, address_hierarchy_id from address_hierarchy inner join address_hierarchy_type on type_id = location_attribute_type_id where address_hierarchy_type.name = 'District') ah_d on pa.county_district = ah_d.name and ah_s.parent_id = ah_d.address_hierarchy_id inner join (select address_hierarchy.name, parent_id, type_id, address_hierarchy_id from address_hierarchy inner join address_hierarchy_type on type_id = location_attribute_type_id where address_hierarchy_type.name = 'Province') ah_p on pa.state_province = ah_p.name and ah_d.parent_id = ah_p.address_hierarchy_id inner join (select address_hierarchy.name, address_hierarchy_id from address_hierarchy inner join address_hierarchy_type on type_id = location_attribute_type_id where address_hierarchy_type.name = 'Country') ah_c on pa.country = ah_c.name and ah_p.parent_id = ah_c.address_hierarchy_id) x on paa.person_address_id = x.person_address_id and paa.date_created > '2010-01-01' and paa.date_created < '2010-02-01' and paa.voided=0 and paa.preferred = 1  where x.person_address_id is null ";
	//	left join  public static final String BAD_CELL_UMU = "select paa.person_address_id, paa.person_id from person_address paa         left join (select  pa.person_id, pa.person_address_id from (select person_address.person_address_id,person_address.person_id, person_address.city_village, person_address.address1 from person_address where voided = 0 and date_created > '2010-08-01') pa inner join (select address_hierarchy.name, parent_id, type_id from address_hierarchy inner join address_hierarchy_type on type_id = location_attribute_type_id where address_hierarchy_type.name = 'Umudugudu') ah_p on pa.address1 = ah_p.name inner join (select address_hierarchy.name, address_hierarchy_id from address_hierarchy inner join address_hierarchy_type on type_id = location_attribute_type_id where address_hierarchy_type.name = 'Cell') ah_c on pa.city_village = ah_c.name and ah_p.parent_id = ah_c.address_hierarchy_id) x on paa.person_address_id = x.person_address_id where paa.voided = 0 and paa.date_created > '2010-08-01' and x.person_address_id is null order by paa.date_created desc";
//	public static final String BAD_PROVINCE_DISTRICT = "select paa.person_address_id, paa.person_id from person_address paa         left join (select  pa.voided,pa.person_address_id,pa.person_id, pa.country, pa.state_province  from (select person_address.voided,person_address.person_address_id,person_address.person_id, person_address.country, person_address.state_province from person_address) pa inner join (select address_hierarchy.name, parent_id, type_id from address_hierarchy inner join address_hierarchy_type on type_id = location_attribute_type_id where address_hierarchy_type.name = 'District') ah_p on pa.state_province = ah_p.name inner join (select address_hierarchy.name, address_hierarchy_id from address_hierarchy inner join address_hierarchy_type on type_id = location_attribute_type_id where address_hierarchy_type.name = 'Province') ah_c on pa.state_province = ah_c.name and ah_p.parent_id = ah_c.address_hierarchy_id) x on paa.person_address_id = x.person_address_id where x.person_address_id is null and paa.voided = 0 order by paa.date_created desc";
	public static final String ALL_ADDRESSES = "select * from (select max(date_created), patient_id from patient_program group by patient_id) pp inner join  person_address on pp.patient_id = person_address.person_id where person_address.voided = 0  order by person_address.date_created desc";
	
	
	public static final String INVALID_ADDRESS_COUNT = 
		"select count(*) "
		+ " from person_address " 
		+ " left join patient_identifier on patient_identifier.patient_id = person_address.person_id "
		+ " left join patient_program on patient_program.patient_id = person_address.person_id "
		+ " left join patient_state on patient_program.patient_program_id = patient_state.patient_program_id "
		+ " left join program_workflow_state on patient_state.state = program_workflow_state.program_workflow_state_id "
		+ " left join concept_name on concept_name.concept_id = program_workflow_state.concept_id "
		+ " left join person_name on person_name.person_id = person_address.person_id "
		+ " where person_address.voided = 0 AND "
		+ " patient_identifier.preferred = 1 AND "
		+ " person_name.preferred = 1 AND "
		+ " patient_program.voided = 0 AND "
		+ " patient_program.date_completed is null AND "
		+ " (person_address.country not in (select name from address_hierarchy where type_id = 1) "
		+ " OR person_address.state_province not in (select name from address_hierarchy where type_id = 2 and parent_id in (select address_hierarchy_id from address_hierarchy where name = person_address.country and type_id = 1)) "
		+ " OR person_address.county_district not in (select name from address_hierarchy where type_id = 3 and parent_id in (select address_hierarchy_id from address_hierarchy where name = person_address.state_province and type_id = 2))"
		+ " OR person_address.city_village not in (select name from address_hierarchy where type_id = 4 and parent_id in (select address_hierarchy_id from address_hierarchy where name = person_address.county_district and type_id = 3))"
		+ " OR person_address.address3 not in (select name from address_hierarchy where type_id = 5 and parent_id in (select address_hierarchy_id from address_hierarchy where name = person_address.city_village and type_id = 4))"
		
		+ " OR person_address.address1 not in (select name from address_hierarchy where type_id = 6 and parent_id in (select address_hierarchy_id from address_hierarchy where name = person_address.address3 and type_id = 5)))";
		
		
	/**
	 * yes this is a massive subquery but its not that slow
	 */
	public static final String INVALID_ADDRESS_QUERY = 
		"select distinct patient_identifier.identifier,person_name.family_name, person_name.given_name, location.name, patient.patient_id, person_address.state_province, person_address.county_district, person_address.city_village,person_address.address3,person_address.address1 "
	+ " from person_address " 
	+ " inner join patient_identifier on patient_identifier.patient_id = person_address.person_id "
	+ " inner join patient on patient.patient_id = patient_identifier.patient_id "
	+ " inner join person on person.person_id = patient_identifier.patient_id "
	+ " inner join location on location.location_id = patient_identifier.location_id "
	+ " left join person_name on person_name.person_id = person_address.person_id "
	+ " where patient.voided = 0 AND "
	+ " person.voided = 0 AND "
	+ " person.dead = 0 AND "
	+ " person_address.voided = 0 AND "
//	+ " patient_identifier.identifier_type = 5 AND "
	+ " patient_identifier.preferred = 1 AND "
	+ " patient_identifier.voided = 0 AND "
	+ " person_name.preferred = 1 AND "
	+ " person_name.voided = 0 AND "
	+ " (not exists (select ah.name from address_hierarchy ah where ah.type_id = 1 and ah.name = person_address.country) " 
	+ " OR not exists (select ah.name from address_hierarchy ah where ah.type_id = 2 and ah.name = person_address.state_province and exists (select ah2.address_hierarchy_id from address_hierarchy ah2 where ah2.name = person_address.country and ah2.type_id = 1 and ah.parent_id = ah2.address_hierarchy_id)) " 
	+ " OR not exists (select ah.name from address_hierarchy ah where ah.type_id = 3 and ah.name = person_address.county_district and exists (select ah2.address_hierarchy_id from address_hierarchy ah2 where ah2.name = person_address.state_province and ah2.type_id = 2 and ah.parent_id = ah2.address_hierarchy_id)) "
	+ " OR not exists (select ah.name from address_hierarchy ah where ah.type_id = 4 and ah.name = person_address.city_village and exists (select ah2.address_hierarchy_id from address_hierarchy ah2 where ah2.name = person_address.county_district and ah2.type_id = 3 and ah.parent_id = ah2.address_hierarchy_id)) "
	+ " OR not exists (select ah.name from address_hierarchy ah where ah.type_id = 5 and ah.name = person_address.address3 and exists (select ah2.address_hierarchy_id from address_hierarchy ah2 where ah2.name = person_address.city_village and ah2.type_id = 4 and ah.parent_id = ah2.address_hierarchy_id)) "
	+ " OR not exists (select ah.name from address_hierarchy ah where ah.type_id = 6 and ah.name = person_address.address1 and exists (select ah2.address_hierarchy_id from address_hierarchy ah2 where ah2.name = person_address.address3 and ah2.type_id = 5 and ah.parent_id = ah2.address_hierarchy_id))) "
	+ " order by location.name";
}
