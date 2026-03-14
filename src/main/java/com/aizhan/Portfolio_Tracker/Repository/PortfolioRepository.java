package com.aizhan.Portfolio_Tracker.Repository;
import com.aizhan.Portfolio_Tracker.Entity.Portfolio;
import com.aizhan.Portfolio_Tracker.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PortfolioRepository extends JpaRepository<Portfolio, Long>{
    List<Portfolio> findByUser(User user);
    List<Portfolio> findByUserId(Long userId);
}
