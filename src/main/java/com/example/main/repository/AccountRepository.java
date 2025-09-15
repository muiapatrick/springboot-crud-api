package com.example.main.repository;

import com.example.main.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findById(Long id);

    @Query("SELECT a FROM Account a " +
            "WHERE (:id IS NULL OR a.id = :id) " +
            " AND (:clientId IS NULL OR LOWER(a.clientId) = LOWER(CAST(:clientId as string))) "+
            "AND (:iban IS NULL OR LOWER(a.iban) = LOWER(CAST(:iban as string))) " +
            "AND (:bicSwift IS NULL OR LOWER(a.bicSwift) = LOWER(CAST(:bicSwift as string)))")
    List<Account> findAccounts(
            @Param("id") Long id,
            @Param("clientId") String clientId,
            @Param("iban") String iban,
            @Param("bicSwift") String bicSwift
    );
}
