package com.lms.specification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.lms.exceptions.FieldNotFoundException;

import com.lms.utils.ApiUtil;
import com.lms.utils.CustomSortDeserializer;
import com.lms.utils.CustomSortSerializer;
import lombok.*;
import org.apache.logging.log4j.*;
import org.springframework.data.domain.*;

import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Usage in the REST JSON request:
 * <br>
 *
 * <pre>
 * {
 *   "pageNumber": {@link Integer},
 *   "pageSize": {@link Integer},
 *   "sort": [{@link String} array],
 *   "filterGroup": {
 *     "logicalOperator": {@link LogicalOperator},
 *     "filters": [
 *       {
 *         "key": name of the field {@link String},
 *         "searchOperator": {@link SearchOperator},
 *         "value": any object corresponding to field
 *       },
 *       {
 *         "logicalOperator": {@link LogicalOperator},
 *         "filters": [
 *           {
 *             "key": name of the field {@link String},
 *             "searchOperator": {@link SearchOperator},
 *             "value": any object corresponding to field
 *           }
 *         ]
 *       }
 *     ]
 *   }
 * }
 * </pre>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class SearchDTO {
    private static final Logger logger = LogManager.getLogger(SearchDTO.class);

    private Integer pageNumber;
    private Integer pageSize;
    private Sort sort;
    private FilterGroup filterGroup;
    @JsonIgnore
    private Class<?> clazz;

    /**
     * @return Pageable object for pagination and sorting based on the request.
     */
    public Pageable createPageable() {
        int number = (getPageNumber() != null) ? getPageNumber() : ApiUtil.DEFAULT_PAGE;
        int size = (getPageSize() != null) ? getPageSize() : ApiUtil.DEFAULT_SIZE;
        Sort sort = (getSort() != null && !getSort().isEmpty()) ? getSort() : Sort.unsorted();
        return PageRequest.of(number, size, sort);
    }

    /**
     * Builds a Specification object for filtering data based on the request.
     *
     * @param clazz the entity class for which the search request is made
     * @return a wrapped Specification object for filtering data based on the
     * request
     * @throws FieldNotFoundException if keys of the filter request do not
     *                                correspond to fields of the {@code clazz}
     *                                entity
     */
    public <T> SpecificationBasic<T> buildSpecification(Class<T> clazz) throws FieldNotFoundException {
        this.clazz = clazz;
        SpecificationBasic<T> specification = SpecificationBasic.get();
        checkFilters(clazz);
        parseFilters(filterGroup.getFilters(), specification, filterGroup.getLogicalOperator());
        return specification;
    }

    /**
     * Builds a specification structure with possible nested filters and
     * combinations of logical operators.
     */
    private void parseFilters(List<SearchFilter> filters, SpecificationBasic<?> specification, LogicalOperator logicalOperator) {
        for (SearchFilter filter : filters) {
            if (filter.getFilters() != null && !filter.getFilters().isEmpty()) {
                parseFilters(filter.getFilters(), specification, filter.getLogicalOperator());
            } else {
                if (logicalOperator.equals(LogicalOperator.OR)) {
                    specification.or(filter::getKey, filter.getSearchOperator(), convertFilterValue(filter));
                } else if (logicalOperator.equals(LogicalOperator.AND)) {
                    specification.and(filter::getKey, filter.getSearchOperator(), convertFilterValue(filter));
                }
            }
        }
    }

    /**
     * Checks if the filter key is a field of the model entity.
     *
     * @param clazz Model entity
     * @throws FieldNotFoundException if the field is not found in the model entity.
     */
    protected void checkFilters(Class<?> clazz) throws FieldNotFoundException {
        List<String> fields = Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).toList();
        for (SearchFilter filter : filterGroup.getFilters()) {
            if ((filter.getFilters() == null || filter.getFilters().isEmpty()) && !fields.contains(filter.getKey()))
                throw new FieldNotFoundException(filter.getKey());
        }
    }

    public Object convertFilterValue(SearchFilter filter) {
        try {
            String key = filter.getKey();
            Object value = filter.getValue();

            Field field = clazz.getDeclaredField(key);
            Class<?> fieldType = field.getType();

            return castValueToType(value, fieldType);
        } catch (NoSuchFieldException | SecurityException e) {
            logger.error(e);
        }
        return filter.getValue();
    }

    private Object castValueToType(Object value, Class<?> type) {
        if (type.equals(LocalDateTime.class)) {
            if (value instanceof ArrayList<?> between) {
                LocalDateTime from = LocalDateTime.parse((String) between.get(0));
                LocalDateTime to = LocalDateTime.parse((String) between.get(1));
                return List.of(from, to);
            }
            return LocalDateTime.parse((String) value);
        } else if (type.equals(LocalDate.class)) {
            return LocalDate.parse((String) value);
        } else if (type.equals(String.class)) {
            return value.toString();
        } else if (type.equals(Integer.class)) {
            return Integer.valueOf((String) value);
        } else if (type.equals(Long.class)) {
            return Long.valueOf((String) value);
        } else if (type.equals(Date.class)) {
            return parseDate(value.toString());
        }
        return value;
    }

    private Date parseDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.ENGLISH);
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            logger.error(e);
        }
        return null;
    }

    @JsonSerialize(using = CustomSortSerializer.class)
    @JsonDeserialize(using = CustomSortDeserializer.class)
    @SuppressWarnings("unused")
    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
