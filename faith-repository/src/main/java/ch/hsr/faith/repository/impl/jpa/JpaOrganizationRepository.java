package ch.hsr.faith.repository.impl.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ch.hsr.faith.domain.Organization;

@Repository
public interface JpaOrganizationRepository extends JpaRepository<Organization, Long>, JpaSpecificationExecutor<Organization>  {

}
