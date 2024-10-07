/**
 * @author Starling Diaz on 5/18/2024.
 * @Academy StarAcademy
 * @version bank-stark 1.0
 * @since 5/18/2024.
 */
package com.mentorly.bankstark.repository;

import com.mentorly.bankstark.entities.AccountBeneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountBeneficiaryRepository extends JpaRepository<AccountBeneficiary, String> {
    List<AccountBeneficiary> findByAccountId(String accountId);
    List<AccountBeneficiary> findByAccountOwnerId(String accountOwnerId);
}
