package org.online.lms.survey.repository;

import org.online.lms.survey.domain.SurveyAnswer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SurveyAnsRepository extends JpaRepository<SurveyAnswer, Long> {

}
