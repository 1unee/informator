package com.oneune.informator.rest.readers;

import com.google.gson.reflect.TypeToken;
import com.oneune.informator.rest.repositories.UserRepository;
import com.oneune.informator.rest.store.dtos.UserDto;
import com.oneune.informator.rest.store.entities.QUserEntity;
import com.oneune.informator.rest.store.entities.UserEntity;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserReader {

    public final static Type USER_LIST_TYPE = TypeToken.getParameterized(List.class, UserDto.class).getType();
    public final static QUserEntity qUser = new QUserEntity("user");

    UserRepository userRepository;
    ModelMapper modelMapper;
    JPAQueryFactory queryFactory;

    public JPAQuery<UserEntity> writeBaseQuery(Predicate... predicates) {
        return queryFactory.selectFrom(qUser)
                .where(predicates);
    }

    public UserDto getById(Long userId) {
        UserEntity userEntity = writeBaseQuery(qUser.id.eq(userId)).fetchOne();
        return modelMapper.map(userEntity, UserDto.class);
    }

    public UserEntity getEntityById(Long userId) {
        return writeBaseQuery(qUser.id.eq(userId)).fetchOne();
    }

    public List<UserDto> search(int page, int size) {
        Page<UserEntity> paginatedUserEntities = userRepository.findAll(PageRequest.of(page, size));
        return modelMapper.map(paginatedUserEntities.getContent(), USER_LIST_TYPE);
    }

    public Optional<UserDto> get(String username) {
        List<UserEntity> userEntities = writeBaseQuery(qUser.username.eq(username)).fetch();
        List<UserDto> userDtos = modelMapper.map(userEntities, USER_LIST_TYPE);
        return userDtos.isEmpty() ? Optional.empty() : Optional.ofNullable(userDtos.get(0));
    }
}
