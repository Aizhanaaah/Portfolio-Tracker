package com.aizhan.Portfolio_Tracker.Repository;
import com.aizhan.Portfolio_Tracker.Entity.Portfolio;
import com.aizhan.Portfolio_Tracker.Entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PositionRepository extends JpaRepository<Position, Long>{
    List<Position> findByPortfolio(Portfolio portfolio);
    List<Position> findByPortfolioId(Long portfolioId);
}
