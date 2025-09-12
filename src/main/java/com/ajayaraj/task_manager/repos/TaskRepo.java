package com.ajayaraj.task_manager.repos;

import com.ajayaraj.task_manager.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepo extends JpaRepository<Task, UUID> {
    List<Task> findByUserId(UUID userId);

    List<Task> findByCompleted(boolean completed);

    @Query("SELECT t FROM Task t WHERE t.userId = :userId AND t.completed = :completed")
    List<Task> findByUserIdAndCompletionStatus(@Param("userId") UUID userId, @Param("completed") boolean completed);
}
