package com.infrasight.kodtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.infrasight.kodtest.apiendpoints.Account;
import com.infrasight.kodtest.apiendpoints.Group;
import com.infrasight.kodtest.apiendpoints.RelationshipGroup;
import com.infrasight.kodtest.apiendpoints.RelationshipManage;
import com.infrasight.kodtest.requests.RequestAccounts;
import com.infrasight.kodtest.requests.RequestRelationships;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Simple concrete class for JUnit tests with uses {@link TestsSetup} as a
 * foundation for starting/stopping the API server for tests.
 * 
 * You may configure port, api user and api port in {@link TestVariables} if
 * needed.
 */
public class Tests extends TestsSetup {

	private Employee vera;
	private static final int employeeId = 1337;

	/**
	 * Simple example test which asserts that the Kodtest API is up and running.
	 */
	@Test
	public void connectionTest() throws InterruptedException {
		assertTrue(serverUp);
	}

	@Before
	public void setupForAssignment1And2And3() throws InterruptedException {
		vera = new Employee(employeeId, API_KEY);
	}

	@Test
	public void assignment1() throws InterruptedException {
		assertTrue(serverUp);

		/**
		 * TODO: Add code to solve the first assignment. Add Assert to show that you
		 * found the account for Vera
		 */
		assertEquals(1, vera.getAccounts().size());
	}

	@Test
	public void assignment2() throws InterruptedException {
		assertTrue(serverUp);
		/**
		 * TODO: Add code to solve the second assignment where we expect the number of
		 * groups to be 3.
		 */
		int groupCount = 0;
		List<Group> directGroups = vera.getDirectGroups();
		groupCount = directGroups.size();
		// Assert which verifies the expected group count of 3
		assertEquals(3, groupCount);

		/**
		 * TODO: Add Assert to verify the IDs of the groups found
		 */
		String[] groupIds = new String[3];
		for (int i = 0; i < groupCount; i++) {
			groupIds[i] = directGroups.get(i).getId();
		}
		assertEquals("grp_köpenhamn", groupIds[0]);
		assertEquals("grp_malmo", groupIds[1]);
		assertEquals("grp_itkonsulter", groupIds[2]);
	}

	@Test
	public void assignment3() throws InterruptedException {
		assertTrue(serverUp);
		/**
		 * TODO: Add code to solve the third assignment. Add Assert to verify the
		 * expected number of groups. Add Assert to verify the IDs of the groups found.
		 */
		List<Group> allGroups = vera.getAllGroups();
		assertEquals(9, allGroups.size());

		// IDs of groups
		List<String> ids = Arrays.asList("grp_köpenhamn", "grp_malmo", "grp_itkonsulter",
										 "grp_danmark", "grp_sverige", "grp_inhyrda",
										 "grp_chokladfabrik", "grp_choklad", "grp_konfektyr");
		for (Group group : allGroups) {
			assertTrue(ids.contains(group.getId()));
		}
	}

	@Test
	public void assignment4() throws InterruptedException {
		assertTrue(serverUp);

		/**
		 * TODO: Add code to solve the fourth assignment. Add Asserts to verify the
		 * total salary requested
		 */
		String inhyrdGruppId = "grp_inhyrda";
		int totalSalary = 0;
		List<RelationshipGroup> relationshipsInGroup = new RequestRelationships(API_KEY).requestGroupId(inhyrdGruppId);
		for (RelationshipGroup relationship : relationshipsInGroup) {
			for (Account account : new Employee(relationship.getMemberId(), API_KEY).getAccounts()) {
				totalSalary += account.getSalary();
			}
		}
		assertEquals(6112609, totalSalary);

	}

	@Test
	public void assignment5() throws InterruptedException {
		assertTrue(serverUp);

		/**
		 * TODO: Add code to solve the fifth assignment. Add Asserts to verify the
		 * managers requested
		 */
		Set<String> swedishMembersBetween2019And2022Ids = new HashSet<>();

		String swedenId = "grp_sverige";
		String salesGroupId = "grp_saljare";


		List<RelationshipGroup> requestRelationships = new RequestRelationships(API_KEY).requestGroupId(swedenId);
		for (RelationshipGroup groupRelationship : requestRelationships) {
			List<RelationshipGroup> groupIds  = new RequestRelationships(API_KEY).requestGroupId(groupRelationship.getMemberId());
			for (RelationshipGroup groupId : groupIds) {
				List<Account> swedishMembers = new RequestAccounts(API_KEY).requestId(groupId.getMemberId());
				for (Account swedishMember : swedishMembers) {
					DateConvertor dc = new DateConvertor(swedishMember.getEmployedSince());
					if (dc.isBetween(2019, 2022, dc.getYear())) {
						swedishMembersBetween2019And2022Ids.add(swedishMember.getId());
					}
				}
			}
		}

		Set<String> swedenSalesDepartmentBetween2019And2022Ids = new HashSet<>();
		List<RelationshipGroup> salesRelationships = new RequestRelationships(API_KEY).requestGroupId(salesGroupId);
		for (RelationshipGroup salesRelationship : salesRelationships) {
			String memberId = salesRelationship.getMemberId();
			if (swedishMembersBetween2019And2022Ids.contains(memberId)) {
				swedenSalesDepartmentBetween2019And2022Ids.add(memberId);
			}
		}

		Map<String, List<String>> managerSalesDepartmentListIds = new HashMap<>();
		for (String swedishSalesPersonId : swedenSalesDepartmentBetween2019And2022Ids) {
			// get manager through the one who is managed
			List<RelationshipManage> manages = new RequestRelationships(API_KEY).requestManagedId(swedishSalesPersonId);
			for (RelationshipManage manage : manages) {
				String managerId = manage.getAccountId(); // manager
				String managedPersonId = manage.getManagedId(); // managed employee
				List<String> newManagedList = new ArrayList<>(); // list of managed people
				if(!managerSalesDepartmentListIds.containsKey(managerId)) { // check if manager is added to list
					newManagedList.add(managedPersonId); // add sales managed person to list
				} else {
					newManagedList.addAll(managerSalesDepartmentListIds.get(managerId)); // get list of managed people and add one new managed person
					newManagedList.add(managedPersonId);
				}
				managerSalesDepartmentListIds.put(managerId, newManagedList);
			}
		}
		Set<Map.Entry<String, List<String>>> entries = managerSalesDepartmentListIds.entrySet();
		Map<String, List<String>> sortedValues = new MapSortTotalManaged().sort(entries);


		int size = Integer.MAX_VALUE;
		for (List<String> employees : sortedValues.values()) {
			assertTrue(employees.size() <= size);
			size = employees.size();
		}
	}
}
