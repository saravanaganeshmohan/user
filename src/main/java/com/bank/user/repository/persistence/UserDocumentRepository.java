package com.bank.user.repository.persistence;

import com.bank.user.application.domain.Status;
import com.bank.user.application.domain.UserDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDocumentRepository extends JpaRepository<UserDocument, UUID> {

    @Query
    Optional<UserDocument> findByUsers_UserIdAndDocumentName(Long userId,String documentName);

    @Query
    List<UserDocument> findByUsers_UserIdAndStatus(Long userId, Status status);
    @Query
    Optional<UserDocument> findByUsers_UserIdAndUserDocumentId(Long userId, UUID userDocumentId);

}
