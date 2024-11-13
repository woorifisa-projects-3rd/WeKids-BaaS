package com.wekids.baas.baasMember.repository;

import com.wekids.baas.baasMember.domain.BaasMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaasMemberRepository extends JpaRepository<BaasMember, Long> {
}
