package land.leets.domain.comment.presentation.mapper;

import land.leets.domain.comment.domain.Comment;
import land.leets.domain.comment.presentation.dto.CommentResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CommentResponse mappingCommentToDto(Comment comment);
}