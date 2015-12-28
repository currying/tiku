package com.toparchy.molecule.permission.init;

import static org.picketlink.idm.model.basic.BasicModel.addToGroup;
import static org.picketlink.idm.model.basic.BasicModel.getGroup;
import static org.picketlink.idm.model.basic.BasicModel.grantRole;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.basic.Group;
import org.picketlink.idm.model.basic.Role;

import com.toparchy.molecule.permission.data.ApplicationResourceRepository;
import com.toparchy.molecule.permission.data.ApplicationRoleRepository;
import com.toparchy.molecule.permission.model.ApplicationResource;
import com.toparchy.molecule.permission.model.ApplicationRole;
import com.toparchy.molecule.permission.model.Member;

@Singleton
@Startup
public class SecurityInitializer {

	@Inject
	private PartitionManager partitionManager;
	@Inject
	private EntityManager moleculeEm;
	@Inject
	private ApplicationRoleRepository applicationRoleRepository;
	@Inject
	private ApplicationResourceRepository applicationResourceRepository;

	@PostConstruct
	public void createUsers() {
		if (check()) {
			createGroup("adminstrators", "ADMINISTRATOR", "超级管理员");
			createGroup("teacher_group", "teacher", "教师");
			createGroup("student_group", "student", "学生");
			addUser("admin", "admin0123456789", "王", "宇轩", "currying", "currying@qq.com", "18652848028", "adminstrators");

			createApplicationRole(new ApplicationRole("ADMINISTRATOR", "超级管理员"));
			createApplicationRole(new ApplicationRole("teacher", "教师"));
			createApplicationRole(new ApplicationRole("student", "学生"));

			createApplicationResource(new ApplicationResource("Administrator", "超级管理", "REST"));

			createRoleResource();
		}
	}

	private boolean check() {
		if (applicationRoleRepository.findAll().size() <= 0)
			return true;
		else
			return false;
	}

	private void createUser(String loginName, String password_, String firstName, String lastName, String nickName,
			String email, String phoneNumber, String roleName, String aliasName) {
		Member user = new Member(loginName, firstName, lastName, nickName, email, phoneNumber);
		IdentityManager identityManager = this.partitionManager.createIdentityManager();
		identityManager.add(user);
		Password password = new Password(password_);
		identityManager.updateCredential(user, password);
		Role role = new Role(roleName);
		identityManager.add(role);
		RelationshipManager relationshipManager = this.partitionManager.createRelationshipManager();
		grantRole(relationshipManager, user, role);
	}

	private void createGroup(String groupName, String roleName, String aliasName) {
		Group group = new Group(groupName);
		IdentityManager identityManager = this.partitionManager.createIdentityManager();
		identityManager.add(group);
		identityManager.update(group);
		Role role = new Role(roleName);
		identityManager.add(role);
		RelationshipManager relationshipManager = this.partitionManager.createRelationshipManager();
		grantRole(relationshipManager, group, role);
	}

	private void addUser(String loginName, String password_, String firstName, String lastName, String nickName,
			String email, String phoneNumber, String groupName) {
		Member user = new Member(loginName, firstName, lastName, nickName, email, phoneNumber);
		IdentityManager identityManager = this.partitionManager.createIdentityManager();
		identityManager.add(user);
		Password password = new Password(password_);
		identityManager.updateCredential(user, password);
		RelationshipManager relationshipManager = this.partitionManager.createRelationshipManager();
		addToGroup(relationshipManager, user, getGroup(identityManager, groupName));
	}

	private void createApplicationRole(ApplicationRole applicationRole) {
		moleculeEm.persist(applicationRole);
	}

	private void createApplicationResource(ApplicationResource applicationResource) {
		moleculeEm.persist(applicationResource);
	}

	private void createRoleResource() {
		ApplicationRole applicationRole = applicationRoleRepository.findByKey("ADMINISTRATOR");
		applicationRole.addApplicationResource(applicationResourceRepository.findByKey("Administrator"));
		moleculeEm.persist(applicationRole);
	}
}
