package com.toparchy.molecule.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.picketlink.Identity;
import org.picketlink.credential.DefaultLoginCredentials;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.credential.Password;

import com.toparchy.molecule.permission.model.Member;

@Stateless
public class MemberRegistration {

	@Inject
	private Logger log;
	@Inject
	private PartitionManager partitionManager;
	@Inject
	private Event<Member> memberEventSrc;
	@Inject
	private Identity identity;

	public void register(Member member) throws Exception {
		log.info("Registering " + member.getLoginName());
		IdentityManager identityManager = this.partitionManager.createIdentityManager();
		identityManager.add(member);
		identityManager.updateCredential(member, new Password(member.getPassWord()));
		memberEventSrc.fire(member);
	}

	public void modifyPassword(String password) {
		IdentityManager identityManager = this.partitionManager.createIdentityManager();
		identityManager.updateCredential(this.identity.getAccount(), new Password(password));
	}
}
