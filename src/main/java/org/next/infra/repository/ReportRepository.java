package org.next.infra.repository;

import org.next.lms.reply.domain.Reply;
import org.next.lms.report.Report;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
