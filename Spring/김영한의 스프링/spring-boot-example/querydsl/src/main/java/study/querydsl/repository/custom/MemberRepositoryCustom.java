package study.querydsl.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;

import java.util.List;

public interface MemberRepositoryCustom {

    List<MemberTeamDto> search(MemberSearchCondition condition);

    Page<MemberTeamDto> searchByPagingV1(MemberSearchCondition condition, Pageable pageable);
    Page<MemberTeamDto> searchByPagingV2(MemberSearchCondition condition, Pageable pageable);
}
